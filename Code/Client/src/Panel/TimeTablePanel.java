package Panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.Stub;

public class TimeTablePanel extends JPanel {

	Vector<String> Sincheonglist = new Vector<String>();
	Vector<String> Freeclasslist = new Vector<String>();

	String[] day;
	int start, forTime;
	int Gap = 20;
	int width = 100;
	int height = 100;
	Graphics g;

	BufferedImage table;

	String userID;
	String fileName;
	Stub stub;

	public TimeTablePanel(String userID, Stub stub) {
		this.userID = userID;
		this.fileName = "Freeclass\\" + userID + "Freeclass.txt";
		this.stub = stub;
		this.setSize(width * 5 + Gap, height * 11 + Gap);
		
		updateTable();
		Sincheonglist.clear();
		Freeclasslist.clear();

		for (int xLine = 0; xLine < 6; xLine++) {
			g.drawLine(Gap + xLine * width, 0, Gap + xLine * width, Gap + height * 11);
		}
		showImage();
	}

	public void showImage() {
		ImageIcon image = new ImageIcon(table);
		JLabel imageLabel = new JLabel(image);
		this.add(imageLabel);
	}

	public Color ChooseColor(int i) {
		switch (i % 5) {
		case 0:
			return new Color(197, 226, 246);
		case 1:
			return new Color(153, 224, 231);
		case 2:
			return new Color(255, 246, 246);
		case 3:
			return new Color(252, 181, 187);
		case 4:
			return new Color(255, 218, 219);
		}
		return null;
	}

	public void setSincheong() {
		Vector<Object> datas = new Vector<Object>();
		datas.add(userID);
		Vector<Object> result = (Vector<Object>)stub.runSession("SincheongManager", "show", datas);
		if (!result.get(0).equals("timeOut")) {
			for (int i = 0; i < result.size()/6; i++) {
				String data = "";
				for (int j = i*6;j <i*6+6;j++) {
					if (j == i*6) {
						data=(String)result.get(j);
					} else {
						data += " "+(String)result.get(j);
					}
				}
				Sincheonglist.add(data);
			}
		}
	}

	public void setFreeclass() {
		Vector<Object> datas = new Vector<Object>();
		datas.add(fileName);
		Vector<Object> result = stub.runSession("FreeclassManager", "showFreeclass", datas);
		if (!((String)result.get(0)).equals("timeOut")) {
			for (int i = 0; i < result.size()/3; i++) {
				String data = "";
				for (int j = i*3;j <i*3+3;j++) {
					if (j == i*3) {
						data=(String)result.get(j);
					} else {
						data += " "+(String)result.get(j);
					}
				}
				Freeclasslist.add(data);
			}
		}
	}

	public String setRectSincheong(String data) {
		String[] splitData = data.split(" ");
		String name = splitData[1];
		String[] time = splitData[5].split("");
		day = splitData[4].split("");
		start = (Integer.parseInt(time[0] + time[1]) - 9) * 60 + Integer.parseInt(time[2] + time[3]);
		forTime = (Integer.parseInt(time[5] + time[6]) - 9) * 60 + Integer.parseInt(time[7] + time[8]) - start;
		return name;
	}

	public String setRectFreeclass(String data) {
		String[] splitData = data.split(" ");
		String name = splitData[0];
		day = splitData[1].split("");
		String[] time = splitData[2].split("");
		start = (Integer.parseInt(time[0] + time[1]) - 9) * 60 + Integer.parseInt(time[2] + time[3]);
		forTime = (Integer.parseInt(time[5] + time[6]) - 9) * 60 + Integer.parseInt(time[7] + time[8]) - start;
		return name;
	}

	public void drawLecture(int color, int i, String name, Graphics g) {
		// 글자 6개까지 OK
		g.setColor(ChooseColor(color));
		g.fillRect(Gap + i * width, Gap + start * 5 / 3, width, forTime * 5 / 3);
		g.setColor(Color.GRAY);
		g.drawRect(Gap + i * width, Gap + start * 5 / 3, width, forTime * 5 / 3);
		if (name.length() <= 6) {
			g.drawString(name, Gap + i * width + 8, Gap + start * 5 / 3 + forTime);
		} else {
			String[] nameSplit = name.split("");
			Vector<String> nameSix = new Vector<String>();
			for (int index = 0; index < nameSplit.length; index++) {
				if (index % 6 == 0) {
					nameSix.add(nameSplit[index]);
				} else {
					nameSix.setElementAt(nameSix.get(index / 6) + nameSplit[index], index / 6);
				}
			}
			for (int index = 0; index < nameSix.size(); index++) {
				g.drawString(nameSix.get(index), Gap + i * width + 8, Gap + start * 5 / 3 + forTime + 20 * index);
			}
		}
	}
	
