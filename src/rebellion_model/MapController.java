package rebellion_model;

import java.util.ArrayList;

/**
 * Created by ellen on 18/5/17.
 */
public class MapController {

    //a two-dimensional array that records the state of each position in the map:
    static int[][] map = new int[Params.MAX_MAP_XVALUE][Params.MAX_MAP_YVALUE];
    //a list that contains all the agents
    ArrayList<Agent> agents = new ArrayList<>();
    //a list that contains all the cops
    ArrayList<Cop> cops = new ArrayList<>();
    // vision
    final static double vision = Params.VISION;

    public MapController(int[][] map, ArrayList agents, ArrayList cops){

        this.map = map;
        this.agents = agents;
        this.cops = cops;
    }

    /**
     * update jail term, if jail term decreases to 0, change the state to quiet
     */

    public void updateJailTerm(){

        for(Agent agent : agents){

            if(agent.getJailTerm() > 0) {
                if (agent.getJailTerm() == 1) {
                    // update state
                    agent.setState(Params.QUIET_AGENT);
                    // update map
                    updateOneAction(agent.getPositionX(), agent.getPositionY(),
                            Params.JAILED_AGENT, Params.QUIET_AGENT);
                }
                // update term
                agent.updateTerm();

            }
        }
    }

    public void moveAll(){

        for(Agent agent : agents){
            int x = agent.getPositionX();
            int y = agent.getPositionY();
            agent.move(getEmptySlots(x, y));
            // update the map for next agent to move
            updateOneMove(x, y, agent.getPositionX(), agent.getPositionY(),
                                                      agent.getState());
        }

        for(Cop cop : cops){
            int x = cop.getPositionX();
            int y = cop.getPositionY();
            cop.move(getEmptySlots(x, y));
            // update the map for next cop to move
            updateOneMove(x, y, cop.getPositionX(), cop.getPositionY(),
                                                    Params.COP);
        }

    }

    public void takeActionAll(){

        for(Agent agent : agents) {
            int x = agent.getPositionX();
            int y = agent.getPositionY();
            int old_state = agent.getState();
            // each agent updates its state according to its new neighbourhood
            agent.ifRebellion(getNeighbours(x, y));
            // update the map
            updateOneAction(x, y, old_state, agent.getState());
        }

        for(Cop cop : cops){
            int x = cop.getPositionX();
            int y = cop.getPositionY();
            int[] target = cop.arrest(getNeighbours(x, y));

            // arrest an agent in the target position
            if(target != null){

                //set that agent to jail
                for(Agent agent : agents){
                    if(agent.getPositionX() == target[0] &&
                            agent.getPositionY() == target[1]){
                        agent.setJailed();
                    }
                }
                // update the map
                // cop moves to the target agent position
                updateOneMove(x, y, target[0], target[1], Params.COP);
                // agent still in the same position, but state updates to jailed
                updateOneAction(target[0],target[1],
                        Params.ACTIVE_AGENT, Params.JAILED_AGENT);
            }
        }
    }

    /**
     * update the map after one move
     * @param oX
     * @param oY
     * @param nX
     * @param nY
     * @param state
     */
    private void updateOneMove(int oX, int oY, int nX, int nY, int state){

        map[oX][oY] -= state;
        map[nX][nY] += state;
    }

    /**
     * update the map after on action
     * @param x
     * @param y
     * @param oState
     * @param nState
     */
    private void updateOneAction(int x, int y, int oState, int nState){
        map[x][y] -= oState;
        map[x][y] += nState;
    }


