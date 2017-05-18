package rebellion_model;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by ellen on 18/5/17.
 */
public class MapController {

    //a two-dimensional array that records the state of each position in the map:
    static int[][] map = new int[Params.MAX_MAP_XVALUE][Params.MAX_MAP_YVALUE];
    //a list that contains all the agents
    ArrayList<Agent> agents = new ArrayList<Agent>();
    //a list that contains all the cops
    ArrayList<Cop> cops = new ArrayList<Cop>();
    // vision
    final static int vision = Params.VISION;

    public MapController(int[][] map, ArrayList agents, ArrayList cops){

        MapController.map = map;
        this.agents = agents;
        this.cops = cops;
    }

    public void moveAll(){


        for(Agent agent : agents){

        }

    }

    public void takeActionAll(){



    }

    /**
     * update the map according to the lists of agents and cops
     */
    private void updateMap(){

        // initialize a new map with all 0 (empty state)
        int[][] new_map = {{Params.EMPTY}};

        // get a updated map
        for(Agent agent : agents){
            new_map[agent.getPositionX()][agent.getPositionY()] = agent.state;
        }
        for(Cop cop : cops){
            //new_map[cop.positionX][cop.positionY] = Params.COP;
        }
        // update the map
        map = new_map;

    }

    /**
     * get neighbours of the agent
     * can be empty slots, or sum of non-empty neighbours
     * @param x
     * @param y
     * @param map
     * @param empty
     * @return list of non-empty neighbours when empty is false
     *          list of empty slots when empty is true
     */
    public ArrayList getNeighbours(int x, int y, int[][] map, boolean empty){

        // list of non-empty neighbours
        ArrayList neighbours = new ArrayList();
        // list of positions occupied by an active agent
        ArrayList<int[]> activePositions = new ArrayList<>();
        // list of empty slots
        ArrayList<int[]> emptySlots = new ArrayList<>();

        int copNum = 0,activeNum = 0, quietNum = 0,jailedNum = 0;
        int visionLength = (vision * 2) + 1;
        int startingX = x - vision;
        int startingY = y - vision;
        if(startingX < 0)
            startingX = Params.MAX_MAP_XVALUE - (vision - x);
        if(startingY < 0)
            startingY = Params.MAX_MAP_YVALUE - (vision - y);

        for(int i = 0; i < visionLength; i++) {
            int tempX = (i + startingX) % Params.MAX_MAP_XVALUE;
            for (int j = 0; j < visionLength; j++) {
                int tempY = (j + startingY) % Params.MAX_MAP_YVALUE;
                // if the target position is within vision
                if(inVision(x, y, tempX, tempY)){
                    if(empty){
                        // when empty slots are required
                        if(map[tempX][tempY] == Params.EMPTY){
                            emptySlots.add(new int[]{tempX, tempY});
                        }
                    }else{
                        // when sum number of non-empty neighbours are required
                        switch (map[tempX][tempY]){
                            case Params.COP:
                                copNum++; break;
                            case Params.JAILED_AGENT:
                                jailedNum++; break;
                            case Params.ACTIVE_AGENT:
                                activePositions.add(new int[]{tempX,tempY});
                                activeNum ++; break;
                            case Params.QUIET_AGENT:
                                quietNum++; break;
                        }
                    }
                }
            }
        }
        if(empty){
            return emptySlots;
        }else{
            neighbours.add(0, activePositions);
            neighbours.add(1, quietNum);
            neighbours.add(2, activeNum);
            neighbours.add(3, jailedNum);
            neighbours.add(4, copNum);
            return neighbours;
        }
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
