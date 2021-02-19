package DAO;

import java.util.Vector;
import java.util.concurrent.Semaphore;

import Entity.Database;
import Entity.ELecture;

public class DAOLecture {

	Database db;
	Semaphore semaphore;

	public DAOLecture(String schema, String table, Semaphore semaphore) {
		this.semaphore = semaphore;
		db = new Database(schema, table);
	}

	public Vector<ELecture> getItems(String fileName) {
		Vector<ELecture> items = new Vector<ELecture>();
		String result = null;
		try {
			semaphore.acquire();
			result = db.showTable("lecture");
			semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (!result.equals("")) {
			String[] splitResult = result.split("\n");
			for (int i = 0; i < splitResult.length; i++) {
				ELecture eLecture = new ELecture();
				String[] data = splitResult[i].split(" ");
				eLecture.setNumber(data[0]);
				eLecture.setName(data[1]);
				eLecture.setProfessor(data[2]);
				eLecture.setCredit(data[3]);
				eLecture.setDay(data[4]);
				eLecture.setTime(data[5]);
				items.add(eLecture);
			}
		}
		return items;
	}

	public Vector<Object> search(String searchType, String keyword, String day) {
		String columnName = null;
		switch (searchType) {
		case "강의명":
			columnName = "name";
			break;
		case "교수명":
			columnName = "professor";
			break;
		}
		Vector<Object> result = db.search(columnName, keyword, day);
		return result;
	}

	public void addLecture(Vector<Object> data) {
		Vector<String> selected = new Vector<String>();
		for(Object object:data) {
			selected.add(object.toString());
		}
		db.addRow(selected);
	}

}
