package Panel;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import main.Stub;

public class StudentTable extends JPanel {
	
	public JTable Table;
	JScrollPane TableScroll;
	Vector<String> header = new Vector<String>(), addData;
	DefaultTableModel model;
	ActionListener handler;
	String PanelName = "";
	String fileName;
	JLabel totalCredit;
	GridBagConstraints gbc;

	String userID;
	Stub stub;
	String searchType;
	String keyword;
	String day;

	public StudentTable(ActionListener listener, MouseListener mouseHandler, String UserID, Stub stub) {
		this.userID = UserID;
		this.stub = stub;

		this.handler = listener;
		this.setLayout(new GridLayout());
		header.add("아이디");
		header.add("이름");
		header.add("학번");
		header.add("전공");
		header.add("전화번호");
		model = new DefaultTableModel(header, 0) {
			public boolean isCellEditable(int i, int c) {
				return false;
			}
		};
		Table = new JTable(model);
		Table.getColumn("아이디").setPreferredWidth(150);
		Table.getColumn("이름").setPreferredWidth(650);
		Table.getColumn("학번").setPreferredWidth(200);
		Table.getColumn("전공").setPreferredWidth(50);
		Table.getColumn("전화번호").setPreferredWidth(200);
		TableScroll = new JScrollPane(Table);
		Table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.add(TableScroll);
		Table.addMouseListener(mouseHandler);
	}

	public void showTable(Vector<Object> result) {
		model.setRowCount(0);
		for (int i = 0; i < result.size() / 6; i++) {
			addData = new Vector<String>();
			for (int j = i * 6; j < i *6 + 6; j++) {
				addData.add((String) result.get(j));
			}
			model.addRow(addData);
		}
	}

	public DefaultTableModel getModel() {
		return model;
	}

	public int getRow() {
		return Table.getSelectedRow();
	}

	public JTable getTable() {
		return Table;
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
	}
}
