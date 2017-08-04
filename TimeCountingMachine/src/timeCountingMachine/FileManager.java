package timeCountingMachine;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
 * This class only has static method.
 * You must not create FileManager as object.
 */
public class FileManager
{
	public static void saveData()
	{
		FileWriter reader;
		try
		{
			reader = new FileWriter("savedData.txt");
			reader.write("¿‘√‚∑¬!");
			reader.append('!'); 
			reader.close();
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}
	
	public static void loadUserNames()
	{
		FileReader reader;
		try
		{
			reader = new FileReader("userNames.txt");
			int read;
			while((read = reader.read()) != -1)
			{
				System.out.print((char)read);
			}
			reader.close();
		}
		catch (Exception exception)
		{
			System.out.print("Maybe not found: userNames.txt");
			exception.printStackTrace();
		}
	}
}
