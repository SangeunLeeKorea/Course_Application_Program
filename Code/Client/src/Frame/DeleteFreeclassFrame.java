package Frame;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import main.Stub;

public class DeleteFreeclassFrame extends JFrame {

	JTable table;
	JScrollPane TableScroll;
	Vector<String> header = new Vector<String>();
	DefaultTableModel model;
	JButton delete = new JButton("����");
	String fileName;

	Stub stub;

	public DeleteFreeclassFrame(String userID, ActionListener fCButtonPushed, Stub stub) {
		super("����� ���� ���� ����");
		this.stub = stub;
		this.setLayout(new BorderLayout());
		this.setSize(500, 800);

		fileName = "Freeclass\\" + userID + "Freeclass.txt";

		header.add("�̸�");
		header.add("����");
		header.add("�ð�");
		model = new DefaultTableModel(header, 0) {
			public boolean isCellEditable(int i, int c) {
				return false;
			}
		};
		table = new JTable(model);
		table.getColumn("�̸�").setPreferredWidth(200);
		table.getColumn("����").setPreferredWidth(100);
		table.getColumn("�ð�").setPreferredWidth(200);
		TableScroll = new JScrollPane(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		delete.addActionListener(fCButtonPushed);
		this.add(TableScroll, BorderLayout.CENTER);
		this.add(delete, BorderLayout.SOUTH);

		showTable();

		this.setVisible(true);
	}

	public void showTable() {
		model.setRowCount(0);
		Vector<Object> datas = new Vector<Object>();
		datas.add(fileName);
		Vector<Object> result = stub.runSession("FreeclassManager", "showFreeclass", datas);
		if (!result.get(0).equals("timeOut")) {
			for (int i = 0; i < result.size() / 3; i++) {
				Vector<String> data = new Vector<String>();
				for (int j = i * 3; j < i * 3 + 3; j++) {
					data.add((String) result.get(j));
				}
				model.addRow(data);
			}
		}
	}

	public boolean doDELETE() {
		int row = table.getSelectedRow();
		Vector<Object> datas = new Vector<Object>();
		datas.add(fileName);
		datas.add((String) model.getValueAt(row, 0));
		datas.add((String) model.getValueAt(row, 1));
		datas.add((String) model.getValueAt(row, 2));
		Vector<Object> result = stub.runSession("FreeclassManager", "deleteFreeclass", datas);
		if (!result.get(0).equals("timeOut")) {
			showTable();
			JOptionPane.showMessageDialog(null, "����� ���� ���¸� �����߽��ϴ�.", "���� �Ϸ�", JOptionPane.PLAIN_MESSAGE);
			return true;
		}
		return false;
	}

	public JButton getDelete() {
		return delete;
	}
}
