package Manager;

import java.util.Vector;
import java.util.concurrent.Semaphore;

import DAO.DAOProfile;

public class ProfileManager {

	Semaphore semaphore;

	public ProfileManager(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	public Vector<Object> fixProfile(Vector<Object> data) {
		DAOProfile daoProfile = new DAOProfile("profiledata", "studentlist", semaphore);
		Vector<String> selected = new Vector<String>();
		for (int i = 0; i < 6; i++) {
			selected.add((String) data.get(i));
		}
		daoProfile.fixProfile(selected);
		Vector<Object> result = new Vector<Object>();
		result.add("success");
		return result;
	}

	public Vector<Object> checkID(Vector<Object> data) {
		String ID = (String) data.get(0);
		DAOProfile daoProfile = new DAOProfile("profiledata", "studentlist", semaphore);
		Vector<Object> result = new Vector<Object>();
		result.add(daoProfile.checkIDExcist(ID));
		return result;
	}

	public Vector<Object> join(Vector<Object> data) {
		DAOProfile daoProfile = new DAOProfile("profiledata", "studentlist", semaphore);
		Vector<String> vectorData = new Vector<String>();
		for (Object object : data) {
			vectorData.add((String) object);
		}
		daoProfile.addProfile(vectorData);
		Vector<Object> result = new Vector<Object>();
		result.add("success");
		return result;
	}

	public Vector<Object> getProfile(Vector<Object> data) {
		String ID = (String) data.get(0);
		DAOProfile daoProfile = new DAOProfile("profiledata", "studentlist", semaphore);
		Vector<Object> result = daoProfile.getProfile(ID);
		return result;
	}

	public Vector<Object> setPicture(Vector<Object> data) {
		DAOProfile daoProfile = new DAOProfile("profiledata", "picturelist", semaphore);
		String userID = (String)data.get(0);
		byte[] imgByte = (byte[])data.get(1);
		daoProfile.setPicture(userID, imgByte);
		Vector<Object> result = new Vector<Object>();
		result.add("success");
		return result;
	}

	public Vector<Object> getPicture(Vector<Object> data) {
		String userID = (String)data.get(0);
		DAOProfile daoProfile = new DAOProfile("profiledata", "picturelist", semaphore);
		Vector<Object> result = daoProfile.getPicture(userID);
		return result;
	}
}
