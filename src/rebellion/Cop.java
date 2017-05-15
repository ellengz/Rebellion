package rebellion;

import java.util.ArrayList;
import java.util.Random;

public class Cop {

	//the unique id of an cop
		int id;
	// the x-coordinate of the cop in the map
	int positionX;
	// the y-coordinate of the cop in the map
	int positionY;
	private Random randomGenerator = new Random();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	/*
	 * a un-jailed agent moves every turn
	 */
	public void moveCop(int map[][])
	{
		ArrayList emptySlots  = new MapController().getEmptySlotsInVision(this.getPositionX(), this.getPositionY(), map);
		//System.out.println("size of empty slots for cop "+emptySlots.size());
		  if (emptySlots.size()>0)			 
		  {
			  int moveTo = randomGenerator.nextInt(emptySlots.size());
			  if(moveTo%2!=0)
				  moveTo = moveTo-1;
			  map[(int)emptySlots.get(moveTo)][ (int)emptySlots.get(moveTo+1)] = 4;
			  map[this.getPositionX()][this.getPositionY()] = 0;
			  this.setPositionX((int)emptySlots.get(moveTo));
			  this.setPositionY((int)emptySlots.get(moveTo+1));		
			//  arrestAgent(map);
		  }
	}
	public void arrestAgent(int map[][])
	{
		ArrayList elements  = new MapController().getNeighbourInVision(this.getPositionX(), this.getPositionY(), map);
		ArrayList activeLocations = (ArrayList)elements.get(0);
		  if (activeLocations.size()>0)			 
		  {
			  int activeX = randomGenerator.nextInt(activeLocations.size());
			  activeX = (activeX/2)*2;
			  
			  map[(int)activeLocations.get(activeX)][ (int)activeLocations.get(activeX+1)] = 3;
			  
			 //need to set jail
			  map[this.getPositionX()][this.getPositionY()] = 0;
			  this.setPositionX((int)activeLocations.get(activeX));
			  this.setPositionY((int)activeLocations.get(activeX+1));				
		  }
	}
	
		public String toString()
	{
	    return id+"    " +String.format("%03d",positionX)+"    "+String.format("%03d",positionY);
	}
	

}
