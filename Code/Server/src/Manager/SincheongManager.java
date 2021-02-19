package Manager;

import java.util.Vector;
import java.util.concurrent.Semaphore;

import DAO.DAOSincheong;
import Entity.ESincheong;

public class SincheongManager {
	
	Semaphore semaphore;

	public SincheongManager(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	public Vector<Object> checkSame(Vector<Object> data) {
		Vector<Object> result = new Vector<Object>();
		DAOSincheong daoSincheong = new DAOSincheong("sincheongdata", data.get(0)+"sincheong", semaphore);
		Vector<String> selected = new Vector<String>();
		for (int i = 1; i < 7; i++) {
			selected.add((String)data.get(i));
		}
		result.add(daoSincheong.checkSame((String)data.get(0), selected));
		return result;
	}

	public Vector<Object> checkCredit(Vector<Object> data) {
		DAOSincheong daoSincheong = new DAOSincheong("sincheongdata", data.get(0)+"sincheong", semaphore);
		Vector<Object> result = new Vector<Object>();
		result.add(daoSincheong.checkCredit((String)data.get(0), (String)data.get(1)));
		return result;
	}

	public Vector<Object> checkTime(Vector<Object> data) {
		DAOSincheong daoSincheong = new DAOSincheong("sincheongdata", data.get(0)+"sincheong", semaphore);
		Vector<String> selected = new Vector<String>();
		for (int i = 1; i < 7; i++) {
			selected.add((String)data.get(i));
		}
		Vector<Object> result = new Vector<Object>();
		result.add(daoSincheong.checkTime(selected));
		return result;
	}

	public Vector<Object> addSincheong(Vector<Object> data) {
		DAOSincheong daoSincheong = new DAOSincheong("sincheongdata", data.get(0)+"sincheong", semaphore);
		Vector<String> selected = new Vector<String>();
		for (int i = 1; i < data.size(); i++) {
			selected.add((String)data.get(i));
		}
		daoSincheong.add(selected);
		daoSincheong = new DAOSincheong("professorlecturedata", selected.get(1), semaphore);
		daoSincheong.addStudent(data.get(0).toString());
		daoSincheong = new DAOSincheong("professordata", selected.get(2), semaphore);
		Vector<ESincheong> items = daoSincheong.show();
		int count = 0;
		for (ESincheong item : items) {
			if (selected.get(1).equals(item.getName())) {
				count = Integer.parseInt(item.getTime());
			}
		}
		daoSincheong.plusStudent(selected.get(1), count);
		Vector<Object> result = new Vector<Object>();
		result.add("result");
		return result;
	}

	public Vector<Object> deleteSincheong(Vector<Object> data) {
		DAOSincheong daoSincheong = new DAOSincheong("sincheongdata", data.get(0)+"sincheong", semaphore);
		Vector<String> selected = new Vector<String>();
		String userID = data.get(0).toString();
		data.removeElementAt(0);
		for (Object move:data) {
			selected.add((String)move);
		}
		daoSincheong.deleteSincheong(selected);
		daoSincheong = new DAOSincheong("professorlecturedata", selected.get(1), semaphore);
		daoSincheong.deleteStudent(userID);
		daoSincheong = new DAOSincheong("professordata", selected.get(2), semaphore);
		Vector<ESincheong> items = daoSincheong.show();
		int count = 0;
		for (ESincheong item : items) {
			if (selected.get(1).equals(item.getName())) {
				count = Integer.parseInt(item.getTime());
			}
		}
		daoSincheong.minusStudent(selected.get(1), count);
		Vector<Object> result = new Vector<Object>();
		result.add("success");
		return result;
	}

	public Vector<Object> show(Vector<Object> data) {
		String userID = (String)data.get(0);
		DAOSincheong daoSincheong = new DAOSincheong("sincheongdata", userID+"sincheong", semaphore);
		Vector<ESincheong> items = daoSincheong.show();
		Vector<Object> result = new Vector<Object>();
		for (ESincheong item : items) {
			result.add(item.getNumber());
			result.add(item.getName());
			result.add(item.getProfessor());
			result.add(item.getCredit());
			result.add(item.getDay());
			result.add(item.getTime());
		}
		return result;
	}
	
	public Vector<Object> getStudentList(Vector<Object> data) {
		DAOSincheong daoSincheong = new DAOSincheong("professorlecturedata", data.get(0).toString(), semaphore);
		Vector<ESincheong> items = daoSincheong.show();
		Vector<Object> result = new Vector<Object>();
		for (ESincheong item : items) {
			result.add(item.getNumber());
			result.add(item.getName());
			result.add(item.getProfessor());
			result.add(item.getCredit());
			result.add(item.getDay());
			result.add(item.getTime());
		}
		return result;
	}
}
