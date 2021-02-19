package Frame;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import main.Stub;

public class MemoFrame extends JFrame {
	String userID;
	Stub stub;
	GridBagConstraints gbc;
	GridBagLayout gbl;
	JMenuBar menuBar;
	JMenu file;
	JMenuItem save, deleteAll;

	JTextArea ta;

	ActionHandler handler;

	public MemoFrame(String userID, Stub stub) {
		this.userID = userID;
		this.stub = stub;

		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		this.gbl = new GridBagLayout();
		this.gbc = new GridBagConstraints();

		menuBar = new JMenuBar();
		file = new JMenu("파일");
		save = new JMenuItem("저장");
		deleteAll = new JMenuItem("내용 전체 삭제");
		menuBar.add(file);
		file.add(save);
		file.add(deleteAll);
		this.setJMenuBar(menuBar);
		
		handler = new ActionHandler();
		save.addActionListener(handler);
		deleteAll.addActionListener(handler);

		ta = new JTextArea(40, 50);
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		Border lineBorder = BorderFactory.createLineBorder(Color.black, 3);
		Border emptyBorder = BorderFactory.createEmptyBorder(7, 7, 7, 7);
		ta.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));
		String text = showMemo();
		if (text != null) {
			ta.setText(text);
		}
		this.add(new JScrollPane(ta));

		this.setVisible(true);
	}

	public void save() {
		Vector<Object> datas = new Vector<Object>();
		datas.add(userID);
		datas.add(ta.getText());
		Vector<Object> result = stub.runSession("MemoManager", "saveMemo", datas);
		String check = (String)result.get(0);
		if (!check.equals("timeOut")) {
			JOptionPane.showMessageDialog(null, "입력한 메모가 저장되었습니다.", "메모 저장", JOptionPane.PLAIN_MESSAGE);
		} else {
			this.dispose();
		}
	}

	public void deleteAll() {
		Vector<Object> datas = new Vector<Object>();
		datas.add(userID);
		Vector<Object> result = stub.runSession("MemoManager", "deleteAll", datas);
		System.out.println("delete 실행 완료");
		String check = (String)result.get(0);
		System.out.println("delete check");
		System.out.println(check);
		if (!check.equals("timeOut")) {
			JOptionPane.showMessageDialog(null, "메모를 모두 지웠습니다.", "메모 삭제", JOptionPane.PLAIN_MESSAGE);
		} else {
			this.dispose();
		}
	}

	public String showMemo() {
		Vector<Object> datas = new Vector<Object>();
		datas.add(userID);
		Vector<Object> result = stub.runSession("MemoManager", "showMemo", datas);
		if (!((String)result.get(0)).equals("timeOut")) {
			if (result.size()>0) {
				return (String) result.get(0);
			}
		}
		return null;
	}

	class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == save) {
				save();
			} else if (e.getSource() == deleteAll) {
				deleteAll();
				ta.setText(showMemo());
				repaint();
			}
		}
	}
}
