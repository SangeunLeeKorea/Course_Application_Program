package Manager;

import java.util.Vector;
import java.util.concurrent.Semaphore;

import DAO.DAOFreeclass;

public class FreeclassManager {
	
	public FreeclassManager(Semaphore semaphore) {}
	
	public Vector<Object> showFreeclass(Vector<Object> data){
		Vector<Object> result = new Vector<Object>();
		DAOFreeclass daoFreeclass = new DAOFreeclass();
		String fileName = (String)data.get(0);
		result = daoFreeclass.showFreeclass(fileName);
		return result;
	}
	
	public Vector<Object> addFreeclass(Vector<Object> data){
		DAOFreeclass daoFreeclass = new DAOFreeclass();
		String fileName = (String)data.get(0);
		data.remove(0);
		Vector<String> para = new Vector<String>();
		for(Object object:data) {
			para.add((String)object);
		}
		daoFreeclass.addFreeclass(fileName, para);
		
		Vector<Object> result = new Vector<Object>();
		result.add("success");
		return result;
	}
	
	public Vector<Object> deleteFreeclass(Vector<Object> data){
		DAOFreeclass daoFreeclass = new DAOFreeclass();
		String fileName = (String)data.get(0);
		data.remove(0);
		Vector<String> para = new Vector<String>();
		for(Object object:data) {
			para.add((String)object);
		}
		daoFreeclass.deleteFreeclass(fileName, para);
		
		Vector<Object> result = new Vector<Object>();
		result.add("success");
		return result;
	}

}
