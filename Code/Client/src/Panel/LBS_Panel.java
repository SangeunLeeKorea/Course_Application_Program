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

public class LBS_Panel extends JPanel {

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
	public LBS_Panel(ActionListener listener, MouseListener mouseHandler, String UserID, Stub stub) {
		this.userID = UserID;
		this.stub = stub;

		this.handler = listener;
		this.setLayout(new GridLayout());
		header.add("���¹�ȣ");
		header.add("���¸�");
		header.add("������");
		header.add("����");
		header.add("���� ����");
		header.add("���� �ð�");
		model = new DefaultTableModel(header, 0) {
			public boolean isCellEditable(int i, int c) {
				return false;
			}
		};
		Table = new JTable(model);
		Table.getColumn("���¹�ȣ").setPreferredWidth(150);
		Table.getColumn("���¸�").setPreferredWidth(650);
		Table.getColumn("������").setPreferredWidth(200);
		Table.getColumn("����").setPreferredWidth(50);
		Table.getColumn("���� ����").setPreferredWidth(50);
		Table.getColumn("���� �ð�").setPreferredWidth(200);
		TableScroll = new JScrollPane(Table);
		Table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.add(TableScroll);
		Table.addMouseListener(mouseHandler);
	}

	public void showTable() {
		model.setRowCount(0);
		if (PanelName.equals("Basket")) {
			Vector<Object> datas = new Vector<Object>();
			datas.add(userID);
			Vector<Object> result = stub.runSession("BasketManager", "showBasket", datas);
			for (int i = 0; i < result.size() / 6; i++) {
				addData = new Vector<String>();
				for (int j = i * 6; j < i *6 + 6; j++) {
					addData.add((String) result.get(j));
				}
				model.addRow(addData);
			}
		} else if (PanelName.equals("Lecture")) {
			Vector<Object> datas = new Vector<Object>();
			datas.add(userID);
			datas.add(fileName);
			Vector<Object> result = stub.runSession("LectureManager", "getItems", datas);
			for (int i = 0; i < result.size() / 6; i++) {
				addData = new Vector<String>();
				for (int j = i * 6; j < i * 6 + 6; j++) {
					addData.add((String) result.get(j));
				}
				model.addRow(addData);
			}
		} else if (PanelName.equals("Sincheong")) {
			Vector<Object> datas = new Vector<Object>();
			datas.add(userID);
			Vector<Object> result = stub.runSession("SincheongManager", "show", datas);
			int total = 0;
			for (int i = 0; i < result.size() / 6; i++) {
				addData = new Vector<String>();
				for (int j = i * 6; j < i * 6+ 6; j++) {
					addData.add((String) result.get(j));
					if (j%6==3) {
						total += Integer.parseInt((String)result.get(j));
					}
				}
				model.addRow(addData);
			}
			if (totalCredit == null) {
				totalCredit = new JLabel("��û�� ������ ������ �� " + total + "���� �Դϴ�.");
				GridBagLayout gbl = new GridBagLayout();
				gbc = new GridBagConstraints();
				gbc.fill = GridBagConstraints.BOTH;
				this.setLayout(gbl);
				gbc = layout(gbc, 0, 0, 0, 1, 1, 1);
				gbl.setConstraints(totalCredit, gbc);
				gbc = layout(gbc, 0.1, 0.1, 0, 0, 1, 1);
				gbl.setConstraints(TableScroll, gbc);
				this.add(totalCredit);
				this.repaint();
			} else {
				totalCredit.setText("��û�� ������ ������ �� " + total + "���� �Դϴ�.");
			}
		} else if (PanelName.equals("searchByName")) {
			Vector<Object> datas = new Vector<Object>();
			datas.add(fileName);
			datas.add(searchType);
			datas.add(keyword);
			datas.add(day);
			Vector<Object> result = stub.runSession("LectureManager", "search", datas);
			for (int i = 0; i < result.size() / 6; i++) {
				addData = new Vector<String>();
				for (int j = i * 6; j < i * 6 + 6; j++) {
					addData.add((String) result.get(j));
				}
				model.addRow(addData);
			}
			if (model.getRowCount()==0) {
				JOptionPane.showMessageDialog(null, "�˻� ����� �����ϴ�.", "��� ����", JOptionPane.WARNING_MESSAGE);
			}
		} else if (PanelName.equals("ProfessorLectures")) {
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
