package Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;

import Panel.LBS_Panel;
import Panel.LecturePanel;
import Panel.SearchNamePanel;
import main.Stub;

public class LoginedFrame extends JFrame {

	JPanel menuPanel = new JPanel();
	JPanel ToolPanel = new JPanel();
	JPanel ViewPanel = new JPanel();
	JPanel buttonPanel = new JPanel();
	JPanel gap = new JPanel();
	public LoginFrame loginFrame;
	LecturePanel lecturePanel;
	MypageFrame mypageFrame;
	public JButton logout = new JButton("�α׾ƿ�");
	JButton lecture = new JButton("���� ã��");
	JButton basket = new JButton("�̸���� ���");
	JButton sincheong = new JButton("��û ���");
	JButton timeTable = new JButton("�ð�ǥ");
	JButton memo = new JButton("�޸�");
	JButton mypage = new JButton("����������");
	JButton addSincheong = new JButton("��û�ϱ�");
	JButton deleteBasket = new JButton("�̸���⿡�� ����");
	JButton addBasket = new JButton("�̸����");
	JButton deleteSincheong = new JButton("���� öȸ");
	ActionHandler actionHandler;
	MouseHandler mouseHandler;
	JLabel hello;
	GridBagConstraints gbcMenu, gbcTool, gbcButton;
	GridBagLayout gblMenu, gblTool, gblButton;
	LBS_Panel basketPanel;
	LBS_Panel sincheongPanel;
	TimeTableFrame timeTablePanel;
	MemoFrame memoFrame;
	JTabbedPane tab = new JTabbedPane();
	SearchNamePanel searchName;

	String userID;
	Stub stub;

