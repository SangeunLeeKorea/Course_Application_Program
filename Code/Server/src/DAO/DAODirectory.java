package DAO;

import java.util.Vector;
import java.util.concurrent.Semaphore;

import Entity.Database;
import Entity.EDirectory;

public class DAODirectory {
	
	private Database db;
	Semaphore semaphore;
	
	public DAODirectory(String SchemaName, String TableName, Semaphore semaphore) {
		this.semaphore = semaphore;
		db = new Database(SchemaName, TableName);
	}

	public Vector<EDirectory> getItems(){
		Vector<EDirectory> items = new Vector<EDirectory>();
		String result = null;
		try {
			semaphore.acquire();
			result = db.showTable("directory");
			semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String[] splitData = result.split("\n");
		for (int i=0;i<splitData.length;i++) {
			EDirectory eDirectory = new EDirectory();
			String[] directoryData = splitData[i].split(" ");
			eDirectory.setNumber(directoryData[0]);
			eDirectory.setName(directoryData[1]);
			eDirectory.setHyperLink(directoryData[2]);
			items.add(eDirectory);
		}
		return items;

	}
}
