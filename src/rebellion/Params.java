package rebellion;
/**
 * Parameters that influence the behaviour of the system 
 */
public class Params {
	public final static int MAX_MAP_XVALUE = 50;
	public final static int MAX_MAP_YVALUE = 50;
	public final static int VISION = 9;
	public final static double COP_DENSITY = 2;
	public final static double AGENT_DENSITY = 10.8;
	public final static int MAX_JAIL_TERM = 20;
	public final static double GOVERNMENT_LEGITIMACY = 0.1;
	public final static double K_PARAM = 2.3;
	public final static double THRESHOLD = 0.1;
	// the state of a patch in the map, saying it's occupied by whom
	public final static int EMPTY = 0;
	public final static int QUIET_AGENT = 1;
	public final static int ACTIVE_AGENT = 2;
	public final static int JAILED_AGENT = 1;
	public final static int COP = 4;


}