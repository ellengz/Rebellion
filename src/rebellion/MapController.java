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
	public ArrayList getNeighbourInVision(int x, int y,int[][] map){
		ArrayList elements = new ArrayList();
		ArrayList activePositions = new ArrayList();

		int copNum = 0,activeNum = 0, quietNum = 0,jailedNum = 0;
		int vision = Params.VISION;
		int visionLength = (vision * 2) + 1;
		int startingX = x - vision;
		int startingY = y - vision;
		if(startingX < 0)
			startingX = Params.MAX_MAP_XVALUE - (vision - x);
		if(startingY < 0)
			startingY = Params.MAX_MAP_YVALUE - (vision - y);

		for(int i = 0; i < visionLength; i++) {

			int tempX = (i + startingX) % Params.MAX_MAP_XVALUE;
			for (int j = 0; j < visionLength; j++) {
				int tempY = (j + startingY) % Params.MAX_MAP_YVALUE;
				boolean inVision = inVision(x, y, tempX, tempY);
                if (inVision){
                    switch (map[tempX][tempY]){
                        case Params.COP:
                            copNum++;
                            break;
                        case Params.JAILED_AGENT:
                            jailedNum++;
                            break;
                        case Params.ACTIVE_AGENT:
                            activePositions.add(tempX);
                            activePositions.add(tempY);
                            activeNum ++;
                            break;
                        case Params.QUIET_AGENT:
                            quietNum++;
                            break;
                    }
                }
			}
		}
		elements.add(0, activePositions);
		elements.add(1, quietNum);
		elements.add(2, activeNum);
		elements.add(3, jailedNum);
		elements.add(4, copNum);

		return elements;

	}

	public ArrayList getEmptySlotsInVision(int x, int y, int[][]map){
		int vision = Params.VISION;
		ArrayList emptySlots = new ArrayList();


		int visionLength = (vision*2)+1;
		int startingX = x - vision;
		int startingY = y - vision;
		if(startingX<0)
			startingX = Params.MAX_MAP_XVALUE - (vision-x);
		if(startingY<0)
			startingY = Params.MAX_MAP_YVALUE - (vision-y);

		for(int i = 0; i < visionLength; i++){
			int tempX = (i + startingX) % Params.MAX_MAP_XVALUE;
			for(int j = 0; j < visionLength; j++){

				int tempY = (j + startingY) % Params.MAX_MAP_YVALUE;
				boolean inVision = inVision(x, y, tempX, tempY);

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

    /**
     * check whether the target patch is within the vision
     * @param startingX
     * @param startingY
     * @param checkX
     * @param checkY
     * @return boolean
     */
	private boolean inVision(int startingX,int startingY,int checkX,int checkY){

		double distanceX = Math.min(Math.abs(startingX-checkX),
                Params.MAX_MAP_XVALUE - Math.abs(startingX-checkX));

        double distanceY = Math.min(Math.abs(startingY-checkY),
                Params.MAX_MAP_YVALUE - Math.abs(startingY-checkY));

		double radiusDistance = Math.sqrt(Math.pow(distanceX, 2) +
                                          Math.pow(distanceY, 2));
		return(radiusDistance <= Params.VISION);
	}


	public void display(){
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
