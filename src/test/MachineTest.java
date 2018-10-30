package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import javamatic.*;

/**
 * This class has JUnit tests for the Machine class.
 */
public class MachineTest
{
	private static Machine machine;

	/**
	 * Creates a new machine before every test case.
	 * The machine has 3 drinks and 2 ingredients.
	 */
	@Before
	public void setUp()
	{
		machine = new Machine();
		
		Ingredient coffee = new Ingredient("Coffee", 0.5f);
		Ingredient sugar = new Ingredient("Sugar", 1.5f);
		Ingredient decaf = new Ingredient("Decaf", 0.75f);
		machine.addIngredient(coffee);
		machine.addIngredient(sugar);
		machine.addIngredient(decaf);
		
		Drink normalCoffee = new Drink("Normal Coffee");
		normalCoffee.addIngredient(coffee, 1);
		normalCoffee.addIngredient(sugar, 3);
		Drink decafCoffee = new Drink("Decaf Coffee");
		decafCoffee.addIngredient(decaf, 2);
		decafCoffee.addIngredient(sugar, 2);
		machine.addDrink(normalCoffee);
		machine.addDrink(decafCoffee);
		
		machine.restockInventory();
	}
	
	/**
	 * Tests that the machine initially outputs the correct inventory, menu,
	 * and total number of drinks.
	 */
	@Test
	public void initialOutput()
	{
		String inventory = machine.outputInventory();
		String menu = machine.outputMenu();
		int totalDrinks = machine.getNumberOfDrinks();
		assertEquals("Inventory:\nCoffee,10\nDecaf,10\nSugar,10\n", inventory);
		assertEquals("Menu:\n1,Decaf Coffee,$4.50,true\n2,Normal Coffee,$5.00,true\n", menu);
		assertEquals(2, totalDrinks);
	}
	
	/**
	 * Tests that buying a drink from the machine correctly updates the inventory
	 * by subtracting the ingredient quantities that were dispensed.
	 */
	@Test
	public void buyDrink()
	{
		boolean buyResult = machine.buyDrink(2);
		String drinkName = machine.getDrinkName(2);
		String inventory = machine.outputInventory();
		String menu = machine.outputMenu();
		assertEquals(true, buyResult);
		assertEquals("Normal Coffee", drinkName);
		assertEquals("Inventory:\nCoffee,9\nDecaf,10\nSugar,7\n", inventory);
		assertEquals("Menu:\n1,Decaf Coffee,$4.50,true\n2,Normal Coffee,$5.00,true\n", menu);
	}
	
	/**
	 * Tests that buying multiple drinks from the machine correctly updates the inventory
	 * after each drink. Also makes sure that the menu gets updated correctly when drinks
	 * are out of stock because there are no longer enough ingredients for them.
	 */
	@Test
	public void buyMultipleDrinks()
	{
		boolean buyResult = machine.buyDrink(2);
		String drinkName = machine.getDrinkName(2);
		String inventory = machine.outputInventory();
		String menu = machine.outputMenu();
		assertEquals(true, buyResult);
		assertEquals("Normal Coffee", drinkName);
		assertEquals("Inventory:\nCoffee,9\nDecaf,10\nSugar,7\n", inventory);
		assertEquals("Menu:\n1,Decaf Coffee,$4.50,true\n2,Normal Coffee,$5.00,true\n", menu);
		
		buyResult = machine.buyDrink(2);
		drinkName = machine.getDrinkName(2);
		inventory = machine.outputInventory();
		menu = machine.outputMenu();
		assertEquals(true, buyResult);
		assertEquals("Normal Coffee", drinkName);
		assertEquals("Inventory:\nCoffee,8\nDecaf,10\nSugar,4\n", inventory);
		assertEquals("Menu:\n1,Decaf Coffee,$4.50,true\n2,Normal Coffee,$5.00,true\n", menu);
		
		buyResult = machine.buyDrink(1);
		drinkName = machine.getDrinkName(1);
		inventory = machine.outputInventory();
		menu = machine.outputMenu();
		assertEquals(true, buyResult);
		assertEquals("Decaf Coffee", drinkName);
		assertEquals("Inventory:\nCoffee,8\nDecaf,8\nSugar,2\n", inventory);
		assertEquals("Menu:\n1,Decaf Coffee,$4.50,true\n2,Normal Coffee,$5.00,false\n", menu);
		
		buyResult = machine.buyDrink(2);
		drinkName = machine.getDrinkName(2);
		inventory = machine.outputInventory();
		menu = machine.outputMenu();
		assertEquals(false, buyResult);
		assertEquals("Normal Coffee", drinkName);
		assertEquals("Inventory:\nCoffee,8\nDecaf,8\nSugar,2\n", inventory);
		assertEquals("Menu:\n1,Decaf Coffee,$4.50,true\n2,Normal Coffee,$5.00,false\n", menu);
		
		buyResult = machine.buyDrink(1);
		drinkName = machine.getDrinkName(1);
		inventory = machine.outputInventory();
		menu = machine.outputMenu();
		assertEquals(true, buyResult);
		assertEquals("Decaf Coffee", drinkName);
		assertEquals("Inventory:\nCoffee,8\nDecaf,6\nSugar,0\n", inventory);
		assertEquals("Menu:\n1,Decaf Coffee,$4.50,false\n2,Normal Coffee,$5.00,false\n", menu);
	}
	
	/**
	 * Tests that restocking the inventory after buying drinks correctly resets the quantity
	 * of every ingredient in the inventory to the maximum. Also makes sure that the menu gets
	 * updated correctly to show drinks as in stock again. 
	 */
	@Test
	public void testRestockInventory()
	{
		machine.buyDrink(1);
		machine.buyDrink(1);
		machine.buyDrink(2);
		machine.buyDrink(2);
		
		boolean buyResult = machine.buyDrink(1);
		assertEquals(false, buyResult);
		
		buyResult = machine.buyDrink(2);
		assertEquals(false, buyResult);
		
		String inventory = machine.outputInventory();
		String menu = machine.outputMenu();
		assertEquals("Inventory:\nCoffee,8\nDecaf,6\nSugar,0\n", inventory);
		assertEquals("Menu:\n1,Decaf Coffee,$4.50,false\n2,Normal Coffee,$5.00,false\n", menu);
		
		machine.restockInventory();
		inventory = machine.outputInventory();
		menu = machine.outputMenu();
		assertEquals("Inventory:\nCoffee,10\nDecaf,10\nSugar,10\n", inventory);
		assertEquals("Menu:\n1,Decaf Coffee,$4.50,true\n2,Normal Coffee,$5.00,true\n", menu);
		
		buyResult = machine.buyDrink(1);
		assertEquals(true, buyResult);
		
		buyResult = machine.buyDrink(2);
		assertEquals(true, buyResult);
		
		inventory = machine.outputInventory();
		menu = machine.outputMenu();
		assertEquals("Inventory:\nCoffee,9\nDecaf,8\nSugar,5\n", inventory);
		assertEquals("Menu:\n1,Decaf Coffee,$4.50,true\n2,Normal Coffee,$5.00,true\n", menu);
		
		machine.restockInventory();
		inventory = machine.outputInventory();
		menu = machine.outputMenu();
		assertEquals("Inventory:\nCoffee,10\nDecaf,10\nSugar,10\n", inventory);
		assertEquals("Menu:\n1,Decaf Coffee,$4.50,true\n2,Normal Coffee,$5.00,true\n", menu);
	}
}
