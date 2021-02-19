package DAO;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.Semaphore;

import javax.imageio.ImageIO;

import Entity.Database;

public class DAOProfile {

	private Database db;
	Semaphore semaphore;
	private String schema;

	public DAOProfile(String schema, String table, Semaphore semaphore) {
		this.schema = schema;
		this.semaphore = semaphore;
		db = new Database(schema, table);
	}

	public void addProfile(Vector<String> profile) {
		try {
			semaphore.acquire();
			db.addRow(profile);
			semaphore.release();
			String userID = profile.get(0);
			String PW = profile.get(2);

			db = new Database(schema, "picturelist");
			Vector<String> data = new Vector<String>();
			data.add(userID);
			BufferedImage defaultImage = ImageIO.read(new File("color&image\\Picture.png"));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(defaultImage, "png", baos);
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			
			File newF = new File("color&image\\"+userID+"Picture.png");
			FileOutputStream fos = new FileOutputStream("color&image\\"+userID+"Picture.png");
			fos.write(imageInByte);
			fos.flush();
			
			String path = newF.getAbsolutePath();
			path = path.replace("\\", "/");
			data.add(path);
			semaphore.acquire();
			db.addRow(data);
			semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void fixProfile(Vector<String> Profile) {
		for (int i = 1; i < Profile.size(); i++) {
			try {
				semaphore.acquire();
				db.rewrite(1, i, Profile.get(0), Profile.get(i));
				semaphore.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public String checkIDExcist(String ID) {
		String result = null;
		try {
			semaphore.acquire();
			result = db.showTable("student");
			semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String[] splitResult = result.split("\n");
		if (ID.equals("")) {
			return "";
		} else {
			for (int i = 0; i < splitResult.length; i++) {
				String[] data = splitResult[i].split(" ");
				if (data[0].equals(ID)) {
					return "exist";
				}
			}
		}
		return "OK";
	}

	public Vector<Object> getProfile(String ID) {
		Vector<Object> returnValue = new Vector<Object>();
		String result = null;
		try {
			semaphore.acquire();
			result = db.showTable("student");
			semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String[] splitResult = result.split("\n");
		for (int i = 0; i < splitResult.length; i++) {
			String[] datas = splitResult[i].split(" ");
			if (datas[0].equals(ID)) {
				for (String data : datas) {
					returnValue.add(data);
				}
			}
		}
		return returnValue;
	}

	public void setPicture(String userID, byte[] imgByte) {
		String filePath = null;
		try {
			semaphore.acquire();
			String result = db.showTable("studentPic");
			semaphore.release();
			String[] splitResult = result.split("\n");
			for (int i = 0; i < splitResult.length; i++) {
				String[] datas = splitResult[i].split(" ");
				if (datas[0].equals(userID)) {
					filePath = datas[1];
				}
			}
			ByteArrayInputStream inputStream = new ByteArrayInputStream(imgByte);
			BufferedImage bufferedImage = ImageIO.read(inputStream);
			ImageIO.write(bufferedImage, "png", new File(filePath));
			filePath = filePath.replace("\\", "/");
			System.out.println(filePath);
			semaphore.acquire();
			db.rewrite(0, 1, userID, filePath);
			semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Vector<Object> getPicture(String userID) {
		String filePath = null;
		byte[] imgByte = null;
		try {
			semaphore.acquire();
			String result = db.showTable("studentPic");
			semaphore.release();
			String[] splitResult = result.split("\n");
			for (String datas : splitResult) {
				String[] data = datas.split(" ");
				if (data[0].equals(userID)) {
					filePath = data[1];
				}
			}
			filePath = filePath.replace("/", "\\");
			File f = new File(filePath);
			BufferedImage image = ImageIO.read(f);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", baos);
			baos.flush();
			imgByte = baos.toByteArray();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Vector<Object> result = new Vector<Object>();
		result.add(imgByte);
		return result;
	}
}
