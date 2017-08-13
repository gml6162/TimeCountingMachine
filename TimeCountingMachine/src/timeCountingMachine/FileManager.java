package timeCountingMachine;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;

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
			reader.write("hello");
			reader.append('!');
			reader.close();
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}

	public static HashMap<String, Object> loadUserData()
	{
		StringBuffer buffer = new StringBuffer();
		FileReader reader;
		HashMap<String, Object> userData = new HashMap<String, Object>();

		try
		{
			reader = new FileReader("userNames.txt");
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
			System.out.print("Maybe not found: userNames.txt");
			exception.printStackTrace();
			return userData;
		}

		StringTokenizer stringTokenizer = new StringTokenizer(buffer.toString(), "\n", false);
		while (stringTokenizer.hasMoreTokens())
		{
			userData.put(stringTokenizer.nextToken(), {stringTokenizer.nextToken(), stringTokenizer.nextToken(), stringTokenizer.nextToken()});
		}
		return userData;
	}
}