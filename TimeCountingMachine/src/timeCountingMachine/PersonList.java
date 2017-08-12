package timeCountingMachine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PersonList{
	int b;
	String filepath = "주니어명단.txt";
	FileInputStream file;
	public PersonList(StringBuffer buffer){
		try {
			file = new FileInputStream(filepath);
			b = file.read();
			while (b != -1) {
				buffer.append((char) b);
				b = file.read();
			}

		} catch (FileNotFoundException e) {
			System.out.println("Oops : FileNotFoundException");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

