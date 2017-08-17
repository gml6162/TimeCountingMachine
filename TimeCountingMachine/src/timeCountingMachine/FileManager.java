package timeCountingMachine;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

/*
 * This class only has static method.
 * You must not create FileManager as object.
 * Written by GF
 */

public class FileManager
{
	private static String USER_NAMES_FILE = "userNames.txt";
	private static String SAVED_DATA_FILE = "savedData.txt";
	
	public static HashMap<String, String[]> userData;
	private static HashMap<String, String[]> userRecordData;
	
	public static void setUserRecord(String user, long record)
	{
		String[] temp = userRecordData.get(user);
		userRecordData.replace(user, new String[]{temp[0], temp[1], temp[2], longToString(record)});		
		saveData();
	}
	
	public static void saveData()
	{
		BufferedWriter bufferedWriter = null;
		try
		{
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(SAVED_DATA_FILE), "UTF8"));
			String string = "";
			Set<String> keys = FileManager.userData.keySet();
			
			for(Iterator<String> iterator = keys.iterator(); iterator.hasNext(); ){
				String user = iterator.next();
				String[] value = userRecordData.get(user);
				string = string
						.concat(user)
						.concat("\t")
						.concat(value[0])
						.concat("\t")
						.concat(value[1])
						.concat("\t")
						.concat(value[2])
						.concat("\t")
						.concat(value[3])
						.concat("\n");
			}
			bufferedWriter.write(string);
			bufferedWriter.close();
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}

	public static void loadUserData()
	{	
		String user_names_file = USER_NAMES_FILE;
		String saved_data_file = SAVED_DATA_FILE;
		
		FileReader reader;
		StringBuffer buffer;
		StringTokenizer stringTokenizer;
		
		userData = new HashMap<String, String[]>();
		userRecordData = new HashMap<String, String[]>();
		
		try
		{
			reader = new FileReader(saved_data_file);
			buffer = new StringBuffer();
			int read;
			while ((read = reader.read()) != -1)
			{
				buffer.append((char) read);
				System.out.print((char) read);
			}
			reader.close();
			
			stringTokenizer = new StringTokenizer(buffer.toString());
			while (stringTokenizer.hasMoreTokens())
			{
				String key = stringTokenizer.nextToken();
				String[] temp = {stringTokenizer.nextToken(),stringTokenizer.nextToken(),stringTokenizer.nextToken(),stringTokenizer.nextToken()};
				userData.put(key, new String[]{temp[0], temp[1], temp[2]});
				userRecordData.put(key, new String[]{temp[0], temp[1], temp[2], temp[3]});
			}
			System.out.println("Backup user data.");
		}
		catch (Exception exception)
		{
			// no such file: SAVED_DATA_FILE
			try
			{
				reader = new FileReader(user_names_file);
				buffer = new StringBuffer();
				int read;
				while ((read = reader.read()) != -1)
				{
					buffer.append((char) read);
					System.out.print((char) read);
				}
				reader.close();
				
				stringTokenizer = new StringTokenizer(buffer.toString());
				while (stringTokenizer.hasMoreTokens())
				{
					String[] temp = stringTokenizer.nextToken().split("/");
					userData.put(temp[0], new String[]{temp[1], temp[2], temp[3]});
					userRecordData.put(temp[0], new String[]{temp[1], temp[2], temp[3], longToString(0)});
				}
				System.out.println("Hello.");
			}
			catch (Exception e)
			{
				System.out.print("Maybe not found: " + user_names_file);
				e.printStackTrace();
			}
		}
	}
	
	private static String longToString(long time)
	{
		return String.format("%02d분%02d초%03d", time % 1000, (time / 1000) % 60, (time / (1000 * 60)) % 60);
	}
	
	private static long stringToLong(String time)
	{
		return Long.parseLong(time.substring(0, 2)) * 60000 + Long.parseLong(time.substring(3, 5)) * 1000 + Long.parseLong(time.substring(6, 9));
	}
}