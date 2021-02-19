package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Vector;

import javax.swing.JOptionPane;

import Frame.LoginFrame;
import Frame.LoginedFrame;
import Frame.ProfessorLoginedFrame;
import Frame.TimeTableFrame;

public class Client {

	Stub stub;

	LoginFrame loginFrame;
	LoginedFrame loginedFrame;
	ProfessorLoginedFrame professorFrame;
	TimeTableFrame timeTable;
	ActionHandler handler;

	public Client() {
		handler = new ActionHandler();
		stub = new Stub(handler);
		loginFrame = new LoginFrame(handler, stub);
		loginedFrame = new LoginedFrame(handler, stub);
		professorFrame = new ProfessorLoginedFrame(handler, stub);
		loginedFrame.hide();
		professorFrame.hide();
	}

	public void loginSuccess(LoginFrame login, boolean professor) {
		if (professor) {
			Vector<Object> datas = new Vector<Object>();
			datas.add(login.getUserID());
			datas.add(login.getUserPW());
			Vector<Object> result = stub.runSession("ProfessorLoginManager", "login", datas);
			int check = Integer.parseInt((String) result.get(0));
			if (check == 1) {
				login.hide();
				professorFrame.show();
				professorFrame.setHello(login.getUserID());
				professorFrame.setUserID(login.getUserID());
				JOptionPane.showMessageDialog(null, "�α��ο� �����Ͽ����ϴ�.", "�α��� �Ϸ�", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "��ϵ��� ���� ���̵� Ȥ�� �ùٸ��� ���� ��й�ȣ�Դϴ�.", "�α��� ����", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			Vector<Object> datas = new Vector<Object>();
			String base64ID = encAES(login.getUserID());
			String base64PW = encAES(login.getUserPW());
			datas.add(base64ID);
			datas.add(base64PW);
			Vector<Object> result = stub.runSession("LoginManager", "login", datas);
			int check = Integer.parseInt((String) result.get(0));
			if (check == 1) {
				login.hide();
				loginedFrame.show();
				loginedFrame.setHello(login.getUserID());
				loginedFrame.setUserID(login.getUserID());
				JOptionPane.showMessageDialog(null, "�α��ο� �����Ͽ����ϴ�.", "�α��� �Ϸ�", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "��ϵ��� ���� ���̵� Ȥ�� �ùٸ��� ���� ��й�ȣ�Դϴ�.", "�α��� ����", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// ��ȣȭ
	public String encAES(String str) {
		String enStr = null;
		try {
			Encoder encoder = Base64.getEncoder();
			enStr = new String(encoder.encode(str.getBytes("UTF-8")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return enStr;
	}

	class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == loginFrame.LoginB) {
				loginFrame.setUserIDPW();
				loginSuccess(loginFrame, loginFrame.isProfessorLogin());
			} else if (e.getActionCommand().equals("timeOut") || e.getSource() == loginedFrame.logout || e.getSource() == professorFrame.logout) {
				if (e.getActionCommand().equals("timeOut")) {
					JOptionPane.showMessageDialog(null, "�������� Ȱ������ �ʾ� �α׾ƿ� �Ǿ����ϴ�.", "�α׾ƿ�", JOptionPane.PLAIN_MESSAGE);
				}
				Vector<Object> datas = new Vector<Object>();
				stub.runSession("logout", "logout", datas);
				loginFrame.show();
				loginFrame.setPW();
				loginedFrame.hide();
				professorFrame.hide();
			}
		}
	}
}