    /**
     * get the list of neighbours within the vision
     * @param x
     * @param y
     * @return a list of non-empty neighbours
     */
    public ArrayList getNeighbours(int x, int y){

        // list of non-empty neighbours
        ArrayList neighbours = new ArrayList();
        // list of positions occupied by an active agent
        ArrayList<int[]> activePositions = new ArrayList<>();

        int copNum = 0,activeNum = 0, quietNum = 0,jailedNum = 0;
        double visionLength = (vision * 2) + 1;
        double startingX = x - vision;
        double startingY = y - vision;
        if(startingX < 0)
            startingX = Params.MAX_MAP_XVALUE - (vision - x);
        if(startingY < 0)
            startingY = Params.MAX_MAP_YVALUE - (vision - y);

        for(int i = 0; i < visionLength; i++) {
            int tempX = (int) ((i + startingX) % Params.MAX_MAP_XVALUE);
            for (int j = 0; j < visionLength; j++) {
                int tempY = (int) ((j + startingY) % Params.MAX_MAP_YVALUE);
                // if the target position is within vision
                if(inVision(x, y, tempX, tempY)){

                    if(map[tempX][tempY] <= Params.JAILED_AGENT){

                        switch (map[tempX][tempY]) {
                            case Params.COP:
                                copNum++;
                                break;
                            case Params.JAILED_AGENT:
                                jailedNum++;
                                break;
                            case Params.ACTIVE_AGENT:
                                activePositions.add(new int[]{tempX, tempY});
                                activeNum++;
                                break;
                            case Params.QUIET_AGENT:
                                quietNum++;
                                break;
                        }
                    }else if(map[tempX][tempY] <= 2*Params.JAILED_AGENT){

                        switch (map[tempX][tempY] - Params.JAILED_AGENT) {
                            case Params.COP:
                                copNum++;
                                break;
                            case Params.JAILED_AGENT:
                                jailedNum ++;
                                break;
                            case Params.ACTIVE_AGENT:
                                activePositions.add(new int[]{tempX, tempY});
                                activeNum++;
                                break;
                            case Params.QUIET_AGENT:
                                quietNum++;
                                break;
                        }
                        jailedNum ++;

                    }else {
                        switch (map[tempX][tempY] - 2*Params.JAILED_AGENT) {
                            case Params.COP:
                                copNum++;
                                break;
                            case Params.JAILED_AGENT:
                                jailedNum++;
                                break;
                            case Params.ACTIVE_AGENT:
                                activePositions.add(new int[]{tempX, tempY});
                                activeNum++;
                                break;
                            case Params.QUIET_AGENT:
                                quietNum++;
                                break;
                        }

                        jailedNum += 2;
                    }

//                    switch (map[tempX][tempY]){
//                        case Params.COP:
//                            copNum++; break;
//                        case Params.JAILED_AGENT:
//                            jailedNum++; break;
//                        case Params.ACTIVE_AGENT:
//                            activePositions.add(new int[]{tempX,tempY});
//                            activeNum ++; break;
//                        case Params.QUIET_AGENT:
//                            quietNum++; break;
//                        // combined state
//                        case Params.COP + Params.JAILED_AGENT:
//                            copNum ++; jailedNum ++; break;
//                        case Params.QUIET_AGENT + Params.JAILED_AGENT:
//                            quietNum ++; jailedNum ++; break;
//                        case Params.ACTIVE_AGENT + Params.JAILED_AGENT:
//                            activeNum ++; jailedNum ++; break;
//                        }
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
     * @param x
     * @param y
     * @return a list of empty slots
     */
    public ArrayList<int[]> getEmptySlots(int x, int y){

        // list of empty slots
        ArrayList<int[]> emptySlots = new ArrayList<>();

        double visionLength = (vision * 2) + 1;
        double startingX = x - vision;
        double startingY = y - vision;
        if(startingX < 0)
            startingX = Params.MAX_MAP_XVALUE - (vision - x);
        if(startingY < 0)
            startingY = Params.MAX_MAP_YVALUE - (vision - y);

        for(int i = 0; i < visionLength; i++) {
            int tempX = (int) ((i + startingX) % Params.MAX_MAP_XVALUE);
            for (int j = 0; j < visionLength; j++) {
                int tempY = (int) ((j + startingY) % Params.MAX_MAP_YVALUE);
                // if the target position is within vision
                if(inVision(x, y, tempX, tempY)){
                    if(map[tempX][tempY] == Params.EMPTY |
                            map[tempX][tempY] % Params.JAILED_AGENT == 0){
                        emptySlots.add(new int[]{tempX,tempY});
                    }
//                    else if(map[tempX][tempY] == Params.JAILED_AGENT){
//                        for(Agent agent : agents){
//                            if(agent.getPositionX() == tempX &&
//                                    agent.getPositionY() == tempY){
//                                if(agent.jailTerm > 1){
//                                    emptySlots.add(new int[]{tempX,tempY});
//                                }
//                            }
//                        }
//                    }
                }
            }
        }

        return emptySlots;
    }

    /**
     * check if target is within the vision
     * @param x
     * @param y
     * @param targetX
     * @param targetY
     * @return Boolean
     */
    private Boolean inVision(int x, int y, int targetX, int targetY){

        double distanceX = Math.min(Math.abs(x - targetX),
                Params.MAX_MAP_XVALUE - Math.abs(x - targetX));

        double distanceY = Math.min(Math.abs(y - targetY),
                Params.MAX_MAP_YVALUE - Math.abs(y - targetY));

        double radiusDistance = Math.sqrt(Math.pow(distanceX, 2) +
                Math.pow(distanceY, 2));

        return (radiusDistance <= vision);
    }

}
