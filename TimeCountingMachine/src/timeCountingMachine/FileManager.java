package timeCountingMachine;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

/*
 * This class only has static method.
 * You must not create FileManager as object.
 */

public class FileManager
{
	public static HashMap<String, String[]> userData;
	
	public static void saveData()
	{
		FileWriter reader;
		try
		{
			reader = new FileWriter("savedData.txt");
			reader.write("test");
			//reader.append('!');
			reader.close();
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}

	public static void loadUserData(String textFile)
	{
		FileReader reader;
		StringBuffer buffer = new StringBuffer();
		userData = new HashMap<String, String[]>();

		try
		{
			reader = new FileReader(textFile);
			int read;
			while ((read = reader.read()) != -1)
			{
				buffer.append((char) read);
				System.out.print((char) read);
			}
			reader.close();
		}
		catch (Exception exception)
		{
			System.out.print("Maybe not found: " + textFile);
			exception.printStackTrace();
		}

		StringTokenizer stringTokenizer = new StringTokenizer(buffer.toString(), "\n", false);
		while (stringTokenizer.hasMoreTokens())
			userData.put(stringTokenizer.nextToken(), new String[]{stringTokenizer.nextToken(), stringTokenizer.nextToken(), stringTokenizer.nextToken()});
	}
}