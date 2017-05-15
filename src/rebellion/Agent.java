package rebellion;

import java.util.ArrayList;
import java.util.Random;

public class Agent {
	//the unique id of an agent
	int id;
	//a random number range from 0-1, fixed for the agent's lifetime
	double riskAversion;
	//a random number range from 0-1, fixed for the agent's lifetime
	double perceivedHardship;
	//the state of an agent, true when the agent is active
	//otherwise is false
	boolean isActive;
	//the number of remaining turns for the agent to be in jail
	int jailTerm;
	//the x-coordinate of the agent in the map
	int positionX;
	//the y-coordinate of the agent in the map
	int positionY;
	//the state of an agent
	int state;
	private final static int INITIAL = 0;
	private final static int QUIET = 1;
	private final static int ACTIVE = 2;
	private final static int JAILED = 3;

	private Random randomGenerator = new Random();

	/**
	 * create a new agent
	 * @param id
	 */
	public Agent(int id){
		this.id = id;
		this.riskAversion = Math.random();
		this.perceivedHardship = Math.random();
		this.jailTerm = 0;
		this.isActive = false;
		this.state = INITIAL;
		this.positionX = -1;
		this.positionY = -1;
	}

	/**
	 * set the state of an agent
	 * @return state of the agent
	 */
	public int setState(){
		if(this.jailTerm > 0){
			this.state = JAILED;
		}else{
			this.state = (this.isActive)? ACTIVE: QUIET;
		}
		return this.state;
	}

	public int getPositionX() {
		return positionX;
	}
	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}
	public int getPositionY() {
		return positionY;
	}
	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	/**
	 * a un-jailed agent moves every turn
	 * @param map
	 */
	public void moveAgent(int map[][]){
		ArrayList emptySlots  = new MapController().getEmptySlotsInVision(
				this.getPositionX(), this.getPositionY(), map);
		if (emptySlots.size()>0){
			int moveTo = randomGenerator.nextInt(emptySlots.size());
			if(moveTo%2!=0)
				moveTo = moveTo-1;
			map[(int)emptySlots.get(moveTo)][ (int)emptySlots.get(moveTo+1)] = 1;
			map[this.getPositionX()][this.getPositionY()] = 0;
			this.setPositionX((int)emptySlots.get(moveTo));
			this.setPositionY((int)emptySlots.get(moveTo+1));
			setRebellion(map);
		}
	}

	/**
	 * decide if an agent turns to active or not
	 * according to the current map and params
	 * @param map
	 */
	public void setRebellion(int[][] map){
		//get the number of cops and active agents in the neighbour of an agent
		ArrayList elements = new MapController().getNeighbourInVision(
				this.getPositionX(), this.getPositionY(), map);
		int copNum = (int) elements.get(Params.COP);
		int activeNum = (int) elements.get(Params.ACTIVE_AGENT) + 1;

		//the possibility of an agent to be arrested
		System.out.println(copNum + "------" + activeNum);
		double eArrestProbability
				= 1 - Math.exp(- Params.K_PARAM * Math.round(copNum/activeNum));

		//the grievance of an agent
		double grievance
				= this.perceivedHardship * (1 - Params.GOVERNMENT_LEGITIMACY);

		//decide if an agent turns to active or not
		if(grievance-this.riskAversion * eArrestProbability > Params.THRESHOLD){
			this.isActive = true;
			map[this.getPositionX()][this.getPositionY()] = 2;
		}else{
			this.isActive = false;
		}
	}

	public String toString(){
		return id+"    "+String.format("%.15f", riskAversion) + "    " +
				String.format("%.15f", perceivedHardship) + "    " +
				String.format("%03d", positionX)+"    " +
				String.format("%03d", positionY)+"    " + state;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public void setJailed(){
		this.jailTerm = new Random().nextInt(Params.MAX_JAIL_TERM + 1);
	}



}
