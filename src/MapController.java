import java.util.ArrayList;

/**
 * A map controller can detect neighbours info for a patch, move all agents and cops in map, allow
 * all agents and cops to take action, and update the map info accordingly.
 */
public class MapController {

	// a two-dimensional array that records the state of each position in the map:
	static String[][] map = new String[Params.MAX_MAP_XVALUE][Params.MAX_MAP_YVALUE];
	// a list that contains all the agents
	ArrayList<Agent> agents = new ArrayList<>();
	// a list that contains all the cops
	ArrayList<Cop> cops = new ArrayList<>();
	// vision
	final static double vision = Params.VISION;

	/**
     * create a new map controller
     */
	public MapController(String[][] map, ArrayList agents, ArrayList cops) {

		this.map = map;
		this.agents = agents;
		this.cops = cops;
	}

	/**
	 * update jail term, if jail term decreases to 0, change the state to quiet
	 */
	public void updateJailTerm() {
		for (Agent agent : agents) {
			if (agent.getJailTerm() > 0) {
				// update the state of an agent which will be released in this tick
				if (agent.getJailTerm() == 1) {
					agent.setState(Params.QUIET_AGENT);
					// update the state in the map
					updateOneAction(agent.getPositionX(), agent.getPositionY(), Params.JAILED_AGENT,
							Params.QUIET_AGENT);
				}
				// decrease the jailed term by 1s
				agent.updateTerm();
			}
		}
	}

	/**
	 * move all the agents and cops
	 */
	public void moveAll() {
		// if the switch of the agent movement is on
		if(Params.MOVEMENT){
			// move all the agents
			for (Agent agent : agents) {
				int x = agent.getPositionX();
				int y = agent.getPositionY();
				agent.move(getEmptySlots(x, y));
				// update the map for next agent to move
				updateOneMove(x, y, agent.getPositionX(), agent.getPositionY(), agent.getState());
			}
		}
		// move all the cops
		for (Cop cop : cops) {
			int x = cop.getPositionX();
			int y = cop.getPositionY();
			cop.move(getEmptySlots(x, y));
			// update the map for next cop to move
			updateOneMove(x, y, cop.getPositionX(), cop.getPositionY(), Params.COP);
		}

	}

	/**
	 * allow all agents and cops to take actions
	 */
	public void takeActionAll() {
		// the agents decide if it will become quiet or active
		for (Agent agent : agents) {
			int x = agent.getPositionX();
			int y = agent.getPositionY();
			String old_state = agent.getState();
			// each agent updates its state according to its new neighbourhood
			agent.ifRebellion(getNeighbours(x, y));
			// update the map
			updateOneAction(x, y, old_state, agent.getState());
		}

		// the cops arrest active agents
		for (Cop cop : cops) {
			int x = cop.getPositionX();
			int y = cop.getPositionY();
			int[] target = cop.arrest(getNeighbours(x, y));
			// arrest an agent in the target position
			if (target != null) {
				// set that agent to jail
				for (Agent agent : agents) {
					if (agent.getPositionX() == target[0] && agent.getPositionY() == target[1]) {
						agent.setJailed();
					}
				}
				// update the map
				// cop moves to the target agent position
				updateOneMove(x, y, target[0], target[1], Params.COP);
				// agent still in the same position, but state updates to jailed
				updateOneAction(target[0], target[1], Params.ACTIVE_AGENT, Params.JAILED_AGENT);
			}
		}
	}

	/**
	 * update the map after one move
	 * 
	 * @param oX
	 * @param oY
	 * @param nX
	 * @param nY
	 * @param state
	 */
	private void updateOneMove(int oX, int oY, int nX, int nY, String state) {
		map[oX][oY] = map[oX][oY].replaceFirst(state, "");
		if (map[nX][nY] != null) {
			map[nX][nY] += state;
		} else {
			map[nX][nY] = state;
		}
	}

	/**
	 * update the map after one action
	 * 
	 * @param x
	 * @param y
	 * @param oState
	 * @param nState
	 */
	private void updateOneAction(int x, int y, String oState, String nState) {
		map[x][y] = map[x][y].replaceFirst(oState, nState);
	}

