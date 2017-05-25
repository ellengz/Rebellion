/**
 * Parameters that influence the behaviour of the system.
 */
public class Params {
	// the max number of columns in the map
	public final static int MAX_MAP_XVALUE = 40;
	// the max number of rows in the map
	public final static int MAX_MAP_YVALUE = 40;
	// the vision of both agents & cops
	public final static double VISION = 7;
	// the density of the cops in the map, range from 0-100
	public final static double COP_DENSITY = 4;
	// the density of the agents in the map, range from 0-100
	public final static double AGENT_DENSITY = 70;
	// the max jail term of an agent
	public final static int MAX_JAIL_TERM = 30;
	// the legitimacy of a government, range from 0-1
	public final static double GOVERNMENT_LEGITIMACY = 0.82;
	// a fixed number used to judge if an agent is active or quiet
	public final static double K_PARAM = 2.3;
	// a fixed number used to judge if an agent is active or quiet
	public final static double THRESHOLD = 0.1;
	// the number of ticks the simulator will run
	public final static int TICKS = 500;
	// the state of a patch in the map, saying it's occupied by whom
	public final static String EMPTY = "";
	public final static String COP = "c";
	public final static String QUIET_AGENT = "q";
	public final static String ACTIVE_AGENT = "a";
	public final static String JAILED_AGENT = "j";
	// the switch of the extension function, true is on, false is off
	public final static boolean EXTENT = false;
	// the switch of the agent movement, true is on, false is off
	public final static boolean MOVEMENT = true;
	// the parameter to decide the influence of the jailed number to the
	// government legitimacy
	public final static double LEGITIMACY_COE = 0.01;

}
