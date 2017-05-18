package rebellion_model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ellen on 18/5/17.
 */
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

    private Random randomGenerator = new Random();

    public Agent(int id){
        this.id = id;
        this.riskAversion = Math.random();
        this.perceivedHardship = Math.random();
        this.jailTerm = 0;
//        this.isActive = false;
        this.state = Params.QUIET_AGENT;
    }

    /**
     * move to an empty slot when the agent is quiet or active
     * count down the jail terms when the agent is jailed
     * @param emptySlots
     */
    public void move(ArrayList<int[]> emptySlots){

        if (this.jailTerm == 0){
            if(emptySlots.size() > 0){
                // choose a random position
                int moveTo = randomGenerator.nextInt(emptySlots.size());
                this.setPositionX(emptySlots.get(moveTo)[0]);
                this.setPositionY(emptySlots.get(moveTo)[1]);
            }
        }else{
            this.jailTerm --;
        }
    }

    /**
     * decide if an agent turns to active/ quite,
     * according to the neighbours and params
     * @param neighbours
     */
    public void updateState(ArrayList neighbours){

        if(this.jailTerm == 0){

            int copNum = (int) neighbours.get(Params.COP);
            int activeNum = (int) neighbours.get(Params.ACTIVE_AGENT) + 1;

            //the possibility of an agent to be arrested
            double eArrestProbability
                = 1 - Math.exp(- Params.K_PARAM * Math.round(copNum/activeNum));

            //the grievance of an agent
            double grievance
                = this.perceivedHardship * (1 - Params.GOVERNMENT_LEGITIMACY);

            //decide if an agent turns from quite to active or vice verse
            if(grievance-this.riskAversion*eArrestProbability>Params.THRESHOLD){
                this.setState(Params.ACTIVE_AGENT);
            }else {
                this.setState(Params.QUIET_AGENT);
            }
        }

    }

    public void setJailed(){
        this.jailTerm = new Random().nextInt(Params.MAX_JAIL_TERM + 1);
        this.setState(Params.JAILED_AGENT);
    }

    public int getId() {
        return id;
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
    public int getState() {
        return state;
    }
    public void setState(int state){ this.state = state; }

    public String toString(){
        return id+"    "+String.format("%.15f", riskAversion) + "    " +
                String.format("%.15f", perceivedHardship) + "    " +
                String.format("%03d", positionX)+"    " +
                String.format("%03d", positionY)+"    " + state;
    }

}
