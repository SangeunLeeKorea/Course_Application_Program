package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionAdministrator extends Thread{
	
	Socket socket;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	int sec = 1000;

	boolean runnable = true;
	
	public ConnectionAdministrator(Socket socket, ObjectOutputStream oos, ObjectInputStream ois) {
		this.socket = socket;
		this.oos = oos;
		this.ois = ois;
	}
	
	public void run() {
		try {
			this.sleep(5*sec);
			runnable = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void finishing() {
		try {
			ois.close();
			oos.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean getRunnable() {
		return runnable;
	}
	public void setRunnable(boolean runnable) {
		this.runnable = runnable;
	}
}
