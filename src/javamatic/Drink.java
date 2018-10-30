package javamatic;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * This class describes a drink that the JavaMatic can dispense.
 */
public class Drink implements Comparable<Drink>
{
	private String name;
	private Map<Ingredient, Integer> ingredients;
	private boolean inStock;
	
	/**
	 * Constructs a new drink.
	 * @param name The name of the drink to construct.
	 */
	public Drink(String name)
	{
		this.name = name;
		this.ingredients = new HashMap<Ingredient, Integer>();
		this.inStock = false;
	}
	
	/**
	 * @return The name of the drink.
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * @return The set of all the ingredients in the drink.
	 */
	public Set<Ingredient> getIngredients()
	{
		return ingredients.keySet();
	}
	
	/**
	 * @param ingredient The ingredient to retrieve the quantity for.
	 * @return The quantity of the given ingredient that the drink requires.
	 */
	public int getIngredientQuantity(Ingredient ingredient)
	{
		return ingredients.get(ingredient);
	}
	
	/**
	 * @return Whether the drink is currently in stock.
	 */
	public boolean isInStock()
	{
		return inStock;
	}

	/**
	 * Adds a new ingredient with the given quantity to the drink's recipe.
	 * @param ingredient The ingredient to add to the drink's recipe.
	 * @param quantity The quantity of the ingredient that is required for the drink.
	 */
	public void addIngredient(Ingredient ingredient, int quantity)
	{
		ingredients.put(ingredient, quantity);
	}
	
	/**
	 * Sets whether the drink is currently in stock.
	 * @param inStock Whether the drink is currently in stock.
	 */
	public void setInStock(boolean inStock)
	{
		this.inStock = inStock;
	}
	
	/**
	 * @param drinkNumber The drink number, where 1 is the first drink that is listed.
	 * @return A string that describes the drink number, name, cost, and in stock status of the drink.
	 */
	public String output(int drinkNumber)
	{
		float cost = getTotalCost();
		String costString = formatDollarAmount(cost);
		return String.format("%d,%s,%s,%b\n", drinkNumber, name, costString, inStock);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Drink other)
	{
		return name.compareTo(other.getName());
	}
	
	/**
	 * @return The total cost of the drink, based on the costs and quantities of the ingredients.
	 */
	private float getTotalCost()
	{
		float totalCost = 0;
		for (Entry<Ingredient, Integer> entry : ingredients.entrySet())
		{
			Ingredient ingredient = entry.getKey();
			int amount = entry.getValue();
			float cost = ingredient.getCost();

			totalCost += cost * amount;
		}
		return totalCost;
	}

	/**
	 * Formats a numerical value (such as 1.2) as a dollar amount (such as $1.20).
	 * @param amount The numerical value of the number of dollars.
	 * @return A dollar amount formatted as currency.
	 */
	private static String formatDollarAmount(float amount)
	{
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		return formatter.format(amount);
	}
}
