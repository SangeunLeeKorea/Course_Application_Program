package main;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;

public class Stub {

	Socket socket;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	ActionListener handler;
	JButton timeout;

	public Stub(ActionListener handler) {
		try {
			socket = new Socket("localhost", 8923);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			this.handler = handler;
			timeout = new JButton();
			timeout.addActionListener(handler);
			timeout.setActionCommand("timeOut");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Vector<Object> runSession(String className, String methodName, Vector<Object> datas) {
		Vector<Object> object = new Vector<Object>();
		Vector<Object> result = null;
		try {
			object.add(className);
			object.add(methodName);
			for (Object data : datas) {
				object.add(data);
			}
			oos.writeObject(object);
			oos.flush();
			result = (Vector<Object>) ois.readObject();
			if (result.size()>0&&result.get(0).equals("timeOut")) {
				timeout.doClick();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void quit() {
		Vector<Object> object = new Vector<Object>();
		try {
			object.add("quit");
			oos.writeObject(object);
			oos.close();
			ois.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
