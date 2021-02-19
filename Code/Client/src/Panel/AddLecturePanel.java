package Panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.Stub;

public class AddLecturePanel extends JPanel {
	
	JPanel gettingData = new JPanel();

	ItemSelectionPanel campus;
	ItemSelectionPanel department;
	ItemSelectionPanel major;
	ListSelectionHandler listSelected;
	ActionListener sincheongAdd;
	ActionListener basketAdd;
	GridBagLayout gblForThis, gblForData;
	GridBagConstraints gbcForThis, gbcForData;

	String userID;
	Stub stub;
	
	JTextField TNumber = new JTextField();
	JTextField TName = new JTextField();
	JTextField TCredit = new JTextField();
	JTextField TDay = new JTextField();
	JTextField TTime1 = new JTextField();
	JTextField TTime2 = new JTextField();
	JLabel dash = new JLabel("-");
	JLabel LNumber = new JLabel("강좌번호");
	JLabel LName = new JLabel("강좌명");
	JLabel LCredit = new JLabel("학점");
	JLabel LDay = new JLabel("강의 요일");
	JLabel LTime = new JLabel("강의 시간");
	
	boolean majorSelected = false;
	
	String majorFilename;
	

	public AddLecturePanel(ActionListener handler, MouseListener mouseHandler, String userID, Stub stub) {
		this.userID = userID;
		this.stub = stub;

		// 강의 찾기+강의 미리담기+강의신청
		gblForThis = new GridBagLayout();
		gbcForThis = new GridBagConstraints();
		gbcForThis.fill = GridBagConstraints.BOTH;
		gbcForThis.insets = new Insets(10, 0, 0, 10);
		this.setLayout(gblForThis);

		// 리스트 선택 선언
		listSelected = new ListSelectionHandler();

		// campus
		campus = new ItemSelectionPanel(listSelected, stub);
		gbcForThis = layout(gbcForThis, 0.1, 0.1, 0, 0, 1, 1);
		gblForThis.setConstraints(campus, gbcForThis);
		campus.setFile("root");
		campus.setLabel("캠퍼스");

		// department
		department = new ItemSelectionPanel(listSelected, stub);
		gbcForThis = layout(gbcForThis, 0.1, 0.1, 1, 0, 1, 1);
		gblForThis.setConstraints(department, gbcForThis);
		department.setLabel("단과대학");

		// major
		major = new ItemSelectionPanel(listSelected, stub);
		gbcForThis = layout(gbcForThis, 0.1, 0.1, 2, 0, 1, 1);
		gblForThis.setConstraints(major, gbcForThis);
		major.setLabel("학과(학부)");

		// lecture
		gblForData = new GridBagLayout();
		gbcForData = new GridBagConstraints();
		gbcForData.fill = GridBagConstraints.BOTH;
		gbcForData.insets = new Insets(10, 0, 0, 10);
		gettingData.setLayout(gblForData);
		gbcForData = layout(gbcForThis, 0, 0, 0, 0, 1, 1);
		gblForData.setConstraints(LNumber, gbcForThis);
		gbcForData = layout(gbcForThis, 0, 0, 0, 1, 1, 1);
		gblForData.setConstraints(LName, gbcForThis);
		gbcForData = layout(gbcForThis, 0, 0, 0, 2, 1, 1);
		gblForData.setConstraints(LCredit, gbcForThis);
		gbcForData = layout(gbcForThis, 0, 0, 0, 3, 1, 1);
		gblForData.setConstraints(LDay, gbcForThis);
		gbcForData = layout(gbcForThis, 0, 0, 0, 4, 1, 1);
		gblForData.setConstraints(LTime, gbcForThis);
		gbcForData = layout(gbcForThis, 0.1, 0, 1, 0, 3, 1);
		gblForData.setConstraints(TNumber, gbcForThis);
		gbcForData = layout(gbcForThis, 0.1, 0, 1, 1, 3, 1);
		gblForData.setConstraints(TName, gbcForThis);
		gbcForData = layout(gbcForThis, 0.1, 0, 1, 2, 3, 1);
		gblForData.setConstraints(TCredit, gbcForThis);
		gbcForData = layout(gbcForThis, 0.1, 0, 1, 3, 3, 1);
		gblForData.setConstraints(TDay, gbcForThis);
		gbcForData = layout(gbcForThis, 0.1, 0, 1, 4, 1, 1);
		gblForData.setConstraints(TTime1, gbcForThis);
		gbcForData = layout(gbcForThis, 0, 0, 2, 4, 1, 1);
		gblForData.setConstraints(dash, gbcForThis);
		gbcForData = layout(gbcForThis, 0.1, 0, 3, 4, 1, 1);
		gblForData.setConstraints(TTime2, gbcForThis);
		gettingData.add(LNumber);
		gettingData.add(LName);
		gettingData.add(LCredit);
		gettingData.add(LDay);
		gettingData.add(LTime);
		gettingData.add(TNumber);
		gettingData.add(TName);
		gettingData.add(TCredit);
		gettingData.add(TDay);
		gettingData.add(TTime1);
		gettingData.add(dash);
		gettingData.add(TTime2);
		
		gbcForThis = layout(gbcForThis, 0.1, 0.1, 0, 1, 3, 1);
		gblForThis.setConstraints(gettingData, gbcForThis);

		// add
		this.add(campus);
		this.add(department);
		this.add(major);
		this.add(gettingData);
	}

	public void TableClicked(ListSelectionEvent e) throws IOException {
		if (e.getValueIsAdjusting()) {
			try {
				if (e.getSource() == campus.list) {
					String fileName = campus.getSelectedFileName();
					if (!fileName.equals("timeOut")) {
						department.setFile(fileName);
						major.listData.removeAllElements();
						major.list.setListData(major.listData);
						majorSelected = false;
					}
				} else if (e.getSource() == department.list) {
					String fileName = department.getSelectedFileName();
					if (!fileName.equals("timeOut")) {
						major.setFile(fileName);
						majorSelected = false;
					}
				} else if (e.getSource() == major.list) {
					majorFilename = major.getSelectedFileName();
					majorSelected = true;
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}

	private class ListSelectionHandler implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			try {
				TableClicked(e);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	};

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
	}

	public String getMajorFilename() {
		return majorFilename;
	}
	
	public String getNumber() {
		return TNumber.getText();
	}
	
	public String getName() {
		return TName.getText();
	}
	
	public String getCredit() {
		return TCredit.getText();
	}
	
	public String getDay() {
		return TDay.getText();
	}
	
	public String getTime() {
		String result = TTime1.getText()+"-"+TTime2.getText();
		return result;
	}
	
}