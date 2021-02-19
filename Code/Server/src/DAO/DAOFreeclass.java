package DAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class DAOFreeclass {

	public Vector<Object> showFreeclass(String fileName) {
		Vector<Object> result = new Vector<Object>();
		try {
			File f = new File(fileName);
			if (!f.exists()) {
				f.createNewFile();
			}
			Scanner scan = new Scanner(f);
			while(scan.hasNext()) {
				result.add(scan.next());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void addFreeclass(String fileName, Vector<String> datas) {
		try {
			File f = new File(fileName);
			if (!f.exists()) {
				f.createNewFile();
			}
			FileWriter fw = new FileWriter(f, true);
			fw.write(datas.get(0) + " " + datas.get(1) + " " + datas.get(2)+"\n");
			fw.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteFreeclass(String fileName, Vector<String> selected) {
		try {
			File f = new File(fileName);
			if (!f.exists()) {
				f.createNewFile();
			}
			Vector<String> datas = new Vector<String>();
			Scanner scan = new Scanner(f);
			while(scan.hasNext()) {
				String name = scan.next();
				String day = scan.next();
				String time = scan.next();
				if (!(name.equals(selected.get(0))&&day.equals(selected.get(1))&&time.equals(selected.get(2)))){
					datas.add(name);
					datas.add(day);
					datas.add(time);
				}
			}
			FileWriter fw = new FileWriter(f);
			for (int i=0;i<datas.size()/3;i=i+3) {
				fw.write(datas.get(i) + " " + datas.get(i+1) + " " + datas.get(i+2)+"\n");
			}
			fw.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
