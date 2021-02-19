package DAO;

import java.util.Vector;
import java.util.concurrent.Semaphore;

import Entity.Database;
import Entity.EBasket;

public class DAOBasket {
	
	private Database db;
	Semaphore semaphore;
	
	public DAOBasket(String userID, Semaphore semaphore) {
		this.semaphore = semaphore;
		db = new Database("Basketdata", userID+"basket");
	}

	public void addBasket(Vector<String> Selected){
		try {
			semaphore.acquire();
			db.showTable("lecture");
			db.addRow(Selected);
			semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Vector<EBasket> showBasket(String UserID){
		Vector<EBasket> basketList = new Vector<EBasket>();
		String result = null;
		try {
			semaphore.acquire();
			result = db.showTable("lecture");
			semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (result.equals("")) {
			return basketList;
		}
		String[] splitResult = result.split("\n");
		for(int i=0;i<splitResult.length;i++) {
			String[] data = splitResult[i].split(" ");
			EBasket eBasket = new EBasket();
			eBasket.setNumber(data[0]);
			eBasket.setName(data[1]);
			eBasket.setProfessor(data[2]);
			eBasket.setCredit(data[3]);
			eBasket.setDay(data[4]);
			eBasket.setTime(data[5]);
			basketList.add(eBasket);
		}
		return basketList;
	}

	public boolean checkExcist(Vector<String> Selected){
		boolean checked = false;
		String result = null;
		try {
			semaphore.acquire();
			result = db.showTable("lecture");
			semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (result.equals("")) {
			return true;
		}
		String[] splitResult = result.split("\n");
		for(int i=0;i<splitResult.length;i++) {
			String[] data = splitResult[i].split(" ");
			if ((Selected.get(0).equals(data[0])&&Selected.get(1).equals(data[1])&&Selected.get(2).equals(data[2]))) {
				checked = true;
			}
		}
		if (checked) {
			return false;
		} else {
			return true;
		}
	}
	
	public void deleteBasket(Vector<String> Selected) {
		try {
			semaphore.acquire();
			db.deleteRow(Selected, 1);
			semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
