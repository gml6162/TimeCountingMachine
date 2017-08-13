package timeCountingMachine;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;

/*

 * This class only has static method.

 * You must not create FileManager as object.

 */

public class FileManager {
	//private static StringBuffer buffer; // no use

	public static void saveData() {

		FileWriter reader;

		try {
			reader = new FileWriter("savedData.txt");
			reader.write("���옣");
			reader.append('!');
			reader.close();
		}

		catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static void loadUserNames() {
		StringBuffer buffer = new StringBuffer();

		FileReader reader;
		StringTokenizer stringTokenizer;
		int id = 0;
		String name = new String();
		String[] content = {"","",""};

		try {
			reader = new FileReader("userNames.txt");
			int read;
			while ((read = reader.read()) != -1) {
				buffer.append((char) read);
				System.out.print((char) read);
			}

			reader.close();
		}
		catch (Exception exception) {
			System.out.print("Maybe not found: userNames.txt");
			exception.printStackTrace();
		}

		stringTokenizer = new StringTokenizer(buffer.toString(), "\n", false);
		while (stringTokenizer.hasMoreTokens()) {
			String tmp = stringTokenizer.nextToken();
			if (name.length() == 0) {
				name = tmp;
			}
			else if(content[0].length() == 0) {
				content[0] = tmp;
			}
			else if(content[1].length() == 0) {
				content[1] = tmp;
			}
			else if(content[2].length() == 0) {
				content[2] = tmp;
				Main.userData.put(name, content);
				name = new String();
				content = new String[3];
			}
			
		}
	}

}