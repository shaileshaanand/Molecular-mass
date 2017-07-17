public class ElementNotFoundException extends Exception
{
	String Message;
	ElementNotFoundException(String Message)
	{
		this.Message=Message;
	}
	public String message()
	{
		return Message;
	}
}
