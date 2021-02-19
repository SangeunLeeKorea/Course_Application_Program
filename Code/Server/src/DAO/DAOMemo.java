package DAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DAOMemo {
	
	File f;

	public DAOMemo(String userID) {
		f = new File("Memo//"+userID+"Memo.txt");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void saveMemo(String text) {
		try {
			FileWriter fw = new FileWriter(f);
			fw.write(text);
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String showMemo() {
		String result = "";
		try {
			Scanner scan = new Scanner(f);
			while(scan.hasNext()) {
				result += scan.nextLine();
				if (scan.hasNext()) {
					result += "\n";
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void deleteAll() {
		try {
			FileWriter fw = new FileWriter(f);
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