	public void saveImage() {
		try {
			File f = new File("Saved//" + userID + "TimeTable.PNG");
			for (int i = 2; f.exists(); i++) {
				f = new File("Saved//" + userID + "TimeTable(" + i + ").PNG");
			}
			f.createNewFile();
			ImageIO.write(table, "PNG", f);
			JOptionPane.showMessageDialog(null, "시간표를 사진으로 저장하였습니다.", "사진 저장 완료", JOptionPane.PLAIN_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateTable() {
		table = new BufferedImage(width * 5 + Gap + 1, height * 11 + Gap + 1, BufferedImage.TYPE_INT_RGB);
		g = table.getGraphics();
		g.clearRect(0, 0, width * 5 + Gap + 1, height * 11 + Gap + 1);
		// 세로: 1시간 = 100pixel
		// 가로: 가로 100pixel
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width * 5 + Gap, height * 11 + Gap);
		g.setColor(Color.BLACK);
		g.drawLine(0, 0, 0, Gap + height * 11);
		g.drawLine(0, 0, Gap + width * 5, 0);
		for (int yLine = 0; yLine < 12; yLine++) {
			g.drawLine(0, Gap + yLine * height, Gap + width * 5, Gap + yLine * height);
		}
		int x = 7, y = 16;
		g.setFont(new Font("고딕체", Font.PLAIN, 15));
		g.drawString("월", Gap + 20, y);
		g.drawString("화", Gap + width + 20, y);
		g.drawString("수", Gap + width * 2 + 20, y);
		g.drawString("목", Gap + width * 3 + 20, y);
		g.drawString("금", Gap + width * 4 + 20, y);
		g.drawString("9", x, Gap + 30);
		g.drawString("10", x / 2, Gap + 30 + height);
		g.drawString("11", x / 2, Gap + 30 + height * 2);
		g.drawString("12", x / 2, Gap + 30 + height * 3);
		g.drawString("1", x, Gap + 30 + height * 4);
		g.drawString("2", x, Gap + 30 + height * 5);
		g.drawString("3", x, Gap + 30 + height * 6);
		g.drawString("4", x, Gap + 30 + height * 7);
		g.drawString("5", x, Gap + 30 + height * 8);
		g.drawString("6", x, Gap + 30 + height * 9);
		g.drawString("7", x, Gap + 30 + height * 10);
		setSincheong();
		setFreeclass();
		int color = 0;
		for (int i = 0; i < Sincheonglist.size(); i++) {
			String name = setRectSincheong(Sincheonglist.get(i));
			for (int j = 0; j < day.length; j++) {
				System.out.println(day[j]);
				switch (day[j]) {
				case "월":
					drawLecture(color, 0, name, g);
					break;
				case "화":
					drawLecture(color, 1, name, g);
					break;
				case "수":
					drawLecture(color, 2, name, g);
					break;
				case "목":
					drawLecture(color, 3, name, g);
					break;
				case "금":
					drawLecture(color, 4, name, g);
					break;
				}
			}
			color++;
		}
		for (int i = 0; i < Freeclasslist.size(); i++) {
			String name = setRectFreeclass(Freeclasslist.get(i));
			for (int j = 0; j < day.length; j++) {
				Random random = new Random();
				color = random.nextInt(5);
				switch (day[j]) {
				case "월":
					drawLecture(color, 0, name, g);
					break;
				case "화":
					drawLecture(color, 1, name, g);
					break;
				case "수":
					drawLecture(color, 2, name, g);
					break;
				case "목":
					drawLecture(color, 3, name, g);
					break;
				case "금":
					drawLecture(color, 4, name, g);
					break;
				}
			}
		}
	}

}
