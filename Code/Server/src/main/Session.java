package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.Semaphore;

public class Session extends Thread {

	Socket socket;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	Semaphore semaphore;
	ConnectionAdministrator administrator;
	
	int i = 0;

	public Session(Socket socket, Semaphore semaphore) {
		try {
			this.socket = socket;
			this.semaphore = semaphore;
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			administrator = new ConnectionAdministrator(socket, oos, ois);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		boolean logined = false;
		boolean run = true;
		while (run) {
			try {
				Vector<Object> data = (Vector<Object>)ois.readObject();
				if (administrator.isAlive()) {
					administrator.stop();
				}
				String className = (String)data.get(0);
				if (className.equals("quit")) {
					administrator.finishing();
					run = false;
				} else if (!administrator.getRunnable()) {
					Vector<Object> result = new Vector<Object>();
					result.add("timeOut");
					logined=false;
					if (i!=0) {
						result.removeAllElements();
						result.add("timeOutDone");
						administrator.setRunnable(true);
					}
					i++;
					oos.writeObject(result);
					oos.flush();
				} else if (className.equals("logout")) {
					logined = false;
					administrator.setRunnable(true);
					Vector<Object> result = new Vector<Object>();
					oos.writeObject(result);
					oos.flush();
				} else if (administrator.getRunnable()){
					String methodName = (String)data.get(1);
					if (methodName.equals("login")) {
						logined = true;
						i=0;
					}
					data.remove(0);
					data.remove(0);
					
					Class myClass = Class.forName("Manager." + className);
					Constructor constructor = myClass.getConstructor(Semaphore.class);
					Object instance = constructor.newInstance(semaphore);
					Method method = myClass.getMethod(methodName, Vector.class);
					Vector<Object> result = (Vector<Object>) method.invoke(instance, data);
					oos.writeObject(result);
					oos.flush();
					if (logined) {
						administrator = new ConnectionAdministrator(socket, oos, ois);
						administrator.start();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
}
