package org.example;

public class ExceptionFinally
{
	public static void main(String[] args) throws Exception
	{
		try
		{
			return;
		}
		catch (Exception e)
		{
			System.out.println("here A");
			throw new Exception();
		}
		finally
		{
			System.out.println("there");
		}

	}
}
