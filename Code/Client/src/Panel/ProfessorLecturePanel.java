package Panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import main.Stub;

public class ProfessorLecturePanel extends JPanel {

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

	// ��û�� ���� Ȯ��
	public ProfessorLecturePanel(ActionListener listener, MouseListener mouseHandler, String UserID, Stub stub) {
		this.userID = UserID;
		this.stub = stub;

		this.handler = listener;
		this.setLayout(new GridLayout());
		header.add("���¹�ȣ");
		header.add("���¸�");
		header.add("����");
		header.add("���� ����");
		header.add("���� �ð�");
		header.add("���� �ο�");
		model = new DefaultTableModel(header, 0) {
			public boolean isCellEditable(int i, int c) {
				return false;
			}
		};
		Table = new JTable(model);
		Table.getColumn("���¹�ȣ").setPreferredWidth(150);
		Table.getColumn("���¸�").setPreferredWidth(650);
		Table.getColumn("����").setPreferredWidth(200);
		Table.getColumn("���� ����").setPreferredWidth(50);
		Table.getColumn("���� �ð�").setPreferredWidth(200);
		Table.getColumn("���� �ο�").setPreferredWidth(50);
		TableScroll = new JScrollPane(Table);
		Table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.add(TableScroll);
		Table.addMouseListener(mouseHandler);
	}

	public void showTable() {
		model.setRowCount(0);
		Vector<Object> datas = new Vector<Object>();
		datas.add(userID);
		Vector<Object> result = stub.runSession("LectureManager", "getProfessorLecture", datas);
		for (int i = 0; i < result.size() / 6; i++) {
			addData = new Vector<String>();
			for (int j = i * 6; j < i * 6 + 6; j++) {
				addData.add((String) result.get(j));
			}
			model.addRow(addData);
		}
	}

	public void setPanelName(String setting) {
		PanelName = setting;
	}

	public void SetFileName(String FileName) {
		fileName = FileName;
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
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public void setDay(String day) {
		this.day = day;
	}

}
