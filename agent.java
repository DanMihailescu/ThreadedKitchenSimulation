import java.util.Random;

public class agent extends Thread {
	protected Ingredient[] table = new Ingredient[2];
	protected static Ingredient[] allIngredients = Ingredient.values();
	protected boolean ingredientsAreOnTable = false;
	
	protected int sandwichesMade = 0;
	protected int maxSandwiches = 20;
	
	protected Random rand = new Random();
	public static agent agentThread;

	/**
	 * Main test method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Creates a agent thread and starts it
		agentThread = new agent();
		agentThread.start();

		// Creates and starts the chefs (one for each ingredient)
		for (Ingredient ingredient : allIngredients) {
			new chef(ingredient).start();
		}
	}

	/**
	 * Keeps checking if it can make more sandwiches while putting ingredients on the table
	 */
	public void run() {
		while (!finishedMakingSandwiches()) {
			putIngredientsOnTable();
		}

		// Print out the total number of sandwiches that were made
		System.out.printf("A total of %d sandwiches have been made.%n", sandwichesMade);
	}

	/**
	 * Picks 2 ingredients, then when no ingredients are on the table, puts them on
	 */
	public void putIngredientsOnTable() {
		Ingredient[] ingredients = new Ingredient[2];

		// Picks 2 random ingredients to be put on the table
		ingredients[0] = allIngredients[rand.nextInt(allIngredients.length)];
		do {
			ingredients[1] = allIngredients[rand.nextInt(allIngredients.length)];
		} while (ingredients[0] == ingredients[1]);

		// Puts ingredients on the table and synchronizes
		synchronized (this) {
			try {
				while (ingredientsAreOnTable()) {
					wait();
				}

				this.table = ingredients;
				this.placeIngredients(true);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// Notify all that ingredients have been added
			notifyAll();
		}
	}

	/**
	 *   Checks if there are ingredients on the table
	 */
	public boolean ingredientsAreOnTable() {
		return ingredientsAreOnTable;
	}

	/**
	 * Makes it say that ingredients are on the table
	 * 
	 * @param ingredientsAreOnTable
	 */
	public void placeIngredients(boolean ingredientsAreOnTable) {
		this.ingredientsAreOnTable = ingredientsAreOnTable;
	}

	/**
	 *   Increment the number of sandwiches made
	 */
	synchronized public int makeSandwich() {
		return sandwichesMade++;
	}

	/** 
	 *   Gets all ingredients on the table
	 */
	synchronized public Ingredient[] getIngredientsOnTable() {
		return this.table;
	}

	/**
	 *   Checks if more sandwiches can be made
	 */
	public boolean finishedMakingSandwiches() {
		return (sandwichesMade >= maxSandwiches);
	}

}
