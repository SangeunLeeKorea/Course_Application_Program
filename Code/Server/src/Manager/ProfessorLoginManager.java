package Manager;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Vector;
import java.util.concurrent.Semaphore;

import DAO.DAOLogin;

public class ProfessorLoginManager {
	
	Semaphore semaphore;

	public ProfessorLoginManager(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	public Vector<Object> login(Vector<Object> data) {
		String ID = (String)data.get(0);
		String PW = (String)data.get(1);
		DAOLogin daoLogin = new DAOLogin("professordata", "professorlist", semaphore);
		Vector<Object> result = new Vector<Object>();
		result.add(daoLogin.authenticate(ID, PW, 0, 1));
		return result;
	}
	
	public Vector<Object> findUser(Vector<Object> data) {
		DAOLogin daoLogin = new DAOLogin("professordata", "professorlist", semaphore);
		Vector<Object> result = new Vector<Object>();
		result.add(daoLogin.findUser((String)data.get(0), null));
		return result;
	}

	public Vector<Object> rewrite(Vector<Object> data) {
		String Name = (String)data.get(0);
		String newPW = (String)data.get(1);
		DAOLogin fixStudentList = new DAOLogin("professordata", "professorlist", semaphore);
		fixStudentList.reWrite(0, 1, Name, newPW);
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
