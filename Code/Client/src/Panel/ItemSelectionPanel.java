package Panel;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.io.FileNotFoundException;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

import main.Stub;

public class ItemSelectionPanel extends JPanel {
	public JList<String> list = new JList<String>();
	Vector<String> listData = new Vector<String>();
	Label listName = new Label("");
	JScrollPane scroll = new JScrollPane();
	GridBagConstraints gbc;
	GridBagLayout gbl;
	
	String fileName;
	
	Stub stub;

	public ItemSelectionPanel(ListSelectionListener listSelected, Stub stub) {
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 5, 0, 0);
		gbc.fill = GridBagConstraints.BOTH;
		gbl = new GridBagLayout();
		this.setLayout(gbl);
		scroll.setViewportView(list);
		list.setSelectedIndex(0);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(listSelected);
		layout(gbc, listName, 0.1, 0, 0, 0, 1, 1);
		gbl.setConstraints(listName, gbc);
		layout(gbc, scroll, 0.1, 0.1, 0, 1, 1, 1);
		gbl.setConstraints(scroll, gbc);
		this.add(listName);
		this.add(scroll);
		
		this.stub = stub;
	}

	public void setFile(String Filename){
		this.fileName = Filename;
		Vector<Object> datas = new Vector<Object>();
		datas.add(Filename);
		Vector<Object> result = (Vector<Object>)stub.runSession("FileManager", "getFile", datas);
		listData.removeAllElements();
		list.setListData(listData);
		for (int i = 0; i < result.size(); i++) {
			listData.add((String)result.get(i));
		}
		list.setListData(listData);
		list.repaint();
	}

	public void setLabel(String name) {
		listName.setText(name);
	}

	public String getSelectedFileName() throws FileNotFoundException {
		int selectedIndex = list.getSelectedIndex();
		if (selectedIndex == -1) {
			selectedIndex = 0;
		}
		String result = null;
		Vector<Object> datas = new Vector<Object>();
		datas.add(fileName);
		datas.add(Integer.toString(selectedIndex));
		result = (String)stub.runSession("FileManager", "findFile", datas).get(0);
		return result;
	}

	public GridBagConstraints layout(GridBagConstraints gbc, Component obj, double xleft, double yleft, int x, int y,
			int width, int height) {
		gbc.weightx = xleft; // x여백
		gbc.weighty = yleft; // y여백
		gbc.gridx = x; // 시작위치 x
		gbc.gridy = y; // 시작위치 y
		gbc.gridwidth = width; // 컨테이너 너비
		gbc.gridheight = height; // 컨테이너 높이
		return gbc;
	}
}
