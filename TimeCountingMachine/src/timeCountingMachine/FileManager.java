package timeCountingMachine;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
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
	
	public static HashMap<String, String[]> userData = new HashMap<String, String[]>();
	private static HashMap<String, String[]> userRecordData = new HashMap<String, String[]>();
	private static ArrayList<String[]> rankData = new ArrayList<>();

	public static void setUserRecord(String user, long record)
	{
		String[] temp = userRecordData.get(user);
		if (record < stringToLong(userRecordData.get(user)[3]))
		{
			userRecordData.replace(user, new String[]{temp[0], temp[1], temp[2], longToString(record)});
			saveData();
			setRankData();
		}
	}
	
	public static void setRankData()
	{
		rankData = new ArrayList<>();
		Set<String> keys = FileManager.userRecordData.keySet();
		String name;
		int i;
		long time;
		for(Iterator<String> iterator = keys.iterator(); iterator.hasNext(); )
		{
			name = iterator.next();
			time = stringToLong(userRecordData.get(name)[3]);
			for (i = 0; i < rankData.size(); i++)
			{
				if (time < stringToLong(rankData.get(i)[1]))
				{
					rankData.add(i, new String[] {name, userRecordData.get(name)[3]});
					break;
				}
			}
			if (i == rankData.size())
			{
				rankData.add(i, new String[] {name, userRecordData.get(name)[3]});
			}
		}
	}
	
	public static String[][] getRankDataAsStringArray()
	{
		String[][] returns = new String[rankData.size()][2];
		int i = 0;
		for(String[] strings : rankData)
			returns[i++] = strings;
		return returns;
	}
	
	public static void saveData()
	{
		FileWriter writer;
		try
		{
			writer = new FileWriter(SAVED_DATA_FILE);
			String string = "";
			Set<String> keys = FileManager.userData.keySet();
			writer.write(string);
			for(Iterator<String> iterator = keys.iterator(); iterator.hasNext(); ){
				String user = iterator.next();
				String[] value = userRecordData.get(user);
				string = new String()
						.concat(user)
						.concat("/")
						.concat(value[0])
						.concat("/")
						.concat(value[1])
						.concat("/")
						.concat(value[2])
						.concat("/")
						.concat(value[3])
						.concat(System.lineSeparator());
				writer.append(string);
			}
			writer.close();
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
				System.out.print(read);
			}
			reader.close();
			
			stringTokenizer = new StringTokenizer(buffer.toString(), System.lineSeparator());
			while (stringTokenizer.hasMoreTokens())
			{
				String[] temp = stringTokenizer.nextToken().split("/");
				userData.put(temp[0], new String[]{temp[1], temp[2], temp[3]});
				userRecordData.put(temp[0], new String[]{temp[1], temp[2], temp[3], temp[4]});
			}
			setRankData();
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
				
				stringTokenizer = new StringTokenizer(buffer.toString(), System.lineSeparator());
				while (stringTokenizer.hasMoreTokens())
				{
					String[] temp = stringTokenizer.nextToken().split("/");
					userData.put(temp[0], new String[]{temp[1], temp[2], temp[3]});
					userRecordData.put(temp[0], new String[]{temp[1], temp[2], temp[3], longToString(300000)});
				}
				setRankData();
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
		return String.format("%02d분%02d초%03d", time / 60000, (time / 1000) % 60, time % 1000);
	}
	
	private static long stringToLong(String time)
	{
		return Long.parseLong(time.substring(0, 2)) * 60000 + Long.parseLong(time.substring(3, 5)) * 1000 + Long.parseLong(time.substring(6, 9));
	}
}