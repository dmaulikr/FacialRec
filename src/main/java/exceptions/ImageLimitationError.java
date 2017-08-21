package exceptions;

/**
 * Class Details:-
 * Author: Sarhad
 * User: sarha
 * Date: 06-Jul-17
 * Time : 11:19 PM
 * Project Name: Candy
 * Class Name: CustomExceptions
 */
public class ImageLimitationError extends Exception
{
	
	public ImageLimitationError()
	{
	
	}
	
	public ImageLimitationError(String message)
	{
		super(message);
	}
	
	public ImageLimitationError(Throwable cause)
	{
		super(cause);
	}
	
	public ImageLimitationError(String message, Throwable cause)
	{
		super(message, cause);
	}
	
}
