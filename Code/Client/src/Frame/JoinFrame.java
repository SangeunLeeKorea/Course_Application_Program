package Frame;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import main.Stub;

public class JoinFrame extends JFrame {
	Label LName = new Label("이름");
	Label LId = new Label("아이디");
	Label LPw = new Label("비밀번호");
	Label LPwCheck = new Label("비밀번호 확인");
	Label LStudentNumber = new Label("학번");
	Label LMajor = new Label("전공");
	Label LPhone = new Label("전화번호");
	Label dash1 = new Label("-");
	Label dash2 = new Label("-");
	JTextField TName = new JTextField();
	JTextField TId = new JTextField();
	JPasswordField TPw = new JPasswordField();
	JPasswordField TPwCheck = new JPasswordField();
	JTextField TStudentNumber = new JTextField();
	JTextField TMajor = new JTextField();
	JTextField TPhone1 = new JTextField();
	JTextField TPhone2 = new JTextField();
	JTextField TPhone3 = new JTextField();
	JButton CheckSameId = new JButton("아이디 중복 확인");
	JButton Finished = new JButton("회원가입 완료");

	String CheckedID = "";
	Vector<String> Profile;

	CheckWritten checkWritten = new CheckWritten();

	GridBagConstraints gbc;
	
	Stub stub;

	public JoinFrame(Stub stub) {
		this.setLocationRelativeTo(null);
		this.setSize(900, 500);
		GridBagLayout gbl;
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 4, 0, 4);
		this.setLayout(gbl);
		
		this.stub = stub;

		gbc = layout(gbc, 0, 0, 0, 0, 1, 1);
		gbl.setConstraints(LName, gbc);
		gbc = layout(gbc, 0, 0, 1, 0, 5, 1);
		gbl.setConstraints(TName, gbc);

		gbc = layout(gbc, 0, 0, 0, 1, 1, 1);
		gbl.setConstraints(LId, gbc);
		gbc = layout(gbc, 0, 0, 1, 1, 5, 1);
		gbl.setConstraints(TId, gbc);
		gbc = layout(gbc, 0, 0, 6, 1, 1, 1);
		gbl.setConstraints(CheckSameId, gbc);

		gbc = layout(gbc, 0, 0, 0, 2, 1, 1);
		gbl.setConstraints(LPw, gbc);
		gbc = layout(gbc, 0, 0, 1, 2, 5, 1);
		gbl.setConstraints(TPw, gbc);

		gbc = layout(gbc, 0, 0, 0, 3, 1, 1);
		gbl.setConstraints(LPwCheck, gbc);
		gbc = layout(gbc, 0, 0, 1, 3, 5, 1);
		gbl.setConstraints(TPwCheck, gbc);

		gbc = layout(gbc, 0, 0, 0, 4, 1, 1);
		gbl.setConstraints(LStudentNumber, gbc);
		gbc = layout(gbc, 0, 0, 1, 4, 5, 1);
		gbl.setConstraints(TStudentNumber, gbc);

		gbc = layout(gbc, 0, 0, 0, 5, 1, 1);
		gbl.setConstraints(LMajor, gbc);
		gbc = layout(gbc, 0, 0, 1, 5, 5, 1);
		gbl.setConstraints(TMajor, gbc);

		gbc = layout(gbc, 0, 0, 0, 6, 1, 1);
		gbl.setConstraints(LPhone, gbc);
		gbc = layout(gbc, 0.1, 0, 1, 6, 1, 1);
		gbl.setConstraints(TPhone1, gbc);
		gbc = layout(gbc, 0, 0, 2, 6, 1, 1);
		gbl.setConstraints(dash1, gbc);
		gbc = layout(gbc, 0.1, 0, 3, 6, 1, 1);
		gbl.setConstraints(TPhone2, gbc);
		gbc = layout(gbc, 0, 0, 4, 6, 1, 1);
		gbl.setConstraints(dash2, gbc);
		gbc = layout(gbc, 0.1, 0, 5, 6, 1, 1);
		gbl.setConstraints(TPhone3, gbc);

