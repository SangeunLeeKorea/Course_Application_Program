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
	JButton delete = new JButton("삭제");
	String fileName;

	Stub stub;

	public DeleteFreeclassFrame(String userID, ActionListener fCButtonPushed, Stub stub) {
		super("사용자 지정 강좌 삭제");
		this.stub = stub;
		this.setLayout(new BorderLayout());
		this.setSize(500, 800);

		fileName = "Freeclass\\" + userID + "Freeclass.txt";

		header.add("이름");
		header.add("요일");
		header.add("시간");
		model = new DefaultTableModel(header, 0) {
			public boolean isCellEditable(int i, int c) {
				return false;
			}
		};
		table = new JTable(model);
		table.getColumn("이름").setPreferredWidth(200);
		table.getColumn("요일").setPreferredWidth(100);
		table.getColumn("시간").setPreferredWidth(200);
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
			JOptionPane.showMessageDialog(null, "사용자 지정 강좌를 삭제했습니다.", "삭제 완료", JOptionPane.PLAIN_MESSAGE);
			return true;
		}
		return false;
	}

	public JButton getDelete() {
		return delete;
	}
}
