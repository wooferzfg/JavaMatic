package javamatic;

/**
 * This class describes an ingredient that the JavaMatic's drink are made of.
 */
public class Ingredient implements Comparable<Ingredient>
{
	private String name;
	private float cost;
	
	/**
	 * Constructs a new ingredient.
	 * @param name The name of the ingredient.
	 * @param cost The cost of the ingredient.
	 */
	public Ingredient(String name, float cost)
	{
		this.name = name;
		this.cost = cost;
	}
	
	/**
	 * @return The name of the ingredient.
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * @return The cost of the ingredient.
	 */
	public float getCost()
	{
		return cost;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Ingredient other)
	{
		return name.compareTo(other.getName());
	}
}
