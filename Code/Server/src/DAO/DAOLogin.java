package DAO;

import java.util.concurrent.Semaphore;

import Entity.Database;

public class DAOLogin {

	private Database db;
	Semaphore semaphore;

	public static boolean found;

	public DAOLogin(String schema, String table, Semaphore semaphore) {
		this.semaphore = semaphore;
		db = new Database(schema, table);
	}

	private boolean validateUser(String userID, String userPW, int idcount, int pwcount) {
		found = false;
		String result = null;
		try {
			semaphore.acquire();
			result = db.showTable("student");
			semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String[] splitResult = result.split("\n");
		for (int i = 0; !found && i<splitResult.length; i++) {
			String[] data = splitResult[i].split(" ");
			if (data[idcount].equals(userID) && data[pwcount].contentEquals(userPW)) {
				found = true;
			}
		}
		return found;
	}

	public String authenticate(String UserID, String UserPW, int idcount, int pwcount) {
		boolean result = this.validateUser(UserID, UserPW, idcount, pwcount);
		if (result) {
			return "1";
		} else {
			return "0";
		}
	}

	public String findID(String Name) {
		String result = null;
		try {
			semaphore.acquire();
			result = db.showTable("student");
			semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String[] splitResult = result.split("\n");
		for (int i=0;i<splitResult.length;i++) {
			String[] data = splitResult[i].split(" ");
			if (data[1].equals(Name)) {
				return data[0];
			}
		}
		return "error";
	}

	public String findUser(String Name, String ID) {
		String result = null;
		try {
			semaphore.acquire();
			result = db.showTable("student");
			semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String[] splitResult = result.split("\n");
		if (ID == null) {
			for (int i = 0; i < splitResult.length; i++) {
				String[] data = splitResult[i].split(" ");
				if (data[0].equals(Name)) {
					return "success";
				}
			}
		} else {
			for (int i = 0; i < splitResult.length; i++) {
				String[] data = splitResult[i].split(" ");
				if (data[1].equals(Name) && data[0].equals(ID)) {
					return "success";
				}
			}
		}
		return "error";
	}

	public void reWrite(int compareI, int newI, String ID, String newPW) {
		try {
			semaphore.acquire();
			db.rewrite(compareI, newI, ID, newPW);
			semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
