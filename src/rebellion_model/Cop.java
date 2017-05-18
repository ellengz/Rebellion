package rebellion_model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ellen on 18/5/17.
 */
public class Cop {

    //the unique id of an cop
    int id;
    // the x-coordinate of the cop in the map
    int positionX;
    // the y-coordinate of the cop in the map
    int positionY;

    private Random randomGenerator = new Random();

    public Cop(int id){

        this.id = id;

    }

    public void move(ArrayList<int[]> emptySlots){

        if(emptySlots.size() > 0){
            // choose a random position
            int moveTo = randomGenerator.nextInt(emptySlots.size());
            this.setPositionX(emptySlots.get(moveTo)[0]);
            this.setPositionY(emptySlots.get(moveTo)[1]);
        }
    }


    public int getId() {
        return id;
    }
    public int getPositionX() {
        return positionX;
    }
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }
    public int getPositionY() {
        return positionY;
    }
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}
