package Manager;

import java.util.Vector;
import java.util.concurrent.Semaphore;

import DAO.DAOLecture;
import Entity.ELecture;

public class LectureManager {
	
	Semaphore semaphore;

	public LectureManager(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	public Vector<Object> getItems(Vector<Object> data) {
		String userID = (String)data.get(0);
		Vector<Object> result = new Vector<Object>();
		DAOLecture daoLecture = new DAOLecture("lecturedata", (String)data.get(1), semaphore);
		Vector<ELecture> items = daoLecture.getItems(userID);
		for (ELecture item : items) {
			result.add(item.getNumber());
			result.add(item.getName());
			result.add(item.getProfessor());
			result.add(item.getCredit());
			result.add(item.getDay());
			result.add(item.getTime());
		}
		return result;
	}
	
	public Vector<Object> search(Vector<Object> data){
		String tableName = data.get(0).toString();
		String searchType = data.get(1).toString();
		String keyword = data.get(2).toString();
		String day = data.get(3).toString();
		DAOLecture daoLecture = new DAOLecture("lecturedata", tableName, semaphore);
		Vector<Object> result = daoLecture.search(searchType, keyword, day);
		return result;
	}
	
	public Vector<Object> addLecture(Vector<Object> data){
		String tableName = data.get(0).toString();
		String professor = data.get(3).toString();
		data.removeElementAt(0);
		System.out.println(tableName);
		DAOLecture daoLecture = new DAOLecture("lecturedata", tableName, semaphore);
		daoLecture.addLecture(data);
		daoLecture = new DAOLecture("professordata", professor, semaphore);
		daoLecture.addLecture(data);
		Vector<Object> result = new Vector<Object>();
		return result;
	}
	
	public Vector<Object> getProfessorLecture(Vector<Object> data){
		String userID = (String)data.get(0);
		Vector<Object> result = new Vector<Object>();
		DAOLecture daoLecture = new DAOLecture("professordata", userID, semaphore);
		Vector<ELecture> items = daoLecture.getItems(userID);
		for (ELecture item : items) {
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
