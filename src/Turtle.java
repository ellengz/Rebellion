import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ellen on 25/5/17.
 */
public class Turtle {

    //the unique id of an cop
    int id;
    // the x-coordinate of the cop in the map
    int positionX;
    // the y-coordinate of the cop in the map
    int positionY;

    private Random randomGenerator = new Random();

    /**
     * create a new turtle with an id
     */
    public Turtle(int id){
        this.id = id;
    }

    /**
     * the movement of a turtle according to the empty slots in his vision
     */
    public void move(ArrayList<int[]> emptySlots){
        //if there is an empty slot in his vision,the turtle moves
        if(emptySlots.size() > 0){
            // choose a random position
            int moveTo = randomGenerator.nextInt(emptySlots.size());
            this.setPositionX(emptySlots.get(moveTo)[0]);
            this.setPositionY(emptySlots.get(moveTo)[1]);
        }
    }

    /**
     * get the position X of the turtle
     */
    public int getPositionX() {
        return positionX;
    }

    /**
     * set the position X of the turtle
     */
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    /**
     * get the position Y of the turtle
     */
    public int getPositionY() {
        return positionY;
    }

    /**
     * set the position Y of the turtle
     */
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }


}