	/**
	 * get the list of neighbours within the vision
	 * 
	 * @param x
	 * @param y
	 * @return a list of non-empty neighbours
	 */
	public ArrayList getNeighbours(int x, int y) {

		// list of non-empty neighbours
		ArrayList neighbours = new ArrayList();
		// list of positions occupied by an active agent
		ArrayList<int[]> activePositions = new ArrayList<>();

		//get the margin of the vision
		int copNum = 0, activeNum = 0, quietNum = 0, jailedNum = 0;
		double visionLength = (vision * 2) + 1;
		double startingX = x - vision;
		double startingY = y - vision;
		if (startingX < 0)
			startingX = Params.MAX_MAP_XVALUE - (vision - x);
		if (startingY < 0)
			startingY = Params.MAX_MAP_YVALUE - (vision - y);

		// for every slot inside the margin, decide if it is in the vision
		for (int i = 0; i < visionLength; i++) {
			int tempX = (int) ((i + startingX) % Params.MAX_MAP_XVALUE);
			for (int j = 0; j < visionLength; j++) {
				int tempY = (int) ((j + startingY) % Params.MAX_MAP_YVALUE);
				// if the target position is within vision
				if (inVision(x, y, tempX, tempY)) {
					if (map[tempX][tempY] != null &&
							map[tempX][tempY].contains(Params.ACTIVE_AGENT)) {
						activePositions.add(new int[] { tempX, tempY });
					}
					// get the number of agents in each slot
					jailedNum += countChar(map[tempX][tempY], Params.JAILED_AGENT);
					activeNum += countChar(map[tempX][tempY], Params.ACTIVE_AGENT);
					quietNum += countChar(map[tempX][tempY], Params.QUIET_AGENT);
					copNum += countChar(map[tempX][tempY], Params.COP);
				}
			}
		}
		neighbours.add(0, activePositions);
		neighbours.add(1, copNum);
		neighbours.add(2, quietNum);
		neighbours.add(3, activeNum);
		neighbours.add(4, jailedNum);
		return neighbours;

	}

	/**
	 * get the list of empty slots, including patches occupied by jailed agent
	 * within the vision
	 *
	 * @param x
	 * @param y
	 * @return a list of empty slots
	 */
	public ArrayList<int[]> getEmptySlots(int x, int y) {
		// list of empty slots
		ArrayList<int[]> emptySlots = new ArrayList<>();
		//get the margin of the vision
		double visionLength = (vision * 2) + 1;
		double startingX = x - vision;
		double startingY = y - vision;
		if (startingX < 0)
			startingX = Params.MAX_MAP_XVALUE - (vision - x);
		if (startingY < 0)
			startingY = Params.MAX_MAP_YVALUE - (vision - y);
		// for every slot inside the margin, decide if it is in the vision and is empty
		for (int i = 0; i < visionLength; i++) {
			int tempX = (int) ((i + startingX) % Params.MAX_MAP_XVALUE);
			for (int j = 0; j < visionLength; j++) {
				int tempY = (int) ((j + startingY) % Params.MAX_MAP_YVALUE);
				// if the target position is within vision
				if (inVision(x, y, tempX, tempY)) {
					// if the slot is empty or just has jailed agent in it
					if (map[tempX][tempY] == null || map[tempX][tempY].equals("")
							|| (!map[tempX][tempY].contains(Params.QUIET_AGENT)
									&& !map[tempX][tempY].contains(Params.ACTIVE_AGENT)
									&& !map[tempX][tempY].contains(Params.COP) )) {
						emptySlots.add(new int[] { tempX, tempY });
					}
				}
			}
		}
		return emptySlots;
	}

	/**
	 * check if target is within the vision
	 * 
	 * @param x
	 * @param y
	 * @param targetX
	 * @param targetY
	 * @return Boolean
	 */
	private Boolean inVision(int x, int y, int targetX, int targetY) {

		double distanceX = Math.min(Math.abs(x - targetX),
									Params.MAX_MAP_XVALUE - Math.abs(x - targetX));

		double distanceY = Math.min(Math.abs(y - targetY),
									Params.MAX_MAP_YVALUE - Math.abs(y - targetY));

		double radiusDistance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));

		return (radiusDistance <= vision);
	}

	/**
	 * count how many times the sub string (a role) appears in the str
	 *
	 * @param str
	 * @param subStr
	 * @return number of times
	 */
	public int countChar(String str, String subStr) {
		int c = 0;
		if (str != null) {
			for (int i = 0; i <= str.length() - 1; i++) {
				String getstr = str.substring(i, i + 1);
				if (getstr.equals(subStr)) {
					c++;
				}
			}
		}
		return c;
	}

}
