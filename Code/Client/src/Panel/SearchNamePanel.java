package Panel;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.Stub;

public class SearchNamePanel extends JPanel {

	ActionListener listener;
	MouseListener mouseHandler;
	String userID;
	Stub stub;

	GridBagConstraints gbc;
	GridBagLayout gbl;

	boolean dayEnable = false, monChecked = false, tueChecked = false, wedChecked = false, thuChecked = false,
			friChecked = false;

	JComboBox<String> searchType;
	private LBS_Panel table;
	JTextField TSearch = new JTextField("");
	JButton BSearch = new JButton("검색");
	Checkbox checkDay = new Checkbox("날짜 포함 검색");
	Checkbox mon = new Checkbox("월");
	Checkbox tue = new Checkbox("화");
	Checkbox wed = new Checkbox("수");
	Checkbox thu = new Checkbox("목");
	Checkbox fri = new Checkbox("금");

	public SearchNamePanel(ActionListener listener, MouseListener mouseHandler, String UserID, Stub stub) {
		this.listener = listener;
		this.mouseHandler = mouseHandler;
		this.userID = UserID;
		this.stub = stub;
		
		String typeArray[] = {"강의명", "교수명"};
		searchType = new JComboBox<String>(typeArray);
		
		table = new LBS_Panel(listener, mouseHandler, UserID, stub);
		getTable().setPanelName("searchByName");
		getTable().SetFileName("alllecture");
		
		gbc = new GridBagConstraints();
		gbl = new GridBagLayout();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(20, 0, 10, 20);
		
		this.BSearch.setBackground(new Color(075, 101, 128));
		this.BSearch.setForeground(Color.WHITE);
		
		this.gbc = layout(gbc, 0.1, 0, 0, 0, 1, 1);
		this.gbl.setConstraints(searchType, gbc);
		this.gbc = layout(gbc, 0.1, 0, 1, 0, 5, 1);
		this.gbl.setConstraints(TSearch, gbc);
		this.gbc = layout(gbc, 0.1, 0, 0, 1, 1, 1);
		this.gbl.setConstraints(checkDay, gbc);
		this.gbc = layout(gbc, 0.1, 0, 1, 1, 1, 1);
		this.gbl.setConstraints(mon, gbc);
		this.gbc = layout(gbc, 0.1, 0, 2, 1, 1, 1);
		this.gbl.setConstraints(tue, gbc);
		this.gbc = layout(gbc, 0.1, 0, 3, 1, 1, 1);
		this.gbl.setConstraints(wed, gbc);
		this.gbc = layout(gbc, 0.1, 0, 4, 1, 1, 1);
		this.gbl.setConstraints(thu, gbc);
		this.gbc = layout(gbc, 0.1, 0, 5, 1, 1, 1);
		this.gbl.setConstraints(fri, gbc);
		this.gbc = layout(gbc, 0.1, 0, 6, 0, 2, 2);
		this.gbl.setConstraints(BSearch, gbc);
		this.gbc = layout(gbc, 0.1, 0.1, 0, 3, 8, 1);
		this.gbl.setConstraints(getTable(), gbc);
		this.setLayout(gbl);
		
		Checked checkListener = new Checked();
		mon.addItemListener(checkListener);
		tue.addItemListener(checkListener);
		wed.addItemListener(checkListener);
		thu.addItemListener(checkListener);
		fri.addItemListener(checkListener);
		checkDay.addItemListener(checkListener);
		mon.setEnabled(false);
		tue.setEnabled(false);
		wed.setEnabled(false);
		thu.setEnabled(false);
		fri.setEnabled(false);
		
		this.add(searchType);
		this.add(TSearch);
		this.add(checkDay);
		this.add(mon);
		this.add(tue);
		this.add(wed);
		this.add(thu);
		this.add(fri);
		this.add(BSearch);
		this.add(getTable());
		
		BSearch.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(TSearch.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "검색어를 입력하세요.", "검색어 미입력", JOptionPane.WARNING_MESSAGE);
				} else if (dayEnable&&!(monChecked||tueChecked||wedChecked||thuChecked||friChecked)){
					JOptionPane.showMessageDialog(null, "요일이 선택되지 않았습니다.", "요일 미선택", JOptionPane.WARNING_MESSAGE);
				}else {
					getTable().setSearchType(searchType.getSelectedItem().toString());
					getTable().setKeyword(TSearch.getText());
					if (dayEnable) {
						getTable().setDay(dayText());
					} else {
						getTable().setDay("notSelected");
					}
					getTable().showTable();
				}
			}
		});
	}
	
	public String dayText() {
		String result = "";
		if (monChecked) {
			result += "월";
		}
		if (tueChecked) {
			result += "화";
		}
		if (wedChecked) {
			result += "수";
		}
		if (thuChecked) {
			result += "목";
		}
		if (friChecked) {
			result += "금";
		}
		return result;
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
	
	public LBS_Panel getTable() {
		return table;
	}

	class Checked implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getSource()==checkDay) {
				dayEnable = (e.getStateChange() == 1 ? true:false);
				mon.setEnabled(dayEnable);
				tue.setEnabled(dayEnable);
				wed.setEnabled(dayEnable);
				thu.setEnabled(dayEnable);
				fri.setEnabled(dayEnable);
			} else if (e.getSource()==mon) {
				monChecked = (e.getStateChange() == 1 ? true:false);
			} else if (e.getSource()==tue) {
				tueChecked = (e.getStateChange() == 1 ? true:false);
			} else if (e.getSource()==wed) {
				wedChecked = (e.getStateChange() == 1 ? true:false);
			} else if (e.getSource()==thu) {
				thuChecked = (e.getStateChange() == 1 ? true:false);
			} else if (e.getSource()==fri) {
				friChecked = (e.getStateChange() == 1 ? true:false);
			}
		}
	}

}
