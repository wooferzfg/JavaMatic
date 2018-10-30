package javamatic;

import java.util.HashMap;
import java.util.Map;

/**
 * This class adds the default ingredients and drinks to the JavaMatic machine
 * based on the given requirements.
 */
public class JavaMaticFactory
{
	/**
	 * Creates a new JavaMatic machine with the default ingredients and drinks.
	 * @return The newly created machine.
	 */
	public static Machine createMachine()
	{
		Machine machine = new Machine();
		Map<String, Ingredient> ingredients = new HashMap<String, Ingredient>();
		
		addIngredient(machine, ingredients, "Coffee", 0.75f);
		addIngredient(machine, ingredients, "Decaf Coffee", 0.75f);
		addIngredient(machine, ingredients, "Sugar", 0.25f);
		addIngredient(machine, ingredients, "Cream", 0.25f);
		addIngredient(machine, ingredients, "Steamed Milk", 0.35f);
		addIngredient(machine, ingredients, "Foamed Milk", 0.35f);
		addIngredient(machine, ingredients, "Espresso", 1.10f);
		addIngredient(machine, ingredients, "Cocoa", 0.90f);
		addIngredient(machine, ingredients, "Whipped Cream", 1.00f);
		
		Drink coffee = addDrink(machine, "Coffee");
		addIngredientToDrink(ingredients, coffee, "Coffee", 3);
		addIngredientToDrink(ingredients, coffee, "Sugar", 1);
		addIngredientToDrink(ingredients, coffee, "Cream", 1);
		
		Drink decafCoffee = addDrink(machine, "Decaf Coffee");
		addIngredientToDrink(ingredients, decafCoffee, "Decaf Coffee", 3);
		addIngredientToDrink(ingredients, decafCoffee, "Sugar", 1);
		addIngredientToDrink(ingredients, decafCoffee, "Cream", 1);
		
		Drink caffeLatte = addDrink(machine, "Caffe Latte");
		addIngredientToDrink(ingredients, caffeLatte, "Espresso", 2);
		addIngredientToDrink(ingredients, caffeLatte, "Steamed Milk", 1);
		
		Drink caffeAmericano = addDrink(machine, "Caffe Americano");
		addIngredientToDrink(ingredients, caffeAmericano, "Espresso", 3);
		
		Drink caffeMocha = addDrink(machine, "Caffe Mocha");
		addIngredientToDrink(ingredients, caffeMocha, "Espresso", 1);
		addIngredientToDrink(ingredients, caffeMocha, "Cocoa", 1);
		addIngredientToDrink(ingredients, caffeMocha, "Steamed Milk", 1);
		addIngredientToDrink(ingredients, caffeMocha, "Whipped Cream", 1);
		
		Drink cappuccino = addDrink(machine, "Cappuccino");
		addIngredientToDrink(ingredients, cappuccino, "Espresso", 2);
		addIngredientToDrink(ingredients, cappuccino, "Steamed Milk", 1);
		addIngredientToDrink(ingredients, cappuccino, "Foamed Milk", 1);
		
		machine.restockInventory();

		return machine;
	}

	/**
	 * Adds a new ingredient to the machine.
	 * @param machine The JavaMatic machine being created.
	 * @param ingredients A map of ingredient names to ingredient objects.
	 * @param name The name of the new ingredient.
	 * @param cost The cost of the new ingredient.
	 */
	private static void addIngredient(Machine machine, Map<String, Ingredient> ingredients, String name, float cost)
	{
		Ingredient ingredient = new Ingredient(name, cost);
		ingredients.put(name, ingredient);
		machine.addIngredient(ingredient);
	}
	
	/**
	 * Adds a new drink to the machine.
	 * @param machine The JavaMatic machine being created.
	 * @param name The name of the new drink.
	 * @return The new drink object.
	 */
	private static Drink addDrink(Machine machine, String name)
	{
		Drink drink = new Drink(name);
		machine.addDrink(drink);
		return drink;
	}
	
	/**
	 * Adds an ingredient with the given quantity to the recipe of the given drink.
	 * @param ingredients A map of ingredient names to ingredient objects.
	 * @param drink The drink to add the ingredient to.
	 * @param ingredientName The name of the ingredient to add to the drink's recipe.
	 * @param quantity The quantity of the ingredient to add.
	 */
	private static void addIngredientToDrink(Map<String, Ingredient> ingredients, Drink drink, String ingredientName, int quantity)
	{
		Ingredient ingredient = ingredients.get(ingredientName);
		drink.addIngredient(ingredient, quantity);
	}
}
