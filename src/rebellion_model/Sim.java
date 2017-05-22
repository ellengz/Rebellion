package rebellion_model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ellen on 18/5/17.
 */
public class Sim {

	public static void main(String[] args) {

		Random randomGenerator = new Random();

		// the map to store the state of each patch
		String[][] map = new String[Params.MAX_MAP_XVALUE][Params.MAX_MAP_YVALUE];

		int population = Params.MAX_MAP_XVALUE * Params.MAX_MAP_YVALUE;
		int numberOfAgents = (int) (population * (Params.AGENT_DENSITY / 100));
		int numberOfCops = (int) (population * (Params.COP_DENSITY / 100));

		// the list of agents/cops
		ArrayList<Agent> agents = new ArrayList<>(numberOfAgents);
		ArrayList<Cop> cops = new ArrayList<>(numberOfCops);

		// choose a random position for each agent/cop
		int counter = 0;
		System.out.println("1");
		while (counter < numberOfAgents + numberOfCops) {
			int x = randomGenerator.nextInt(Params.MAX_MAP_XVALUE);
			int y = randomGenerator.nextInt(Params.MAX_MAP_YVALUE);
			if (map[x][y] == null || map[x][y].equals("")) {
				if (counter < numberOfAgents) {
					// new agent
					Agent agent = new Agent(counter);
					agent.setPositionX(x);
					agent.setPositionY(y);
					agent.setState(Params.QUIET_AGENT);
					agents.add(agent);
					map[x][y] = Params.QUIET_AGENT;
				} else {
					// new cop
					Cop cop = new Cop(counter - numberOfAgents);
					cop.setPositionX(x);
					cop.setPositionY(y);
					cops.add(cop);
					map[x][y] = Params.COP;
				}
				counter++;
			}
		}

		// the map controller to update map
		MapController mapController = new MapController(map, agents, cops);

		// get the number of agents in different states
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File("test.csv"));

			StringBuilder sb = new StringBuilder();

			sb.append("tick");
			sb.append(',');
			sb.append("quiet agent");
			sb.append(',');
			sb.append("active agent");
			sb.append(',');
			sb.append("jailed agent");
			sb.append('\n');

			int tick = Params.TICKS;

			while (tick > 0) {

				int quietNum = 0;
				int activeNum = 0;
				int jailedNum = 0;

				int qn1 = 0;
				int an1 = 0;
				int jn1 = 0;

				for (int i = 0; i < map.length; i++) {
					for (int j = 0; j < map[i].length; j++) {
						
						jailedNum += mapController.countChar(map[i][j],Params.JAILED_AGENT);
						activeNum += mapController.countChar(map[i][j],Params.ACTIVE_AGENT);
						quietNum += mapController.countChar(map[i][j],Params.QUIET_AGENT);
					}
				}

				for (Agent agent : agents) {
					if (agent.getState() == Params.QUIET_AGENT) {
						qn1++;
					} else if (agent.getState() == Params.ACTIVE_AGENT) {
						an1++;
					} else if (agent.getState() == Params.JAILED_AGENT) {
						jn1++;
					}

				}

				mapController.moveAll();
				mapController.takeActionAll();
				mapController.updateJailTerm();

				System.out.println("map   Q:" + quietNum + "  A:" + activeNum + "  J:" + jailedNum);
				// System.out.println(qn+an+jn);
				// System.out.println(cops.size());
				System.out.println("lists Q:" + qn1 + "  A:" + an1 + "  J:" + jn1);

				sb.append(Params.TICKS - tick);
				sb.append(',');
				sb.append(qn1);
				sb.append(',');
				sb.append(an1);
				sb.append(',');
				sb.append(jn1);
//				sb.append(',');
//				sb.append(quietNum);
//				sb.append(',');
//				sb.append(activeNum);
//				sb.append(',');
//				sb.append(jailedNum);
				sb.append('\n');

				tick--;

			}
			pw.write(sb.toString());
			pw.close();
			System.out.println("done!");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	

}
