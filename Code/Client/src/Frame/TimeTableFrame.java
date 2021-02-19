package Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import Panel.TimeTablePanel;
import main.Stub;

public class TimeTableFrame extends JFrame {

	TimeTablePanel timeTablePanel;
	AddFreeclassFrame add;
	DeleteFreeclassFrame delete;
	JMenu file;
	JMenuBar menuBar;
	JMenuItem save, addFreeclass, deleteFreeclass;

	MenuListener menuClicked;

	String userID;
	Stub stub;

	public TimeTableFrame(String UserID, Stub stub) {
		super("�ð�ǥ");

		this.userID = UserID;
		this.stub = stub;

		timeTablePanel = new TimeTablePanel(userID, stub);
		CreateMenu();
		this.setVisible(true);
		this.setSize(520, 1200);
		this.setLocationRelativeTo(null);
		this.add(timeTablePanel);
		this.setResizable(false);
	}

	public void CreateMenu() {
		menuBar = new JMenuBar();
		file = new JMenu("����");
		menuBar.add(file);
		save = new JMenuItem("�������� ����");
		addFreeclass = new JMenuItem("���� ���� �߰�");
		deleteFreeclass = new JMenuItem("����� ���� ���� ����");
		file.add(save);
		file.add(addFreeclass);
		file.add(deleteFreeclass);

		menuClicked = new MenuListener();
		save.addActionListener(menuClicked);
		addFreeclass.addActionListener(menuClicked);
		deleteFreeclass.addActionListener(menuClicked);

		setJMenuBar(menuBar);
	}

	class MenuListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == save) {
				timeTablePanel.saveImage();
			} else if (e.getSource() == addFreeclass) {
				add = new AddFreeclassFrame(userID, FCButtonPushed, stub);
			} else if (e.getSource() == deleteFreeclass) {
				delete = new DeleteFreeclassFrame(userID, FCButtonPushed, stub);
			}
		}
	}
	
	public void disposeThis() {
		this.dispose();
	}

	ActionListener FCButtonPushed = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!(add == null)) {
				if (e.getSource() == add.getAdd()) {
					if (add.doADD()) {
						TimeTableFrame newttf = new TimeTableFrame(userID, stub);
					}
					add.dispose();
					disposeThis();
				} else if (e.getSource() == add.getCancel()) {
					add.doCANCEL();
				}
			}
			
			if (!(delete == null)) {
				if (e.getSource() == delete.getDelete()) {
					if(delete.doDELETE()) {
						TimeTableFrame newttf = new TimeTableFrame(userID, stub);
					}
					delete.dispose();
					disposeThis();
				}
			}
		}

	};
}
