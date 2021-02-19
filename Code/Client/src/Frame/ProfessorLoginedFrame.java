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
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Frame.LoginedFrame.ActionHandler;
import Panel.AddLecturePanel;
import Panel.ProfessorLecturePanel;
import Panel.StudentTable;
import main.Stub;

public class ProfessorLoginedFrame extends JFrame {

	JPanel menuPanel = new JPanel();
	JPanel ToolPanel = new JPanel();
	JPanel ViewPanel = new JPanel();
	JPanel buttonPanel = new JPanel();
	JPanel gap = new JPanel();
	public LoginFrame loginFrame;
	AddLecturePanel addLecturePanel;
	ProfessorLecturePanel showLectures;
	StudentTable studentTable;
	public JButton logout = new JButton("로그아웃");
	JButton addlecture = new JButton("새로운 강좌 개설");
	JButton showLecture = new JButton("개설 강좌 조회");
	JButton doAddLecture = new JButton("강좌 개설");
	ActionHandler actionHandler;
	JLabel hello;
	GridBagConstraints gbcMenu, gbcTool, gbcButton, gbcMain;
	GridBagLayout gblMenu, gblTool, gblButton, gblMain;
	TimeTableFrame timeTablePanel;
	MemoFrame memoFrame;

	JPanel showLectureAndStudent = new JPanel();

	String userID;
	Stub stub;

