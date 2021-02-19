package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class Server {
	
	ServerSocket serverSocket;
	public Server() {
		try {
			serverSocket = new ServerSocket(8923);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("Client Á¢¼Ó");
				Semaphore semaphore = new Semaphore(1);
				Session session = new Session(socket, semaphore);
				session.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
