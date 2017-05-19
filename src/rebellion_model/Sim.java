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
                    // new agent
                    Agent agent = new Agent(counter);
                    agent.setPositionX(x);
                    agent.setPositionY(y);
                    agent.setState(Params.QUIET_AGENT);
                    agents.add(agent);
                    map[x][y] = Params.QUIET_AGENT;
                }else{
                    // new cop
                    Cop cop = new Cop(counter - numberOfAgents);
                    cop.setPositionX(x);
                    cop.setPositionY(y);
                    cops.add(cop);
                    map[x][y] = Params.COP;
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

            int qn1 = 0;
            int an1 = 0;
            int jn1 = 0;

            for(int i = 0; i < map.length; i++){
                for(int j = 0; j < map[i].length; j++){
                    if(map[i][j] == Params.QUIET_AGENT) qn++;
                    if(map[i][j] == Params.ACTIVE_AGENT) an++;
                    if(map[i][j] == Params.JAILED_AGENT) jn++;
                }
            }

            for(Agent agent : agents){
                if(agent.getState() == 1){
                    qn1++;
                }else if(agent.getState() == 2){
                    an1++;
                } else if(agent.getState() == 3) {
                    jn1++;
                }

            }

            mapController.moveAll();
            mapController.takeActionAll();

            System.out.println("map   Q:" + qn + "  A:" + an + "  J:" + jn);
            //System.out.println(qn+an+jn);
            //System.out.println(cops.size());
            System.out.println("lists Q:" + qn1 + "  A:" + an1 + "  J:" + jn1);


            tick --;

        }


    }


    public void printCSV(){




    }

}
