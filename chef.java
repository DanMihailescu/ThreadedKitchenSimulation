public class chef extends Thread {
	protected Ingredient ingredient;
	protected int sandwichesMade = 0;

	/**
	 * Creates a chef with a ingredient
	 */
	public chef(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	/**
	 * Makes sandwiches until it can't (hits max) 
	 */
	public void run() {
		agent a = agent.agentThread;
		while (!a.finishedMakingSandwiches()) {
			synchronized (a) {
				try {
					// Wait until ingredients are on the table
					while (!a.ingredientsAreOnTable() && !a.finishedMakingSandwiches()) {
						a.wait();
					}

					// Checks if we made the max number of sandwiches
					if (!a.finishedMakingSandwiches()) {
						// Check if they are the ingredients we need
						boolean b = true;
						for (Ingredient ing : a.getIngredientsOnTable()) {
							if (ing == this.ingredient) {
								b = false;
								break;
							}
						}

						// Make a sandwich
						if (b) {
							a.makeSandwich();
							a.placeIngredients(false);
							sandwichesMade++;
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// Notify other threads that this chef is done
				a.notifyAll();
			}
		}

		// Output the total amount of sandwiches that the chef made
		System.out.printf("The %s chef made %d sandwiches.%n", ingredient.toString(), sandwichesMade);
	}
}