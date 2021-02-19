package Panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.Stub;

public class LecturePanel extends JPanel {

	public LBS_Panel lecture;
	ItemSelectionPanel campus;
	ItemSelectionPanel department;
	ItemSelectionPanel major;
	ListSelectionHandler listSelected;
	ActionListener sincheongAdd;
	ActionListener basketAdd;
	GridBagConstraints gbc;

	String userID;
	Stub stub;

	public LecturePanel(ActionListener handler, MouseListener mouseHandler, String userID, Stub stub) {
		this.userID = userID;
		this.stub = stub;

		// ���� ã��+���� �̸����+���ǽ�û
		GridBagLayout gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(10, 0, 0, 10);
		this.setLayout(gbl);

		// ����Ʈ ���� ����
		listSelected = new ListSelectionHandler();

		// campus
		campus = new ItemSelectionPanel(listSelected, stub);
		gbc = layout(gbc, 0.1, 0.1, 0, 0, 1, 1);
		gbl.setConstraints(campus, gbc);
		campus.setFile("root");
		campus.setLabel("ķ�۽�");

		// department
		department = new ItemSelectionPanel(listSelected, stub);
		gbc = layout(gbc, 0.1, 0.1, 1, 0, 1, 1);
		gbl.setConstraints(department, gbc);
		department.setLabel("�ܰ�����");

		// major
		major = new ItemSelectionPanel(listSelected, stub);
		gbc = layout(gbc, 0.1, 0.1, 2, 0, 1, 1);
		gbl.setConstraints(major, gbc);
		major.setLabel("�а�(�к�)");

		// lecture
		lecture = new LBS_Panel(handler, mouseHandler, userID, stub);
		lecture.setPanelName("Lecture");
		gbc = layout(gbc, 0.1, 0.1, 0, 1, 3, 1);
		gbl.setConstraints(lecture, gbc);

		// add
		this.add(campus);
		this.add(department);
		this.add(major);
		this.add(lecture);
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
						lecture.model.setRowCount(0);
					}
				} else if (e.getSource() == department.list) {
					String fileName = department.getSelectedFileName();
					if (!fileName.equals("timeOut")) {
						major.setFile(fileName);
						lecture.model.setRowCount(0);
					}
				} else if (e.getSource() == major.list) {
					String fileName = major.getSelectedFileName();
					if (!fileName.equals("timeOut")) {
						lecture.SetFileName(fileName);
						lecture.showTable();
					}
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
		lecture.setUserID(userID);
	}
}