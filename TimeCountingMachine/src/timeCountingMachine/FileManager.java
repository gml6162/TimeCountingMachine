package timeCountingMachine;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

/*
 * This class only has static method.
 * You must not create FileManager as object.
 */

public class FileManager
{
	public static HashMap<String, String[]> userData;
	private static HashMap<String, String[]> userRecordData;
	
	public static void setUserRecord(String user, long record)
	{
		String[] temp = userRecordData.get(user);
		userRecordData.replace(user, new String[]{temp[0], temp[1], temp[2], String.valueOf(record)});
		
		
		saveData();
	}
	
	public static void setBackupRanking(long record)
	{
		
	}
	
	public static void saveData()
	{
		FileWriter reader;
		try
		{
			reader = new FileWriter("savedData.txt");
			String string = "DATA\n";
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
						.concat(longToString(Long.parseLong(value[3])))
						.concat("\n");
			}
			reader.write(string);
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
		userRecordData = new HashMap<String, String[]>();
		
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

		StringTokenizer stringTokenizer = new StringTokenizer(buffer.toString());
		while (stringTokenizer.hasMoreTokens())
		{
			String[] temp = stringTokenizer.nextToken().split("/");
			userData.put(temp[0], new String[]{temp[1], temp[2], temp[3]});
			userRecordData.put(temp[0], new String[]{temp[1], temp[2], temp[3], "0"});
		}
	}
	
	//public long getRecord(String user){
	//	long record = userRecordData.get(user)[3];
	//	return 
	//}
	
	private static String longToString(long time)
	{
		long ms = time % 1000;
		long se = (time / 1000) % 60;
		long mi = (time / (1000 * 60)) % 60;

		return String.format("%02d분 %02d초 %03d", mi, se, ms);
	}
}