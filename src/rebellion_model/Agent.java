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
    //private final static int INITIAL = 0;
    //private final static int QUIET = 1;
    //private final static int ACTIVE = 2;
    //private final static int JAILED = 3;

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
     * move to a empty slot when the agent is quiet or active
     * @param emptySlots
     */
    public void move(ArrayList emptySlots){

        if (this.state == (Params.QUIET_AGENT | Params.ACTIVE_AGENT)){

            if(emptySlots.size() > 0){

                //this.positionX = x;
                //this.positionY = y;
            }
        }
    }

    /**
     * decide if an agent turns to active or not when its quiet,
     * according to the neighbours and params
     * @param neighbours
     */
    public void takeAction(ArrayList neighbours){

        if(this.state == Params.QUIET_AGENT){

            int copNum = (int) neighbours.get(Params.COP);
            int activeNum = (int) neighbours.get(Params.ACTIVE_AGENT) + 1;

            //the possibility of an agent to be arrested
            System.out.println(copNum + "------" + activeNum);
            double eArrestProbability
                    = 1 - Math.exp(- Params.K_PARAM * Math.round(copNum/activeNum));

            //the grievance of an agent
            double grievance
                    = this.perceivedHardship * (1 - Params.GOVERNMENT_LEGITIMACY);

            //decide if an agent turns to active or not
            if(grievance - this.riskAversion * eArrestProbability>Params.THRESHOLD){
                this.state = Params.ACTIVE_AGENT;
            }
        }

    }



}
