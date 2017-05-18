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

    public int[] arrest(ArrayList neighbours){

        ArrayList<int[]> activePositions = (ArrayList<int[]>) neighbours.get(0);
        if(activePositions.size() > 0){
            // has at least one active agent in vision
            int target = randomGenerator.nextInt(activePositions.size());
            int[] target_p = activePositions.get(target);
            //this.setPositionX(target_p[0]);
            //this.setPositionY(target_p[1]);
            return target_p;

        }else{
            return null;
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

    public String toString(){
        return id+"    " +String.format("%03d",positionX)+"    "
                +String.format("%03d",positionY);
    }
}
