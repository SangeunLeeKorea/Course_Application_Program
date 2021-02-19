package Manager;

import java.util.Vector;
import java.util.concurrent.Semaphore;

import DAO.DAOMemo;

public class MemoManager {

	Semaphore semaphore;

	public MemoManager(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	public Vector<Object> saveMemo(Vector<Object> datas) {
		String userID = (String) datas.get(0);
		String text = (String) datas.get(1);
		DAOMemo daoMemo = new DAOMemo(userID);
		daoMemo.saveMemo(text);
		Vector<Object> result = new Vector<Object>();
		result.add("success");
		return result;
	}

	public Vector<Object> deleteAll(Vector<Object> datas) {
		String userID = (String) datas.get(0);
		DAOMemo daoMemo = new DAOMemo(userID);
		daoMemo.deleteAll();
		Vector<Object> result = new Vector<Object>();
		result.add("success");
		return result;
	}

	public Vector<Object> showMemo(Vector<Object> datas) {
		Vector<Object> result = new Vector<Object>();
		String userID = (String) datas.get(0);
		DAOMemo daoMemo = new DAOMemo(userID);
		String text = daoMemo.showMemo();
		result.add(text);
		return result;
	}
}