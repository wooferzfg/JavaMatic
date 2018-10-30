package javamatic;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * This class takes input from the user, sends it to the machine, and then
 * takes the machine's output and sends it back to the user.
 */
public class JavaMatic
{
	/**
	 * The main entry point for the program.
	 * @param args The command line arguments, which are not used.
	 */
	public static void main(String[] args)
	{
		runMachine(System.in, System.out);
	}
	
	/**
	 * Runs the JavaMatic machine, taking input from the input stream and 
	 * sending output to the output stream.
	 * @param inputStream The stream to take input from.
	 * @param outputStream The stream to send output to.
	 */
	public static void runMachine(InputStream inputStream, PrintStream outputStream)
	{
		Machine machine = JavaMaticFactory.createMachine();
		Scanner scanner = new Scanner(inputStream);

		boolean keepRunning = true;
		boolean printMenu = true;
		while (keepRunning)
		{
			if (printMenu)
			{
				printInventoryAndMenu(machine, outputStream);
			}

			String line = scanner.nextLine();
			if (line.length() > 0)
			{
				keepRunning = parseInput(machine, outputStream, line);
				printMenu = true;
			}
			else
			{
				printMenu = false;
			}
		}
		
		scanner.close();
	}
	
	/**
	 * Prints the machine's inventory and drinks menu to the output stream.
	 * @param machine The JavaMatic machine.
	 * @param outputStream The stream to print output to.
	 */
	private static void printInventoryAndMenu(Machine machine, PrintStream outputStream)
	{
		outputStream.print(machine.outputInventory());
		outputStream.print(machine.outputMenu());
	}

	/**
	 * Parses a line of user input.
	 * The "r" command restocks the machine's inventory.
	 * The "q" command quits out of the application.
	 * Entering a number that corresponds to a drink will buy that drink.
	 * @param machine The JavaMatic machine.
	 * @param outputStream The stream to print output to.
	 * @param line The line of user input.
	 * @return Whether to continue running the input loop.
	 */
	private static boolean parseInput(Machine machine, PrintStream outputStream, String line)
	{
		if (line.toLowerCase().equals("r"))
		{
			machine.restockInventory();
		}
		else if (line.toLowerCase().equals("q"))
		{
			return false;
		}
		else
		{
			boolean validSelection = false;

			try
			{
				int drinkSelection = Integer.parseInt(line);
				if (drinkSelection >= 1 && drinkSelection <= machine.getNumberOfDrinks())
				{
					selectDrink(machine, outputStream, drinkSelection);
					validSelection = true;
				}
			}
			catch (NumberFormatException ex) {	}

			if (!validSelection)
			{
				outputStream.println(String.format("Invalid selection: %s", line));
			}
		}
		return true;
	}
	
	/**
	 * Buys a drink from the machine if the drink is in stock.
	 * @param machine The JavaMatic machine.
	 * @param outputStream The stream to print output to.
	 * @param drinkSelection The number of the drink, which should be a valid number between 1 and the total number of drinks.
	 */
	private static void selectDrink(Machine machine, PrintStream outputStream, int drinkSelection)
	{
		boolean validDrink = machine.buyDrink(drinkSelection);
		String drinkName = machine.getDrinkName(drinkSelection);
		String outputString;
		if (validDrink)
		{
			outputString = String.format("Dispensing: %s", drinkName);
		}
		else
		{
			outputString = String.format("Out of stock: %s", drinkName);
		}
		outputStream.println(outputString);
	}
}
