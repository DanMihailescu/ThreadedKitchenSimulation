agent.java: 

	-> The agent class that creates a agent thread
	-> Places ingredients on table for the chefs
	-> Tells the chefs what ingredients are on the table
	-> Tells the chefs if the max number of sandwiches 
	are made
	-> Prints total number of sandwiches made
	-> Main method creates both agent and chef threads
	-> In order to run this class; run main method
	
chef.java: 

	-> The chef class that creates a chef thread
	-> Checks agent for ingredients on table and
	if their ingredient is the missing one, makes a
	sandwich
	-> Keeps making sandwiches until agent says max is 
	reached
	-> Outputs the number of sandwiches chef made at end
	-> In order to run this class; run agents main method
	
Ingredient.java:

	-> Simple enum that stores the 3 types of ingredients
	-> Used by agent and chef
	-> In order to run this class; run agents main method
	
Instructions: 

	1: Run agent(main)