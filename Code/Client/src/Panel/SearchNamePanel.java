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
	JButton BSearch = new JButton("�˻�");
	Checkbox checkDay = new Checkbox("��¥ ���� �˻�");
	Checkbox mon = new Checkbox("��");
	Checkbox tue = new Checkbox("ȭ");
	Checkbox wed = new Checkbox("��");
	Checkbox thu = new Checkbox("��");
	Checkbox fri = new Checkbox("��");

	public SearchNamePanel(ActionListener listener, MouseListener mouseHandler, String UserID, Stub stub) {
		this.listener = listener;
		this.mouseHandler = mouseHandler;
		this.userID = UserID;
		this.stub = stub;
		
		String typeArray[] = {"���Ǹ�", "������"};
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
					JOptionPane.showMessageDialog(null, "�˻�� �Է��ϼ���.", "�˻��� ���Է�", JOptionPane.WARNING_MESSAGE);
				} else if (dayEnable&&!(monChecked||tueChecked||wedChecked||thuChecked||friChecked)){
					JOptionPane.showMessageDialog(null, "������ ���õ��� �ʾҽ��ϴ�.", "���� �̼���", JOptionPane.WARNING_MESSAGE);
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
			result += "��";
		}
		if (tueChecked) {
			result += "ȭ";
		}
		if (wedChecked) {
			result += "��";
		}
		if (thuChecked) {
			result += "��";
		}
		if (friChecked) {
			result += "��";
		}
		return result;
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
