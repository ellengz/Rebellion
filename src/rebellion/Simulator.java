package rebellion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.plaf.synth.SynthSeparatorUI;

public class Simulator {
	
	static int[][] map = new int[Params.MAX_MAP_XVALUE][Params.MAX_MAP_YVALUE];
	private Random randomGenerator = new Random();
    
	//static ArrayList allAgents = new ArrayList();
	//static ArrayList allCops = new ArrayList();
	static ArrayList allPopulation = new ArrayList();
	
	int quietNum = 0;
	int activeNum = 0;
	int jailedNum = 0;
	

	public static void main(String[] args) 
	{			
		Simulator ob = new Simulator();
		ob.setup();
		ob.moveAll(allPopulation);
		//ob.setNums();
		new ShowMap().draw(map);
		for(int i =0 ;i<10;i++)
		{
			//try{Thread.sleep(2000);}catch(Exception e){}
	 //    ob.moveAll(allPopulation);		
	     
	   //  new ShowMap().draw(map);
		}
	}

		
	
	public void setup()
	{
	    int	totalPossiblePopulation = Params.MAX_MAP_XVALUE * Params.MAX_MAP_YVALUE;
	    int numberOfAgents = (int) (totalPossiblePopulation*Params.AGENT_DENSITY)/100;
	    int numberOfCops =(int) (totalPossiblePopulation*Params.COP_DENSITY)/100;		
		int totalPopulation = numberOfAgents+numberOfCops;		
		
		int[] mapLocations = new Random().ints(0, totalPossiblePopulation).distinct().limit(totalPopulation).toArray();
		
		for(int i = 0;i<mapLocations.length;i++)
		{
			System.out.println(mapLocations[i]);
			int x = mapLocations[i]%Params.MAX_MAP_XVALUE;
			int y = mapLocations[i]/Params.MAX_MAP_YVALUE;
			if(i<numberOfAgents)
			{
				map[x][y] = 1;
				Agent agent = new Agent(i+1);
				agent.setPositionX(x);
				agent.setPositionY(y);
			//	allAgents.add(agent);
				allPopulation.add(agent);
		//		System.out.println("agents added");
			}
			else
			{
				map[x][y] = 4;				
				Cop cop = new Cop();
				cop.setId(i+numberOfAgents+1);
				cop.setPositionX(x);
				cop.setPositionY(y);
			//	allCops.add(cop);	
				allPopulation.add(cop);
			}		
		}	
	//	new ShowMap().draw(map);
		
	}
	
	public void moveAll(ArrayList allMembers)
	{	
		ArrayList tempAllMemberList = new ArrayList();
		while(allMembers.size()>0)
		{			 
			int index = randomGenerator.nextInt(allMembers.size());
			Object ob = allMembers.get(index); 
		
			if(ob instanceof Cop)
			{  
			   Cop cop = (Cop) ob;
			   cop.moveCop(map);
			  
			}
		   else if(ob instanceof Agent)
			{
			 
			   Agent agent = (Agent) ob;
			   agent.moveAgent(map);			  
			
		    }
			tempAllMemberList.add(allMembers.remove(index));	
			break;
			
	   }
	 allPopulation = tempAllMemberList;
	 //tempAllMemberList.clear();
	
   }
	public void takeActions(ArrayList allMembers)
	{	
		ArrayList tempAllMemberList = new ArrayList();
		while(allMembers.size()>0)
		{			 
			int index = randomGenerator.nextInt(allMembers.size());
			Object ob = allMembers.get(index); 
			int x = 0, y = 0;
		 if(ob instanceof Cop)
			{  
			   Cop cop = (Cop) ob;
			   cop.arrestAgent(map);
			  
			}
		   else if(ob instanceof Agent)
			{
			      Agent agent = (Agent) ob;
				  agent.setRebellion(map);			  
			
		    }
			tempAllMemberList.add(allMembers.remove(index));			
			
	   }
	 allPopulation = tempAllMemberList;
	 tempAllMemberList.clear();
	
   }
			  
	public void setNums()
	{
		for(int i =0;i< Params.MAX_MAP_XVALUE;i++)
		{
			for(int j = 0; j<Params.MAX_MAP_YVALUE;j++)
			{
				System.out.print(map[i][j]+" ");
				if(map[i][j] == 1)
			    	 quietNum++;				
				else if(map[i][j] == 2)
					activeNum++;				
				else if(map[i][j] == 3)			
					jailedNum++;				
			}
			System.out.println("");
		
		}
	}
	public ArrayList joinLists(ArrayList first,ArrayList second)
	{
            first.addAll(second);
            return first;
	}


}
