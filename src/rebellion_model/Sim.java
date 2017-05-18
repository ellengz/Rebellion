package rebellion_model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ellen on 18/5/17.
 */
public class Sim {


    public static void main(String[] args) {

        Random randomGenerator = new Random();

        // the map to store the state of each patch
        int[][] map = new int[Params.MAX_MAP_XVALUE][Params.MAX_MAP_YVALUE];

        int population = Params.MAX_MAP_XVALUE * Params.MAX_MAP_YVALUE;
        int numberOfAgents = (int) (population * (Params.AGENT_DENSITY / 100));
        int numberOfCops = (int) (population * (Params.COP_DENSITY / 100));

        // the list of agents/cops
        ArrayList<Agent> agents = new ArrayList<>(numberOfAgents);
        ArrayList<Cop> cops = new ArrayList<>(numberOfCops);

        // choose a random position for each agent/cop
        int counter = 0;
        while(counter < numberOfAgents + numberOfCops){
            int x = randomGenerator.nextInt(Params.MAX_MAP_XVALUE);
            int y = randomGenerator.nextInt(Params.MAX_MAP_YVALUE);
            if (map[x][y] == Params.EMPTY) {
                if(counter < numberOfAgents) {
                    map[x][y] = Params.QUIET_AGENT;
                    // new agent
                    Agent agent = new Agent(counter);
                    agent.setPositionX(x);
                    agent.setPositionY(y);
                    agent.setState(Params.QUIET_AGENT);
                    agents.add(agent);
                }else{
                    map[x][y] = Params.COP;
                    // new cop
                    Cop cop = new Cop(counter - numberOfAgents);
                    cop.setPositionX(x);
                    cop.setPositionY(y);
                    cops.add(cop);
                }
                counter ++;
            }
        }

        // the map controller to update map
        MapController mapController = new MapController(map, agents, cops);

        int tick = Params.TICKS;

        while(tick > 0){

            int qn = 0;
            int an = 0;
            int jn = 0;
            int cn = 0;

            mapController.moveAll();
            mapController.takeActionAll();

//            for(int i = 0; i < map.length; i++){
//                for(int j = 0; j < map[i].length; j++){
//                    if(map[i][j] == Params.QUIET_AGENT) qn++;
//                    if(map[i][j] == Params.ACTIVE_AGENT) an++;
//                    if(map[i][j] == Params.JAILED_AGENT) jn++;
//                    if(map[i][j] == Params.COP) cn++;
//                }
//            }

            for(Agent agent : agents){
                if(agent.getState() == 1){
                    qn++;
                }else if(agent.getState() == 2){
                    an++;
                } else if(agent.getState() == 3) {
                    jn++;
                }

            }


            System.out.println("Q:" + qn + "  A:" + an + "  J:" + jn + "  C:" + cn);
            System.out.println(qn+an+jn);
            System.out.println(cops.size());

            tick --;

        }


    }


    public void printCSV(){




    }

}
