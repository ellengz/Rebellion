import java.util.ArrayList;
import java.util.Random;

/**
 * A cop can move and arrest the active agent.
 */
public class Cop extends Turtle{

    private Random randomGenerator = new Random();

    /**
     * create a new cop with an id
     */
    public Cop(int id){
        super(id);
    }

    /**
     * the cop arrest the active agents in his vision
     */
    public int[] arrest(ArrayList neighbours){
        ArrayList<int[]> activePositions = (ArrayList<int[]>) neighbours.get(0);
        // if there are active agents in his vision
        if(activePositions.size() > 0){
            // has at least one active agent in vision
            int target = randomGenerator.nextInt(activePositions.size());
            int[] target_p = activePositions.get(target);
            this.setPositionX(target_p[0]);
            this.setPositionY(target_p[1]);
            return target_p;
        }else{
            return null;
        }
    }

    /**
     * get the relative info about the cop in string format
     */
    public String toString(){
        return id+"    " +String.format("%03d",positionX)+"    "
                +String.format("%03d",positionY);
    }
}
