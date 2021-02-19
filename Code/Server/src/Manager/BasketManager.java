package Manager;

import java.util.Vector;
import java.util.concurrent.Semaphore;

import DAO.DAOBasket;
import Entity.EBasket;

public class BasketManager {
	
	Semaphore semaphore;
	
	public BasketManager(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	public Vector<Object> checkExcist(Vector<Object> data) {
		Vector<Object> result = new Vector<Object>();
		DAOBasket daoBasket = new DAOBasket((String)data.get(6), semaphore);
		Vector<String> selected = new Vector<String>();
		for (int i = 0; i < 6; i++) {
			selected.add((String)data.get(i));
		}
		if (daoBasket.checkExcist(selected)) {
			result.add("1");
		} else {
			result.add("0");
		}
		return result;
	}

	public Vector<Object> addBasket(Vector<Object> data) {
		DAOBasket daoBasket = new DAOBasket((String)data.get(0), semaphore);
		Vector<String> selected = new Vector<String>();
		for (int i = 1; i < 7; i++) {
			selected.add((String)data.get(i));
		}
		daoBasket.addBasket(selected);
		Vector<Object> result = new Vector<Object>();
		result.add("success");
		return result;
	}
	
	public Vector<Object> deleteBasket(Vector<Object> data) {
		DAOBasket daoBasket = new DAOBasket((String)data.get(0), semaphore);
		Vector<String> selected = new Vector<String>();
		for (int i = 1; i < 7; i++) {
			selected.add((String)data.get(i));
		}
		daoBasket.deleteBasket(selected);
		Vector<Object> result = new Vector<Object>();
		result.add("success");
		return result;
	}
	
	public Vector<Object> showBasket(Vector<Object> data) {
		Vector<Object> result = new Vector<Object>();
		String userID = (String)data.get(0);
		DAOBasket daoBasket = new DAOBasket(userID, semaphore);
		Vector<EBasket> basketV = daoBasket.showBasket(userID);
		for(int i=0;i<basketV.size();i++) {
			result.add(basketV.get(i).getNumber());
			result.add(basketV.get(i).getName());
			result.add(basketV.get(i).getProfessor());
			result.add(basketV.get(i).getCredit());
			result.add(basketV.get(i).getDay());
			result.add(basketV.get(i).getTime());
		}
		return result;
	}

}
