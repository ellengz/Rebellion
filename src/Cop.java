import java.util.ArrayList;
import java.util.Random;

/**
 * A cop can move and arrest the active agent.
 */
public class Cop {

    //the unique id of an cop
    int id;
    // the x-coordinate of the cop in the map
    int positionX;
    // the y-coordinate of the cop in the map
    int positionY;

    private Random randomGenerator = new Random();
    
    /**
     * create a new cop with an id
     */
    public Cop(int id){
        this.id = id;
    }

    /**
     * the movement of a cop according to the empty slots in his vision
     */
    public void move(ArrayList<int[]> emptySlots){
    	//if there is an empty slot in his vision,the cop moves
        if(emptySlots.size() > 0){
            // choose a random position
            int moveTo = randomGenerator.nextInt(emptySlots.size());
            this.setPositionX(emptySlots.get(moveTo)[0]);
            this.setPositionY(emptySlots.get(moveTo)[1]);
        }
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
     * get the position X of the cop
     */
    public int getPositionX() {
        return positionX;
    }
    
    /**
     * set the position X of the cop
     */
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }
    
    /**
     * get the position Y of the cop
     */
    public int getPositionY() {
        return positionY;
    }
    
    /**
     * set the position Y of the cop
     */
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    /**
     * get the relative info about the cop in string format
     */
    public String toString(){
        return id+"    " +String.format("%03d",positionX)+"    "
                +String.format("%03d",positionY);
    }
}
