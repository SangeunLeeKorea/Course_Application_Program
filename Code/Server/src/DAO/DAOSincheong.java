package DAO;

import java.util.Vector;
import java.util.concurrent.Semaphore;

import Entity.Database;
import Entity.ESincheong;

public class DAOSincheong {

	private Database db;
	Semaphore semaphore;

	public DAOSincheong(String schema, String table, Semaphore semaphore) {
		this.semaphore = semaphore;
		db = new Database(schema, table);
	}

	public Vector<ESincheong> show() {
		Vector<ESincheong> items = new Vector<ESincheong>();
		String result = null;
		try {
			semaphore.acquire();
			result = db.showTable("lecture");
			semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (result.equals("")) {
			return items;
		}
		String[] splitResult = result.split("\n");
		for (int i = 0; i < splitResult.length; i++) {
			ESincheong eSincheong = new ESincheong();
			String[] data = splitResult[i].split(" ");
			eSincheong.setNumber(data[0]);
			eSincheong.setName(data[1]);
			eSincheong.setProfessor(data[2]);
			eSincheong.setCredit(data[3]);
			eSincheong.setDay(data[4]);
			eSincheong.setTime(data[5]);
			items.add(eSincheong);
		}
		return items;
	}

	public void add(Vector<String> Selected) {
		try {
			semaphore.acquire();
			db.addRow(Selected);
			semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void deleteSincheong(Vector<String> Selected) {
		try {
			semaphore.acquire();
			db.deleteRow(Selected, 1);
			semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}

	public int checkSame(String UserID, Vector<String> selected) {
		String result = null;
		try {
			semaphore.acquire();
			result = db.showTable("lecture");
			semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (result.equals("")) {
			return 1;
		}
		String[] splitResult = result.split("\n");
		for (int i = 0; i < splitResult.length; i++) {
			String[] data = splitResult[i].split(" ");
			if (selected.get(0).equals(data[0]) && selected.get(1).equals(data[1]) && selected.get(2).equals(data[2])) {
				return 0;
			}
		}
		return 1;
	}

	public int checkCredit(String UserID, String credit) {
		int TotalCredit = 0;
		String result = null;
		try {
			semaphore.acquire();
			result = db.showTable("lecture");
			semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (result.equals("")) {
			return 1;
		}
		String[] splitResult = result.split("\n");
		for (int i = 0; i < splitResult.length; i++) {
			String[] data = splitResult[i].split(" ");
			TotalCredit += Integer.parseInt(data[3]);
		}
		int intCredit = Integer.parseInt(credit);
		if (intCredit + TotalCredit <= 18) {
			return 1;
		} else {
			return 0;
		}
	}

	public int checkTime(Vector<String> Selected) {
		String[] SelectedDay = Selected.get(4).split("");
		String[] SelectedTime = Selected.get(5).split("");
		int SelectedStart = Integer.parseInt(SelectedTime[0] + SelectedTime[1] + SelectedTime[2] + SelectedTime[3]);
		int SelectedEnd = Integer.parseInt(SelectedTime[5] + SelectedTime[6] + SelectedTime[7] + SelectedTime[8]);

		String result = null;
		try {
			semaphore.acquire();
			result = db.showTable("lecture");
			semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (result.equals("")) {
			return 1;
		}
		String[] splitResult = result.split("\n");
		for (int i = 0; i < splitResult.length; i++) {
			String[] data = splitResult[i].split(" ");
			String[] day = data[4].split("");
			String[] time = data[5].split("");
			int start = Integer.parseInt(time[0] + time[1] + time[2] + time[3]);
			int end = Integer.parseInt(time[5] + time[6] + time[7] + time[8]);
			if (!((SelectedStart >= end) || (SelectedEnd <= start))) {
				for (String a : SelectedDay) {
					for (String b : day) {
						if (a.equals(b)) {
							return 0;
						}
					}
				}
			}
		}
		return 1;
	}

	public void addStudent(String userID) {
		Database student = new Database("profiledata", "studentlist");
		String students = student.showTable("sincheongList");
		String[] dataArray = students.split("\n");
		for (String data : dataArray) {
			String[] profile = data.split(" ");
			if (profile[0].equals(userID)) {
				db.checkExcist("sincheongList");
				Vector<String> selected = new Vector<String>();
				for (int i = 0; i < profile.length; i++) {
					if (i!=2) {
						selected.add(profile[i]);
					}
				}
				db.addRow(selected);
			}
		}
	}

	public void deleteStudent(String userID) {
		db.checkExcist("student");
		Vector<String> selected = new Vector<String>();
		selected.add(userID);
		db.deleteRow(selected, 0);
	}

	public void plusStudent(String lectureName, int student) {
		db.rewrite(1, 5, lectureName, Integer.toString(student+1));
	}
	
	public void minusStudent(String lectureName, int student) {
		db.rewrite(1, 5, lectureName, Integer.toString(student-1));
	}
}