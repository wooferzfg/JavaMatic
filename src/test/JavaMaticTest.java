package test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import javamatic.*;

/**
 * This class has black box JUnit tests for the entire JavaMatic application,
 * comparing expected console outputs with actual console outputs.
 */
public class JavaMaticTest
{
	/**
	 * Tests the initial display of the program.
	 */
	@Test
	public void initialDisplay()
	{
		testInputAndOutput("Q\n", "testoutput_initialDisplay.txt");
	}
	
	/**
	 * Tests the output when buying one drink.
	 */
	@Test
	public void buyDrink()
	{
		testInputAndOutput("2\nq\n", "testoutput_buyDrink.txt");
	}
	
	/**
	 * Tests the output when buying many different drinks.
	 */
	@Test
	public void buyManyDrinks()
	{
		testInputAndOutput("6\n5\n4\n3\n2\n1\nq\n", "testoutput_buyManyDrinks.txt");
	}
	
	/**
	 * Tests the output when many different invalid inputs are included.
	 */
	@Test
	public void invalidInputs()
	{
		testInputAndOutput("\n\n7\n-1\n\n\n\nfoijsf\n \nq\n", "testoutput_invalidInputs.txt");
	}
	
	/**
	 * Tests the output when many drinks are bought before and after restocking the machine.
	 */
	@Test
	public void buyWithRestocking()
	{
		testInputAndOutput("1\n1\n1\n1\nR\n1\n3\nq\n", "testoutput_buyWithRestocking.txt");
	}
	
	/**
	 * Tests the JavaMatic application by running it with the given input string.
	 * The output of the application is compared to the expected output, which is stored in a file.
	 * @param input The input to feed to the application.
	 * @param expectedOutputFileName The name of the file that contains the expected output of the application.
	 */
	private void testInputAndOutput(String input, String expectedOutputFileName)
	{
		String expectedOutput = readOutputFile(expectedOutputFileName);
		expectedOutput = convertNewlines(expectedOutput);
		
		InputStream inputStream = new ByteArrayInputStream(input.getBytes());
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		PrintStream outputStream = new PrintStream(byteOutputStream);

		JavaMatic.runMachine(inputStream, outputStream);
		
		String actualOutput = byteOutputStream.toString();
		actualOutput = convertNewlines(actualOutput);

		assertEquals(expectedOutput, actualOutput);
	}
	
	/**
	 * Reads the contents of a file into a string.
	 * @param fileName The name of the file to read from.
	 * @return A string that contains the contents of the file.
	 */
	private static String readOutputFile(String fileName)
	{
		try 
		{
			byte[] contents = Files.readAllBytes(Paths.get("src/test/", fileName));
			return new String(contents);
		}
		catch (IOException e)
		{
			System.out.println(String.format("Could not read file: %s", fileName));
		}
		return null;
	}
	
	/**
	 * Converts all the newlines in the given string to remove carriage returns.
	 * @param text The string to convert.
	 * @return The new string with all newlines converted.
	 */
	private static String convertNewlines(String text)
	{
		return text.replaceAll("\r\n", "\n");
	}
}