	public LoginedFrame(ActionListener loginHandler, Stub stub) {
		this.setLayout(new BorderLayout());
		this.setSize(1500, 1000);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.actionHandler = new ActionHandler();
		this.mouseHandler = new MouseHandler();
		this.hello = new JLabel();
		this.addWindowListener(new Close());

		this.stub = stub;

		// ���ο� ������ ����(�⺻ �޴�)
		this.gblMenu = new GridBagLayout();
		this.gbcMenu = new GridBagConstraints();
		this.gbcMenu.insets = new Insets(0, 20, 0, 20);
		this.gbcMenu.fill = GridBagConstraints.HORIZONTAL;
		this.menuPanel.setLayout(gblMenu);
		this.menuPanel.setBackground(Color.LIGHT_GRAY);
		this.gbcMenu = layout(gbcMenu, 0.1, 0, 0, 0, 1, 1);
		this.gblMenu.setConstraints(hello, gbcMenu);
		this.gbcMenu = layout(gbcMenu, 0, 0, 1, 0, 1, 1);
		this.gblMenu.setConstraints(mypage, gbcMenu);
		this.gbcMenu = layout(gbcMenu, 0, 0, 2, 0, 1, 1);
		this.gblMenu.setConstraints(logout, gbcMenu);
		this.menuPanel.add(hello);
		this.menuPanel.add(mypage);
		this.menuPanel.add(logout);
		this.mypage.setBackground(new Color(075, 101, 128));
		this.mypage.setForeground(Color.WHITE);
		this.logout.setBackground(new Color(075, 101, 128));
		this.logout.setForeground(Color.WHITE);
		this.menuPanel.setPreferredSize(new Dimension(1500, 50));
		this.add(menuPanel, BorderLayout.NORTH);

		// ���� �޴�
		this.gblTool = new GridBagLayout();
		this.gbcTool = new GridBagConstraints();
		this.gbcTool.insets = new Insets(18, 10, 0, 4);
		this.gbcTool.fill = GridBagConstraints.HORIZONTAL;
		this.ToolPanel.setLayout(gblTool);
		this.gbcTool = layout(gbcTool, 0, 0, 0, 0, 1, 1);
		this.gblTool.setConstraints(lecture, gbcTool);
		this.gbcTool = layout(gbcTool, 0, 0, 0, 1, 1, 1);
		this.gblTool.setConstraints(basket, gbcTool);
		this.gbcTool = layout(gbcTool, 0, 0, 0, 2, 1, 1);
		this.gblTool.setConstraints(sincheong, gbcTool);
		this.gbcTool = layout(gbcTool, 0, 0, 0, 3, 1, 1);
		this.gblTool.setConstraints(timeTable, gbcTool);
		this.gbcTool = layout(gbcTool, 0, 0, 0, 4, 1, 1);
		this.gblTool.setConstraints(memo, gbcTool);
		this.gbcTool = layout(gbcTool, 0, 0.1, 0, 5, 1, 1);
		this.gblTool.setConstraints(gap, gbcTool);
		this.ToolPanel.add(lecture);
		this.ToolPanel.add(basket);
		this.ToolPanel.add(sincheong);
		this.ToolPanel.add(timeTable);
		this.ToolPanel.add(memo);
		this.ToolPanel.add(gap);
		this.lecture.setBackground(new Color(075, 101, 128));
		this.lecture.setForeground(Color.WHITE);
		this.basket.setBackground(new Color(075, 101, 128));
		this.basket.setForeground(Color.WHITE);
		this.sincheong.setBackground(new Color(075, 101, 128));
		this.sincheong.setForeground(Color.WHITE);
		this.timeTable.setBackground(new Color(075, 101, 128));
		this.timeTable.setForeground(Color.WHITE);
		this.memo.setBackground(new Color(075, 101, 128));
		this.memo.setForeground(Color.WHITE);
		this.ToolPanel.setPreferredSize(new Dimension(140, 950));
		this.add(ToolPanel, BorderLayout.WEST);

		// �ϴ� ��ư
		this.buttonPanel.setLayout(new FlowLayout(2, 30, 20));
		this.addSincheong.addActionListener(actionHandler);
		this.deleteBasket.addActionListener(actionHandler);
		this.addBasket.addActionListener(actionHandler);
		this.deleteSincheong.addActionListener(actionHandler);
		this.addSincheong.setBackground(new Color(075, 101, 128));
		this.addSincheong.setForeground(Color.WHITE);
		this.addBasket.setBackground(new Color(075, 101, 128));
		this.addBasket.setForeground(Color.WHITE);
		this.deleteBasket.setBackground(new Color(075, 101, 128));
		this.deleteBasket.setForeground(Color.WHITE);
		this.deleteSincheong.setBackground(new Color(075, 101, 128));
		this.deleteSincheong.setForeground(Color.WHITE);
		addButton();
		buttonPanel.remove(deleteBasket);
		buttonPanel.remove(deleteSincheong);
		this.add(buttonPanel, BorderLayout.SOUTH);

		// ���� �ǳ� ����
		this.basketPanel = new LBS_Panel(actionHandler, mouseHandler, userID, this.stub);
		this.lecturePanel = new LecturePanel(actionHandler, mouseHandler, userID, this.stub);
		this.sincheongPanel = new LBS_Panel(actionHandler, mouseHandler, userID, this.stub);
		this.searchName = new SearchNamePanel(actionHandler, mouseHandler, userID, this.stub);
		this.tab.add(lecturePanel, "�������� �˻�");
		this.tab.add(searchName, "�̸����� �˻�");
		this.add(tab, BorderLayout.CENTER);
		this.lecturePanel.setPreferredSize(new Dimension(1360, 950));

		// ��ư ����
		this.lecture.addActionListener(actionHandler);
		this.basket.addActionListener(actionHandler);
		this.sincheong.addActionListener(actionHandler);
		this.mypage.addActionListener(actionHandler);
		this.memo.addActionListener(actionHandler);
		this.logout.addActionListener(loginHandler);
		this.timeTable.addActionListener(actionHandler);
	}

