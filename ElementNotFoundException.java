public class ElementNotFoundException extends Exception
{
	String M;
	ElementNotFoundException(String m)
	{
		M=m;
	}
	public String message()
	{
		return M;
	}
}