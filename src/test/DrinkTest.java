package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import javamatic.*;

/**
 * This class has JUnit tests for the Drink class.
 */
public class DrinkTest
{
	private static Drink drink;
	
	/**
	 * Creates a new drink before every test case.
	 */
	@Before
	public void setUp()
	{
		drink = new Drink("Coffee");
	}
	
	/**
	 * Tests that the drink initially outputs the correct values.
	 */
	@Test
	public void initialOutput()
	{
		String output = drink.output(1);
		assertEquals("1,Coffee,$0.00,false\n", output);
	}
	
	/**
	 * Tests that setting a drink to be in stock properly updates the corresponding
	 * part of the output.
	 */
	@Test
	public void setStock()
	{
		drink.setInStock(true);
		String output = drink.output(923);
		assertEquals("923,Coffee,$0.00,true\n", output);
	}
	
	/**
	 * Tests that adding one ingredient to the drink properly updates the drink's
	 * total cost in the output.
	 */
	@Test
	public void addOneIngredient()
	{
		Ingredient coffee = new Ingredient("Coffee", 1.00f);
		drink.addIngredient(coffee, 20);
		String output = drink.output(5);
		assertEquals("5,Coffee,$20.00,false\n", output);
	}
	
	/**
	 * Tests that adding multiple ingredients to the drink properly updates the drink's
	 * total cost in the output.
	 */
	@Test
	public void addMultipleIngredients()
	{
		Ingredient coffee = new Ingredient("Coffee", 0.37f);
		Ingredient sugar = new Ingredient("Sugar", 20.22f);
		Ingredient cream = new Ingredient("Cream", 127.88f);
		drink.addIngredient(coffee, 22);
		drink.addIngredient(sugar, 3);
		drink.addIngredient(cream, 7);
		String output = drink.output(432);
		assertEquals("432,Coffee,$963.96,false\n", output);
	}
}