		gbc = layout(gbc, 0, 0, 0, 7, 1, 1);
		gbl.setConstraints(Finished, gbc);

		this.add(LName);
		this.add(LId);
		this.add(LPw);
		this.add(LPwCheck);
		this.add(LStudentNumber);
		this.add(LMajor);
		this.add(LPhone);
		this.add(TName);
		this.add(TId);
		this.add(CheckSameId);
		this.add(TPw);
		this.add(TPwCheck);
		this.add(TStudentNumber);
		this.add(TMajor);
		this.add(TPhone1);
		this.add(dash1);
		this.add(TPhone2);
		this.add(dash2);
		this.add(TPhone3);
		this.add(Finished);
		this.setVisible(true);

		TPhone1.addKeyListener(checkWritten);
		TPhone2.addKeyListener(checkWritten);
		TPhone3.addKeyListener(checkWritten);

		ActionListener IDCheckPushed = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String ID = TId.getText();
				Vector<Object> datas = new Vector<Object>();
				datas.add(ID);
				String result = (String)stub.runSession("ProfileManager", "checkID", datas).get(0);
				if (result.equals("exist")) {
					JOptionPane.showMessageDialog(null, "입력한 아이디가 이미 존재합니다.", "아이디 중복", JOptionPane.ERROR_MESSAGE);
				} else if (ID.equals("")) {
					JOptionPane.showMessageDialog(null, "사용할 아이디를 입력하지 않았습니다.", "아이디 미입력",
							JOptionPane.ERROR_MESSAGE);
				} else {
					CheckedID = ID;
					JOptionPane.showMessageDialog(null, "입력한 아이디를 사용할 수 있습니다.", "아이디 사용 가능",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		};
		CheckSameId.addActionListener(IDCheckPushed);

		ActionListener FinishedPush = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!CheckedID.equals(TId.getText())) {
					JOptionPane.showMessageDialog(null, "아이디 중복 확인을 해야합니다.", "아이디 중복 확인 필요", JOptionPane.ERROR_MESSAGE);
				} else if (!TPw.getText().equals(TPwCheck.getText())) {
					JOptionPane.showMessageDialog(null, "입력한 비밀번호가 서로 일치하지 않습니다.", "비밀번호 확인 필요",
							JOptionPane.ERROR_MESSAGE);
				} else {
					Vector<Object> datas = new Vector<Object>();
					datas.add(TId.getText());
					datas.add(TName.getText());
					datas.add(TPw.getText());
					datas.add(TStudentNumber.getText());
					datas.add(TMajor.getText());
					datas.add(TPhone1.getText() + "-" + TPhone2.getText() + "-" + TPhone3.getText());
					stub.runSession("ProfileManager", "join", datas);
					JOptionPane.showMessageDialog(null, "입력한 회원정보로 회원가입이 되었습니다.", "회원가입 성공", JOptionPane.PLAIN_MESSAGE);
					dispose();
				}
			}
		};
		Finished.addActionListener(FinishedPush);
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

	public void notNumber(JTextField phoneText) {
		String text = phoneText.getText();
		if (text.length() == 0) {
			phoneText.setText("");
		} else {
			text = text.substring(0, text.length() - 1);
			phoneText.setText(text);
		}
	}

	private class CheckWritten implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getSource() == TPhone1) {
				if (!(e.getKeyChar() >= '0' && e.getKeyChar() <= '9')) {
					notNumber(TPhone1);
				}
			} else if (e.getSource() == TPhone2) {
				if (!(e.getKeyChar() >= '0' && e.getKeyChar() <= '9')) {
					notNumber(TPhone2);
				}
			} else if (e.getSource() == TPhone3) {
				if (!(e.getKeyChar() >= '0' && e.getKeyChar() <= '9')) {
					notNumber(TPhone3);
				}
			}
		}

	}
}
