# Rebellion
A Java version of NetLogo rebellion model.

## System
### Background
This project models the rebellion of a subjugated population against a central authority. It is is an adaptation of Joshua Epstein's model of civil violence (2002).  

The population wanders around randomly. If their level of grievance against the central authority is high enough, and their perception of the risks involved is low enough, they openly rebel. A separate population of police officers ("cops"), acting on behalf of the central authority, seeks to suppress the rebellion. The cops wander around randomly and arrest people who are actively rebelling.
### Design 
To cover all the features in the original NetLogo model, we use a simulator class to create lists of agents and cops, give each person a random position, and create a map controller to allow each agent/cop move and take action in each tick by providing each agent/cop its neighbours information in the map. All relevant parameters are stored in a params class.  

To make the neighbours information extraction more efficient, instead of iterating the lists, in map controller we use a String[][] to store the state in each position. At first we used int[][] to do this job, however, since one position can be occupied by multiple persons (e.g. one jailed agent with another person or even two more), the state can be very complex and it’s hard to track by adding up each per- son’s state to represent a combined state. Therefore, we decided to use a string (‘jc’ for a position occupied by a jailed agent and a cop), which can clearly identify the complex state.  

Other features captured form NetLogo include: an overload exception will be thrown if the sum of agents and cops is set over the max capacity the current map can hold, and agents’ movement can be turned on/off by changing Params.MOVEMENT.

### Usage
Steps to run our project:

1. make Sim.class
2. java Sim

- When it’s done, outputs can be found in outputs.csv
- Parameters can be changed in Params.java
- Use ‘make clean’ to clean up after changing params and repeat the steps above
- Extension can be applied when Params.EXTENT is true

### Authors
- Zhu Gong  
- Shuang Qiu    
- Arwinder Singh

## Resource
Wilensky, U. (2004). NetLogo Rebellion model.  
http://ccl.northwestern.edu/netlogo/models/Rebellion.  
Center for Connected Learning and Computer-Based Modeling, Northwestern University, Evanston, IL.
