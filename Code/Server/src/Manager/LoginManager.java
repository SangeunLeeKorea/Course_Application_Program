package Manager;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Vector;
import java.util.concurrent.Semaphore;

import DAO.DAOLogin;

public class LoginManager {
	
	Semaphore semaphore;

	public LoginManager(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	public Vector<Object> login(Vector<Object> data) {
		String base64ID = (String)data.get(0);
		String base64PW = (String)data.get(1);
		System.out.println(base64ID);
		System.out.println(base64PW);
		String ID = decAES(base64ID);
		String PW = decAES(base64PW);
		DAOLogin daoLogin = new DAOLogin("profileData", "studentlist", semaphore);
		Vector<Object> result = new Vector<Object>();
		result.add(daoLogin.authenticate(ID, PW, 0, 2));
		return result;
	}

	public Vector<Object> findID(Vector<Object> data) {
		String name = (String)data.get(0);
		DAOLogin daoLogin = new DAOLogin("profileData", "studentlist", semaphore);
		Vector<Object> result = new Vector<Object>();
		result.add(daoLogin.findID(name));
		return result;
	}

	public Vector<Object> findUser(Vector<Object> data) {
		DAOLogin daoLogin = new DAOLogin("profileData", "studentlist", semaphore);
		Vector<Object> result = new Vector<Object>();
		result.add(daoLogin.findUser((String)data.get(0), (String)data.get(1)));
		return result;
	}

	public Vector<Object> rewrite(Vector<Object> data) {
		String Name = (String)data.get(0);
		String newPW = (String)data.get(1);
		DAOLogin fixStudentList = new DAOLogin("profileData", "studentlist", semaphore);
		fixStudentList.reWrite(1, 2, Name, newPW);
		Vector<Object> result = new Vector<Object>();
		result.add("success");
		return result;
	}
	
	// º¹È£È­
	public String decAES(String enStr) {
		String decStr = null;
		try {
			Decoder decoder = Base64.getDecoder();
			decStr = new String(decoder.decode(enStr.getBytes("UTF-8")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decStr;
	}
}
