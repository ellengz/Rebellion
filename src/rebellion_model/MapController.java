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

    public MapController(int[][] map, ArrayList agents, ArrayList cops){

        this.map = map;
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
        this.map = new_map;

    }

    private ArrayList getNeighbours(int x, int y, int[][] map){



        return null;
    }

    /**
     * get the empty slots in current map
     * @param x
     * @param y
     * @param map
     * @return a list of empty slots
     */
    private ArrayList getEmptySlots(int x, int y, int[][] map){

        int vision = Params.VISION;
        ArrayList<int[]> emptySlots = new ArrayList<>();

        int visionLength = (vision*2)+1;
        int startingX = x - vision;
        int startingY = y - vision;
        if(startingX<0)
            startingX = Params.MAX_MAP_XVALUE - (vision-x);
        if(startingY<0)
            startingY = Params.MAX_MAP_YVALUE - (vision-y);

        for(int i = 0; i < visionLength; i++){
            // handel the boundary of the map
            int tempX = (i + startingX) % Params.MAX_MAP_XVALUE;
            for(int j = 0; j < visionLength; j++){
                // handel the boundary the map
                int tempY = (j + startingY) % Params.MAX_MAP_YVALUE;
                // find an empty position
                if(inVision(x, y, tempX, tempY)){
                    if(map[tempX][tempY] == Params.EMPTY){
                        emptySlots.add(new int[]{tempX, tempY});
                    }
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

        return (radiusDistance <= Params.VISION);
    }
}
