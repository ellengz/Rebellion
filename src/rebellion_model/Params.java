package rebellion_model;

/**
 * Created by ellen on 18/5/17.
 */
/**
 * Parameters that influence the behaviour of the system
 */
public class Params {
    public final static int MAX_MAP_XVALUE = 40;
    public final static int MAX_MAP_YVALUE = 40;
    public final static double VISION = 7;
    public final static double COP_DENSITY = 4;
    public final static double AGENT_DENSITY = 70;
    public final static int MAX_JAIL_TERM = 30;
    public final static double GOVERNMENT_LEGITIMACY = 0.82;
    public final static double K_PARAM = 2.3;
    public final static double THRESHOLD = 0.1;
    public final static int TICKS = 120;
    // the state of a patch in the map, saying it's occupied by whom
    public final static int EMPTY = 0;
    public final static int COP = 1;
    public final static int QUIET_AGENT = 2;
    public final static int ACTIVE_AGENT = 3;
    public final static int JAILED_AGENT = 4;




}
