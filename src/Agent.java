import java.util.ArrayList;
import java.util.Random;

/**
 * An agent can move, rebel and be put in jail.
 */
public class Agent extends Turtle{

	// a random number range from 0-1, fixed for the agent's lifetime
	double riskAversion;
	// a random number range from 0-1, fixed for the agent's lifetime
	double perceivedHardship;
	// the number of remaining turns for the agent to be in jail
	int jailTerm;
	// the x-coordinate of the agent in the map
	String state;

	private Random randomGenerator = new Random();

	/**
     * create a new agent with an id
     */
	public Agent(int id) {
		super(id);
		this.riskAversion = Math.random();
		this.perceivedHardship = Math.random();
		this.jailTerm = 0;
		this.state = Params.QUIET_AGENT;
	}

	/**
	 * move to an empty slot when the agent is quiet or active count down the
	 * jail terms when the agent is jailed
	 *
	 * @param emptySlots
	 */

	@Override
	public void move(ArrayList<int[]> emptySlots) {
		// only if the agent is not in jail, it can move
		if (this.jailTerm == 0) {
			if (emptySlots.size() > 0) {
				// choose a random position
				int moveTo = randomGenerator.nextInt(emptySlots.size());
				this.setPositionX(emptySlots.get(moveTo)[0]);
				this.setPositionY(emptySlots.get(moveTo)[1]);
			}
		}
	}

	/**
	 * decide if an agent turns to active/ quite, according to the neighbours
	 * and params
	 *
	 * @param neighbours
	 */
	public void ifRebellion(ArrayList neighbours) {
		// only if the agent is not in jail, it can decide whether to be quiet or active
		if (this.jailTerm == 0) {
			int copNum = (int) neighbours.get(1);
			int activeNum = (int) neighbours.get(3) + 1;
			// the possibility of an agent to be arrested
			double eArrestProbability = 1- Math.exp(-Params.K_PARAM * Math.floor(copNum/activeNum));

			// get the government legitimacy
			double legitimacy = Params.GOVERNMENT_LEGITIMACY;
			// if use the extension method
			if(Params.EXTENT){
				int jailedNum = (int) neighbours.get(4);
				// the government legitimacy increases proportionally with the number of nearby jailed agents
				legitimacy += jailedNum * Params.LEGITIMACY_COE;
			}

			// the grievance of an agent
			double grievance = this.perceivedHardship * (1 - legitimacy);
			// decide if an agent turns from quite to active or vice verse
			if (grievance - this.riskAversion * eArrestProbability > Params.THRESHOLD) {
				this.setState(Params.ACTIVE_AGENT);
			} else {
				this.setState(Params.QUIET_AGENT);
			}
		}
	}

	/**
     * the agent is put into jail
     */
	public void setJailed() {
		// set a random jailed term range from 1 - MAX_JAIL_TERM
		this.jailTerm = randomGenerator.nextInt(Params.MAX_JAIL_TERM)+1;
		// set the state of the agent to jailed
		this.setState(Params.JAILED_AGENT);
	}

	/**
     * get the state of the agent
     */
	public String getState() {
		return state;
	}

	/**
     * set the state of the agent
     */
	public void setState(String state) {
		this.state = state;
	}

	/**
     * get the jail term of the agent
     */
	public int getJailTerm() {
		return this.jailTerm;
	}

	/**
     * decrease the jail term by 1
     */
	public void updateTerm() {
		this.jailTerm -= 1;
	}

	/**
     * get the relative info about the agent in string format
     */
	public String toString() {
		return id + "    " + String.format("%.15f", riskAversion) + "    "
				+ String.format("%.15f", perceivedHardship) + "    "
				+ String.format("%03d", positionX) + "    " + String.format("%03d", positionY)
				+ "    " + state;
	}

}
