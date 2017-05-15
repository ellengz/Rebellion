package rebellion;

import java.util.ArrayList;
import java.util.Random;

public class MapController {
	//a two-dimensional array that records the state of each position in the map: 
	//0 - no agent/cop; 1 - a quiet agent; 2 - an active agent; 3 - a jailed agent; 4 - a cop;
	static int[][] map = new int[Params.MAX_MAP_XVALUE][Params.MAX_MAP_YVALUE];
	//a list that contains all the agents
	ArrayList agentList = new ArrayList();
	//a list that contains all the cops
	ArrayList copList = new ArrayList();
	private Random randomGenerator;

	public MapController(ArrayList agentList, ArrayList copList){
		this.agentList = agentList;
		this.copList = copList;
	}
	public MapController()
	{
		
	}
	
	/*
	 * the steps of a turn
	 */
	public void move(Agent agent){
		//moves all the agents and cops
		for(int i =0;i<agentList.size();i++)
		{
		 //   new Agent(i+1).move(map);
		}
		for(int i =0;i<copList.size();i++)
		{
		 //  new Cop(copList).move(map);
		}
		
		
		
		//decide if the agents rebels
		
		//the cops arrest an active agent within the vision
		
	}
	public ArrayList getNeighbourInVision(int x, int y,int[][] map)
	{
		ArrayList elements = new ArrayList();
		ArrayList activePositions = new ArrayList();
		
		int copNum = 0,activeNum = 0, quietNum = 0,jailedNum = 0;
		int vision = Params.VISION;		
		int visionLength = (vision*2)+1;
		int startingX = x-vision;
		int startingY = y-vision;
		if(startingX<0)
			startingX = Params.MAX_MAP_XVALUE - (vision-x);
		if(startingY<0)
			startingY = Params.MAX_MAP_YVALUE - (vision-y);
	
		for(int i = 0;i<visionLength;i++)
		{

			int tempX = (i+startingX)%Params.MAX_MAP_XVALUE;
			for(int j = 0; j<visionLength;j++)
			{
				int tempY = (j+startingY)%Params.MAX_MAP_YVALUE;
				boolean inVision = inVision(x,y,tempX,tempY,vision);
							
				if (inVision && map[tempX][tempY] == 4)	
					copNum++;									 
				else if(inVision && map[tempX][tempY] ==3)
					jailedNum++;
				else if(inVision && map[tempX][tempY] ==2)
				{ activePositions.add(tempX);activePositions.add(tempY);}
				else if(inVision && map[tempX][tempY] ==1)
					quietNum++;				    
			}
		}
		elements.add(0,activePositions);
		elements.add(1,copNum);elements.add(2,activeNum);
		elements.add(3,quietNum);elements.add(4,jailedNum);
		
		return elements;
		
	}
	
	public ArrayList getEmptySlotsInVision(int x, int y, int[][]map)
	{
		int vision = Params.VISION;
		ArrayList emptySlots = new ArrayList();
		
		
		int visionLength = (vision*2)+1;
		int startingX = x-vision;
		int startingY = y - vision;
		if(startingX<0)
			startingX = Params.MAX_MAP_XVALUE - (vision-x);
		if(startingY<0)
			startingY = Params.MAX_MAP_YVALUE - (vision-y);
	
		for(int i = 0;i<visionLength;i++)
		{
			int tempX = (i+startingX)%Params.MAX_MAP_XVALUE;
			for(int j = 0; j<visionLength;j++)
			{
				
				int tempY = (j+startingY)%Params.MAX_MAP_YVALUE;
				boolean inVision = inVision(x,y,tempX,tempY,vision);
			
				boolean isEmpty = map[tempX][tempY] == 0;
				
				
				if (inVision && isEmpty){					
				     emptySlots.add(tempX); 
				emptySlots.add(tempY);
				map[tempX][tempY] = 107;
				}	 					 
				
				    
			}
		}	
	
		return emptySlots;
	}
	
	private boolean inVision(int startingX,int startingY, int checkX, int checkY, int vision)
	{
		boolean inVision = false;
		double distanceOneX = Math.abs(startingX-checkX);
		double distanceOneY = Math.abs(startingY-checkY);
		double maxX = Math.max(startingY, checkX);
		double maxY = Math.max(startingY, checkY);
		double minX = Math.min(startingX, checkX);
		double minY = Math.min(startingY, checkY);
		
		double distanceTwoX = Params.MAX_MAP_XVALUE - maxX-1+minX; 
		double distanceTwoY = Params.MAX_MAP_YVALUE - maxY-1+minY; 
		double distanceXX = Math.min(distanceOneX, distanceTwoX);
		double distanceYY = Math.min(distanceOneY, distanceTwoY);
		
		double radiusDistance = Math.sqrt(((distanceXX)*(distanceXX))+((distanceYY)*(distanceYY)));
		if(radiusDistance<=vision)
			inVision = true;
		    
		return inVision;
	}	
	
	
	public void display()
	{
		for(int i =0;i< Params.MAX_MAP_XVALUE;i++)
		{
			for(int j = 0; j<Params.MAX_MAP_YVALUE;j++)
			{
				//System.out.print(map[i][j]);
			}
			System.out.println("");
		}
		
		for(int i =0;i<agentList.size();i++)
		{
			System.out.println(agentList.get(i));
		}
		for(int i =0;i<copList.size();i++)
		{
			System.out.println(copList.get(i));
		}
		
		
	}
 

}
