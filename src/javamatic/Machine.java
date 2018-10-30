package javamatic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * This class describes a JavaMatic machine, handling all of the internal logic
 * for dispensing drinks and restocking inventory.
 */
public class Machine
{
	private SortedMap<Ingredient, Integer> inventory;
	private List<Drink> drinks;
	
	private final int MAX_QUANTITY = 10;
	
	/**
	 * Constructs a new machine.
	 */
	public Machine()
	{
		this.inventory = new TreeMap<Ingredient, Integer>();
		this.drinks = new ArrayList<Drink>();
	}
	
	/**
	 * @return The number of different drinks in the machine's menu, including drinks
	 * that are out of stock.
	 */
	public int getNumberOfDrinks()
	{
		return drinks.size();
	}
	
	/**
	 * Adds a new ingredient to the machine's inventory.
	 * @param ingredient The ingredient to add.
	 */
	public void addIngredient(Ingredient ingredient)
	{
		inventory.put(ingredient, MAX_QUANTITY);
	}
	
	/**
	 * Adds a new drink to the machine's menu.
	 * @param drink The drink to add.
	 */
	public void addDrink(Drink drink)
	{
		drinks.add(drink);
		Collections.sort(drinks);
	}
	
	/**
	 * Buys the drink with the given drink number.
	 * @param drinkNumber The number of the drink to buy, where 1 is the first drink that is listed.
	 * @return Whether the drink could be successfully dispensed.
	 */
	public boolean buyDrink(int drinkNumber)
	{
		Drink drink = drinks.get(drinkNumber - 1);
		if (drink.isInStock())
		{
			for (Ingredient ingredient : drink.getIngredients())
			{
				int quantityAvailable = inventory.get(ingredient);
				int quantityRequired = drink.getIngredientQuantity(ingredient);
				int newQuantity = quantityAvailable - quantityRequired;
				inventory.put(ingredient, newQuantity);
			}
			updateDrinkStocks();
			return true;
		}
		return false;
	}
	
	/**
	 * Restocks the machine's inventory, setting every ingredient's quantity to the max quantity.
	 */
	public void restockInventory()
	{
		for (Ingredient ingredient : inventory.keySet())
		{
			inventory.put(ingredient, MAX_QUANTITY);
		}
		updateDrinkStocks();
	}
	
	/**
	 * @param drinkNumber The number of the drink, where 1 is the first drink that is listed.
	 * @return The name of the drink with the given drink number.
	 */
	public String getDrinkName(int drinkNumber)
	{
		Drink drink = drinks.get(drinkNumber - 1);
		return drink.getName();
	}
	
	/**
	 * @return A string describing the machine's current inventory. Each ingredient is listed
	 * with its name and current quantity available.
	 */
	public String outputInventory()
	{
		StringBuilder output = new StringBuilder();
		output.append("Inventory:\n");
		for (Entry<Ingredient, Integer> entry : inventory.entrySet())
		{
			Ingredient ingredient = entry.getKey();
			int quantityAvailable = entry.getValue();
			output.append(String.format("%s,%d\n", ingredient.getName(), quantityAvailable));
		}
		return output.toString();
	}
	
	/**
	 * @return A string describing the machine's drink menu. Each drink is listed with its
	 * drink number, name, cost, and whether it is currently in stock.
	 */
	public String outputMenu()
	{
		StringBuilder output = new StringBuilder();
		output.append("Menu:\n");
		for (int i = 0; i < drinks.size(); i++)
		{
			Drink drink = drinks.get(i);
			int drinkId = i + 1;
			output.append(drink.output(drinkId));
		}
		return output.toString();
	}
	
	/**
	 * Updates whether each drink is currently in stock.
	 */
	private void updateDrinkStocks()
	{
		for (Drink drink : drinks)
		{
			boolean shouldBeInStock = getUpdatedDrinkStock(drink);
			drink.setInStock(shouldBeInStock);
		}
	}
	
	/**
	 * Based on the machine's current inventory, calculates whether the given drink
	 * should currently be in stock or not.
	 * @param drink The drink for which to calculate in stock status.
	 * @return Whether the given drink should be in stock.
	 */
	private boolean getUpdatedDrinkStock(Drink drink)
	{
		for (Ingredient ingredient : drink.getIngredients())
		{
			int quantityAvailable = inventory.get(ingredient);
			int quantityRequired = drink.getIngredientQuantity(ingredient);
			if (quantityRequired > quantityAvailable)
			{
				return false;
			}
		}
		return true;
	}
}