	private void addButton() {
		buttonPanel.add(addBasket);
		buttonPanel.add(addSincheong);
		buttonPanel.add(deleteBasket);
		buttonPanel.add(deleteSincheong);
	}

	public GridBagConstraints layout(GridBagConstraints gbc, double xleft, double yleft, int x, int y, int width,
			int height) {
		gbc.weightx = xleft; // x����
		gbc.weighty = yleft; // y����
		gbc.gridx = x; // ������ġ x
		gbc.gridy = y; // ������ġ y
		gbc.gridwidth = width; // �����̳� �ʺ�
		gbc.gridheight = height; // �����̳� ����
		return gbc;
	}

	public void setUserID(String userID) {
		this.userID = userID;
		this.lecturePanel.setUserID(this.userID);
		this.basketPanel.setUserID(this.userID);
		this.sincheongPanel.setUserID(this.userID);
	}

	public void setHello(String UserID) {
		this.userID = UserID;
		lecturePanel.setUserID(userID);
		basketPanel.setUserID(userID);
		sincheongPanel.setUserID(userID);
		hello.setText("ȯ���մϴ�, " + userID + "��");
	}

	public void showMypage() {
		try {
			mypageFrame = new MypageFrame(userID, stub);
			mypageFrame.getProfile();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	public void AddSincheong(int row, DefaultTableModel model) {
		int result = JOptionPane.showConfirmDialog(null, "������ ���¸� ������û �Ͻðڽ��ϱ�?", "������û", JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			Vector<Object> datas = new Vector<Object>();
			datas.add(userID);
			for (int column = 0; column < 6; column++) {
				datas.add((String) model.getValueAt(row, column));
			}
			Object check = stub.runSession("SincheongManager", "checkSame", datas).get(0);
			if (!check.equals("timeOut")) {
				int checkSame = (int) check;
				datas = new Vector<Object>();
				datas.add(userID);
				datas.add((String) model.getValueAt(row, 3));
				check = stub.runSession("SincheongManager", "checkCredit", datas).get(0);
				int checkCredit = -1;
				if (!check.equals("timeOut")) {
					checkCredit = (int) check;
				}

				datas = new Vector<Object>();
				datas.add(userID);
				for (int column = 0; column < 6; column++) {
					datas.add((String) model.getValueAt(row, column));
				}
				check = stub.runSession("SincheongManager", "checkTime", datas).get(0);
				int checkTime = -1;
				if (!check.equals("timeOut")) {
					checkTime = (int) check;
				}
				if (checkSame == 1 && checkCredit == 1 && checkTime == 1) {
					datas = new Vector<Object>();
					datas.add(userID);
					for (int column = 0; column < 6; column++) {
						datas.add((String) model.getValueAt(row, column));
					}
					stub.runSession("SincheongManager", "addSincheong", datas);
					JOptionPane.showMessageDialog(null, "������ ���¸� ��û�Ͽ����ϴ�.", "������û ����", JOptionPane.PLAIN_MESSAGE);
				} else if (checkSame == 0) {
					JOptionPane.showMessageDialog(null, "������ ���°� �̹� ��û�Ǿ� �ֽ��ϴ�.", "������û ����",
							JOptionPane.WARNING_MESSAGE);
				} else if (checkCredit == 0) {
					JOptionPane.showMessageDialog(null, "��û�� �� �ִ� ������ �Ѿ����ϴ�.(�ִ� 18����)", "������û ����",
							JOptionPane.WARNING_MESSAGE);
				} else if (checkTime == 0) {
					JOptionPane.showMessageDialog(null, "��û�Ϸ��� ���¿� ���� �ð��� ���°� �̹� ��û �Ǿ��ֽ��ϴ�.", "������û ����",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}

	public void AddBasket(DefaultTableModel model, int row) {
		int result = JOptionPane.showConfirmDialog(null, "������ ���¸� �̸���� �Ͻðڽ��ϱ�?", "�̸����", JOptionPane.YES_NO_OPTION);
		Vector<String> Selected = new Vector<String>();
		if (result == JOptionPane.YES_OPTION) {
			Vector<Object> datas = new Vector<Object>();
			for (int column = 0; column < 6; column++) {
				datas.add((String) model.getValueAt(row, column));
			}
			datas.add(userID);
			Object check = stub.runSession("BasketManager", "checkExcist", datas).get(0);
			if (!check.equals("timeOut")) {
				int checkExcist = Integer.parseInt((String) check);
				if (checkExcist == 1) {
					datas = new Vector<Object>();
					datas.add(userID);
					for (int column = 0; column < 6; column++) {
						datas.add((String) model.getValueAt(row, column));
					}
					stub.runSession("BasketManager", "addBasket", datas);
					JOptionPane.showMessageDialog(null, "������ ���¸� ��ҽ��ϴ�.", "�̸���� ����", JOptionPane.PLAIN_MESSAGE);
				} else if (checkExcist == 0) {
					JOptionPane.showMessageDialog(null, "������ ���°� �̹� ����� �ֽ��ϴ�.", "�̸���� ����", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}

	public void DeleteBasket(int row, DefaultTableModel model) {
		int result = JOptionPane.showConfirmDialog(null, "������ ���¸� �̸���⿡�� �����Ͻðڽ��ϱ�?", "�̸���� ����",
				JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			Vector<Object> datas = new Vector<Object>();
			datas.add(userID);
			for (int i = 0; i < 6; i++) {
				datas.add((String) model.getValueAt(row, i));
			}
			Vector<Object> check = stub.runSession("BasketManager", "deleteBasket", datas);
			if (!check.get(0).equals("timeOut")) {
				basketPanel.showTable();
				JOptionPane.showMessageDialog(null, "������ ���¸� �̸���⿡�� �����Ͽ����ϴ�.", "�̸���� ���� ����", JOptionPane.PLAIN_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "�̸���� ���Ÿ� ����Ͽ����ϴ�.", "�̸���� ���� ���", JOptionPane.PLAIN_MESSAGE);
		}
	}

	public void DeleteSincheong(int row, DefaultTableModel model) {
		int result = JOptionPane.showConfirmDialog(null, "������ ���¸� ����öȸ �Ͻðڽ��ϱ�?", "����öȸ", JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			Vector<Object> datas = new Vector<Object>();
			datas.add(userID);
			for (int i = 0; i < 6; i++) {
				datas.add(model.getValueAt(row, i));
			}
			Vector<Object> check = stub.runSession("SincheongManager", "deleteSincheong", datas);
			if (!check.get(0).equals("timeOut")) {
				sincheongPanel.showTable();
				JOptionPane.showMessageDialog(null, "������ ���¸� ����öȸ �߽��ϴ�.", "����öȸ ����", JOptionPane.PLAIN_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "����öȸ���� ����Ͽ����ϴ�.", "����öȸ ���", JOptionPane.PLAIN_MESSAGE);
		}
	}

	// ��ư �׼��ڵ鷯
	class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == addSincheong) {
				if (tab.isVisible()) {
					if (lecturePanel.lecture.Table.getSelectedRow() != -1) {
						AddSincheong(lecturePanel.lecture.getRow(), lecturePanel.lecture.getModel());
					} else if (searchName.getTable().Table.getSelectedRow() != -1){
						AddSincheong(searchName.getTable().getRow(), searchName.getTable().getModel());
					} else {
						JOptionPane.showMessageDialog(null, "���°� ���õ��� �ʾҽ��ϴ�.", "", JOptionPane.ERROR_MESSAGE);
					}
				} else if (basketPanel.isVisible()) {
					if (basketPanel.Table.getSelectedRow() == -1) {
						JOptionPane.showMessageDialog(null, "���°� ���õ��� �ʾҽ��ϴ�.", "", JOptionPane.ERROR_MESSAGE);
					} else {
						AddSincheong(basketPanel.getRow(), basketPanel.getModel());
					}
				}
			} else if (e.getSource() == deleteSincheong) {
				if (sincheongPanel.Table.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "���°� ���õ��� �ʾҽ��ϴ�.", "", JOptionPane.ERROR_MESSAGE);
				} else {
					DeleteSincheong(sincheongPanel.getRow(), sincheongPanel.getModel());
				}
			} else if (e.getSource() == addBasket) {
				if (lecturePanel.lecture.Table.getSelectedRow() != -1) {
					AddBasket(lecturePanel.lecture.getModel(), lecturePanel.lecture.getRow());
				} else if (searchName.getTable().Table.getSelectedRow() != -1){
					AddBasket(searchName.getTable().getModel(), searchName.getTable().getRow());
				} else {
					JOptionPane.showMessageDialog(null, "���°� ���õ��� �ʾҽ��ϴ�.", "", JOptionPane.ERROR_MESSAGE);
				}
			} else if (e.getSource() == deleteBasket) {
				if (basketPanel.Table.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "���°� ���õ��� �ʾҽ��ϴ�.", "", JOptionPane.ERROR_MESSAGE);
				} else {
					DeleteBasket(basketPanel.getRow(), basketPanel.getModel());
				}
			} else if (e.getSource() == mypage) {
				try {
					mypageFrame = new MypageFrame(userID, stub);
					mypageFrame.getProfile();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			} else if (e.getSource() == sincheong) {
				sincheongPanel.setPanelName("Sincheong");
				sincheongPanel.setPreferredSize(new Dimension(1360, 800));
				sincheongPanel.setVisible(true);
				basketPanel.setVisible(false);
				tab.setVisible(false);
				addButton();
				buttonPanel.remove(addSincheong);
				buttonPanel.remove(addBasket);
				buttonPanel.remove(deleteBasket);
				sincheongPanel.showTable();
				add(sincheongPanel, "Center");
				sincheongPanel.updateUI();
				buttonPanel.updateUI();
			} else if (e.getSource() == basket) {
				basketPanel.setPanelName("Basket");
				basketPanel.setPreferredSize(new Dimension(1360, 800));
				basketPanel.setVisible(true);
				sincheongPanel.setVisible(false);
				tab.setVisible(false);
				addButton();
				buttonPanel.remove(deleteSincheong);
				buttonPanel.remove(addBasket);
				basketPanel.showTable();
				add(basketPanel, BorderLayout.CENTER);
				basketPanel.updateUI();
				buttonPanel.updateUI();
			} else if (e.getSource() == lecture) {
				tab.setPreferredSize(new Dimension(1360, 800));
				tab.setVisible(true);
				sincheongPanel.setVisible(false);
				basketPanel.setVisible(false);
				addButton();
				buttonPanel.remove(deleteSincheong);
				buttonPanel.remove(deleteBasket);
				add(tab, BorderLayout.CENTER);
				buttonPanel.updateUI();
			} else if (e.getSource() == timeTable) {
				timeTablePanel = new TimeTableFrame(userID, stub);
			} else if (e.getSource() == memo) {
				memoFrame = new MemoFrame(userID, stub);
			}
		}
	}

	class MouseHandler implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				if (e.getSource() == basketPanel.Table) {
					AddSincheong(basketPanel.getRow(), basketPanel.getModel());
				} else if (e.getSource() == lecturePanel.lecture.Table) {
					AddSincheong(lecturePanel.lecture.getRow(), lecturePanel.lecture.getModel());
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}

	class Close implements WindowListener {
		@Override
		public void windowClosing(WindowEvent e) {
			stub.quit();
			System.exit(0);
		}

		@Override
		public void windowOpened(WindowEvent e) {
		}

		@Override
		public void windowClosed(WindowEvent e) {
		}

		@Override
		public void windowIconified(WindowEvent e) {
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
		}

		@Override
		public void windowActivated(WindowEvent e) {
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
		};
	}
}