	public ProfessorLoginedFrame(ActionListener loginHandler, Stub stub) {
		this.setLayout(new BorderLayout());
		this.setSize(1500, 1000);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.actionHandler = new ActionHandler();
		this.hello = new JLabel();
		this.addWindowListener(new Close());

		this.stub = stub;

		// 새로운 프레임 생성(기본 메뉴)
		this.gblMenu = new GridBagLayout();
		this.gbcMenu = new GridBagConstraints();
		this.gbcMenu.insets = new Insets(0, 20, 0, 20);
		this.gbcMenu.fill = GridBagConstraints.HORIZONTAL;
		this.menuPanel.setLayout(gblMenu);
		this.menuPanel.setBackground(Color.LIGHT_GRAY);
		this.gbcMenu = layout(gbcMenu, 0.1, 0, 0, 0, 1, 1);
		this.gblMenu.setConstraints(hello, gbcMenu);
		this.gbcMenu = layout(gbcMenu, 0, 0, 1, 0, 1, 1);
		this.gblMenu.setConstraints(logout, gbcMenu);
		this.menuPanel.add(hello);
		this.menuPanel.add(logout);
		this.logout.setBackground(new Color(075, 101, 128));
		this.logout.setForeground(Color.WHITE);
		this.menuPanel.setPreferredSize(new Dimension(1500, 50));
		this.add(menuPanel, BorderLayout.NORTH);

		// 좌측 메뉴
		this.gblTool = new GridBagLayout();
		this.gbcTool = new GridBagConstraints();
		this.gbcTool.insets = new Insets(18, 10, 0, 4);
		this.gbcTool.fill = GridBagConstraints.HORIZONTAL;
		this.ToolPanel.setLayout(gblTool);
		this.gbcTool = layout(gbcTool, 0, 0, 0, 0, 1, 1);
		this.gblTool.setConstraints(addlecture, gbcTool);
		this.gbcTool = layout(gbcTool, 0, 0, 0, 1, 1, 1);
		this.gblTool.setConstraints(showLecture, gbcTool);
		this.gbcTool = layout(gbcTool, 0, 0.1, 0, 2, 1, 1);
		this.gblTool.setConstraints(gap, gbcTool);
		this.ToolPanel.add(addlecture);
		this.ToolPanel.add(showLecture);
		this.ToolPanel.add(gap);
		this.addlecture.setBackground(new Color(075, 101, 128));
		this.addlecture.setForeground(Color.WHITE);
		this.showLecture.setBackground(new Color(075, 101, 128));
		this.showLecture.setForeground(Color.WHITE);
		this.ToolPanel.setPreferredSize(new Dimension(140, 950));
		this.add(ToolPanel, BorderLayout.WEST);

		// 하단 버튼
		this.buttonPanel.setLayout(new FlowLayout(2, 30, 20));
		this.doAddLecture.addActionListener(actionHandler);
		this.doAddLecture.setBackground(new Color(075, 101, 128));
		this.doAddLecture.setForeground(Color.WHITE);
		addButton();
		this.add(buttonPanel, BorderLayout.SOUTH);

		// 선택 판넬 생성
		this.addLecturePanel = new AddLecturePanel(actionHandler, null, userID, this.stub);
		this.showLectures = new ProfessorLecturePanel(actionHandler, null, userID, stub);
		this.studentTable = new StudentTable(actionHandler, null, userID, stub);
		this.add(addLecturePanel, BorderLayout.CENTER);
		this.gblMain = new GridBagLayout();
		this.gbcMain = new GridBagConstraints();
		this.gbcMain.insets = new Insets(18, 10, 0, 4);
		this.gbcMain.fill = GridBagConstraints.BOTH;
		this.showLectureAndStudent.setLayout(gblMain);
		this.gbcMain = layout(gbcMain, 0.1, 0.1, 0, 0, 1, 1);
		this.gblMain.setConstraints(showLectures, gbcMain);
		this.gbcMain = layout(gbcMain, 0.1, 0.1, 0, 1, 1, 1);
		this.gblMain.setConstraints(studentTable, gbcMain);
		showLectureAndStudent.add(showLectures);
		showLectureAndStudent.add(studentTable);

		this.addLecturePanel.setPreferredSize(new Dimension(1360, 950));

		// 버튼 연결
		this.addlecture.addActionListener(actionHandler);
		this.showLecture.addActionListener(actionHandler);
		this.logout.addActionListener(loginHandler);
		
		ListSelectionModel selectionModel = showLectures.getTable().getSelectionModel();

		selectionModel.addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent e) {
//		    	DefaultTableModel model = showLectures.getModel();
//		    	JTable table = showLectures.getTable();
//		    	String fileName = model.getValueAt(table.getSelectedRow(), 1).toString();
//		    	Vector<Object> datas = new Vector<Object>();
//		    	datas.add(fileName);
//		    	Vector<Object> result = stub.runSession("SincheongManager", "getStudentList", datas);
//		    	studentTable.showTable(result);
		    }
		});
	}

	private void addButton() {
		buttonPanel.add(doAddLecture);
	}

	public GridBagConstraints layout(GridBagConstraints gbc, double xleft, double yleft, int x, int y, int width,
			int height) {
		gbc.weightx = xleft; // x여백
		gbc.weighty = yleft; // y여백
		gbc.gridx = x; // 시작위치 x
		gbc.gridy = y; // 시작위치 y
		gbc.gridwidth = width; // 컨테이너 너비
		gbc.gridheight = height; // 컨테이너 높이
		return gbc;
	}

	public void setUserID(String userID) {
		this.userID = userID;
		this.addLecturePanel.setUserID(this.userID);
		showLectures.setUserID(userID);
		studentTable.setUserID(userID);
	}

	public void setHello(String UserID) {
		this.userID = UserID;
		addLecturePanel.setUserID(userID);
		showLectures.setUserID(userID);
		studentTable.setUserID(userID);
		hello.setText("환영합니다, " + userID + " 교수님");
	}

	public void AddLecture() {
		Vector<Object> datas = new Vector<Object>();
		datas.add(addLecturePanel.getMajorFilename());
		datas.add(addLecturePanel.getNumber());
		datas.add(addLecturePanel.getName());
		datas.add(this.userID);
		datas.add(addLecturePanel.getCredit());
		datas.add(addLecturePanel.getDay());
		datas.add(addLecturePanel.getTime());
		stub.runSession("LectureManager", "addLecture", datas);
	}
	
	
	// 버튼 액션핸들러
	class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == doAddLecture) {
				String[] time = (addLecturePanel.getTime()).split("-");
				if (!isInt(addLecturePanel.getNumber())) {
					JOptionPane.showMessageDialog(null, "강좌번호는 숫자이어야 합니다.", "오류", JOptionPane.ERROR_MESSAGE);
				} else if (!isInt(addLecturePanel.getCredit())) {
					JOptionPane.showMessageDialog(null, "학점은 숫자이어야 합니다.", "오류", JOptionPane.ERROR_MESSAGE);
				} else if (!checkDay(addLecturePanel.getDay())) {
					JOptionPane.showMessageDialog(null, "요일이 월~금요일이어야 합니다.", "오류", JOptionPane.ERROR_MESSAGE);
				} else if (!isInt(time[0]) || !isInt(time[1])) {
					JOptionPane.showMessageDialog(null, "시간은 숫자로 입력되어야 합니다.", "오류", JOptionPane.ERROR_MESSAGE);
				} else if (Integer.parseInt(time[0]) > Integer.parseInt(time[1])) {
					JOptionPane.showMessageDialog(null, "시작 시간이 끝나는 시간보다 늦습니다.", "오류", JOptionPane.ERROR_MESSAGE);
				} else {
					AddLecture();
					JOptionPane.showMessageDialog(null, "입력한 정보의 새로운 강좌를 개설했습니다.", "강좌 개설 완료",
							JOptionPane.PLAIN_MESSAGE);
				}
			} else if (e.getSource() == showLecture) {
				showLectures.setPanelName("ProfessorLectures");
				showLectureAndStudent.setPreferredSize(new Dimension(1360, 800));
				showLectureAndStudent.setVisible(true);
				addLecturePanel.setVisible(false);
				buttonPanel.remove(doAddLecture);
				showLectures.showTable();
				add(showLectureAndStudent, BorderLayout.CENTER);
				showLectureAndStudent.updateUI();
				buttonPanel.updateUI();
			} else if (e.getSource() == addlecture) {
				addLecturePanel.setPreferredSize(new Dimension(1360, 800));
				addLecturePanel.setVisible(true);
				showLectureAndStudent.setVisible(false);
				addButton();
				add(addLecturePanel, BorderLayout.CENTER);
				buttonPanel.updateUI();
			}
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

	public boolean isInt(String data) {
		try {
			Integer.parseInt(data);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public boolean checkDay(String days) {
		String[] day = days.split("");
		for (String data : day) {
			if (!(data.equals("월") || data.equals("화") || data.equals("수") || data.equals("목") || data.equals("금"))) {
				return false;
			}
		}
		return true;
	}

	class clickTable implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		}
	};
}