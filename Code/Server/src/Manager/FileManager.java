package Manager;

import java.util.Vector;
import java.util.concurrent.Semaphore;

import DAO.DAODirectory;
import Entity.EDirectory;

public class FileManager {

	Semaphore semaphore;

	public FileManager(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	public Vector<Object> getFile(Vector<Object> data) {
		String fileName = (String) data.get(0);
		DAODirectory daoDirectory = new DAODirectory("lecturedata", fileName, semaphore);
		Vector<EDirectory> resultV = daoDirectory.getItems();
		Vector<Object> result = new Vector<Object>();
		for (EDirectory a : resultV) {
			result.add(a.getName());
		}
		return result;
	}

	public Vector<Object> findFile(Vector<Object> data) {
		DAODirectory daoDirectory = new DAODirectory("lecturedata", (String) data.get(0), semaphore);
		Vector<EDirectory> directoryV = daoDirectory.getItems();
		int index = Integer.parseInt((String) data.get(1));
		Vector<Object> result = new Vector<Object>();
		result.add(directoryV.get(index).getHyperLink());
		return result;
	}
}
