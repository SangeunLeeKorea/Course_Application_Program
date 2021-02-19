package Frame;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import main.Client;
import main.Stub;

public class LoginFrame extends JFrame {

	Client main;

	JLabel ID_Label = new JLabel("아이디");
	JLabel PW_Label = new JLabel("비밀번호");
	public JButton join = new JButton("회원가입");
	JButton IDFind = new JButton("아이디 찾기");
	JButton PWFind = new JButton("비밀번호 찾기");
	JTextField ID_Write = new JTextField("");
	JPasswordField PW_Write = new JPasswordField("");
	public String UserID = null;
	static String UserPW = null;
	public boolean pushed = false;
	public JButton LoginB = new JButton("로그인");
	GridBagConstraints gbc;
	ImageIcon logo;
	Container logoContainer;

	Checkbox professor = new Checkbox("교수 로그인");
	boolean professorLogin = false;

	Stub stub;

	public LoginFrame(ActionListener handler, Stub stub) {
		GridBagLayout gbl;
		this.setSize(600, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		logo = new ImageIcon("color&image//logo.png");
		JLabel logoLabel = new JLabel(logo);
		logoContainer = getContentPane();
		logoContainer.add(logoLabel);
		logoContainer.setBackground(Color.WHITE);
		this.addWindowListener(new Close());

		this.stub = stub;

		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(10, 20, 0, 20);
		this.setLayout(gbl);
		gbc = layout(gbc, 0.1, 0, 0, 0, 4, 1);
		gbl.setConstraints(logoLabel, gbc);
		gbc = layout(gbc, 0, 0, 0, 1, 1, 1);
		gbl.setConstraints(ID_Label, gbc);
		gbc = layout(gbc, 0, 0, 0, 2, 1, 1);
		gbl.setConstraints(PW_Label, gbc);
		gbc = layout(gbc, 0.1, 0, 1, 1, 2, 1);
		gbl.setConstraints(ID_Write, gbc);
		gbc = layout(gbc, 0.1, 0, 1, 2, 2, 1);
		gbl.setConstraints(PW_Write, gbc);
		gbc = layout(gbc, 0.1, 0, 0, 3, 1, 1);
		gbl.setConstraints(join, gbc);
		gbc = layout(gbc, 0.1, 0, 3, 1, 1, 2);
		gbl.setConstraints(LoginB, gbc);
		gbc = layout(gbc, 0.1, 0, 1, 3, 1, 1);
		gbl.setConstraints(IDFind, gbc);
		gbc = layout(gbc, 0.1, 0, 2, 3, 1, 1);
		gbl.setConstraints(PWFind, gbc);
		gbc = layout(gbc, 0, 0, 3, 3, 1, 1);
		gbl.setConstraints(professor, gbc);

		join.setBackground(new Color(075, 101, 128));
		LoginB.setBackground(new Color(075, 101, 128));
		IDFind.setBackground(new Color(075, 101, 128));
		PWFind.setBackground(new Color(075, 101, 128));
		join.setForeground(Color.WHITE);
		LoginB.setForeground(Color.WHITE);
		IDFind.setForeground(Color.WHITE);
		PWFind.setForeground(Color.WHITE);

		this.add(ID_Label);
		this.add(PW_Label);
		this.add(ID_Write);
		this.add(PW_Write);
		this.add(join);
		this.add(LoginB);
		this.add(IDFind);
		this.add(PWFind);
		this.add(professor);
		this.setVisible(true);

		ActionListener joinPush = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JoinFrame joinFrame = new JoinFrame(stub);
			}
		};
		join.addActionListener(joinPush);

		ActionListener IDFindPush = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				findID();
			}
		};
		IDFind.addActionListener(IDFindPush);

		ActionListener PWFindPush = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetPW();
			}
		};
		PWFind.addActionListener(PWFindPush);
		LoginB.addActionListener(handler);

		professor.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				professorLogin = (e.getStateChange() == 1 ? true : false);
				IDFind.setEnabled(!professorLogin);
				join.setEnabled(!professorLogin);
			}

		});
	}

	public boolean isProfessorLogin() {
		return professorLogin;
	}

	public void initiate() {
	}

	public String getUserID() {
		return UserID;
	}

	public String getUserPW() {
		return UserPW;
	}

	public void setUserIDPW() {
		UserID = ID_Write.getText();
		UserPW = PW_Write.getText();
	}

	public void error() {
		ID_Write.setText("");
		PW_Write.setText("");
	}

	public void DoDispose() {
		this.dispose();
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

	public void findID() {
		String Name = JOptionPane.showInputDialog("이름을 입력하세요.");
		if (Name == null) {
			JOptionPane.showMessageDialog(null, "아이디 찾기를 취소합니다.", "아이디 찾기 취소", JOptionPane.ERROR_MESSAGE);
		} else {
			Vector<Object> datas = new Vector<Object>();
			datas.add(Name);
			String result = (String) stub.runSession("LoginManager", "findID", datas).get(0);
			if (!result.equals("error")) {
				JOptionPane.showMessageDialog(null, "당신의 아이디는\n" + result + "\n입니다.", "아이디 검색 결과", 1);
			} else if (result.equals("error")) {
				JOptionPane.showMessageDialog(null, "검색 결과가 없습니다.", "아이디 찾기 실패", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void resetPW() {
		String Name = JOptionPane.showInputDialog("이름을 입력하세요.");
		if (Name != null) {
			Vector<Object> datas = new Vector<Object>();
			datas.add(Name);
			String ID = null;
			String result = null;
			if (!professorLogin) {
				ID = JOptionPane.showInputDialog("아이디를 입력하세요.");
				datas.add(ID);
				result = (String) stub.runSession("LoginManager", "findUser", datas).get(0);
			} else {
				result = (String) stub.runSession("ProfessorLoginManager", "findUser", datas).get(0);
			}
			if (!result.equals("error")) {
				String newPW = JOptionPane.showInputDialog("변경할 비밀번호를 입력하세요.");
				datas = new Vector<Object>();
				datas.add(Name);
				datas.add(newPW);
				if (professorLogin) {
					stub.runSession("ProfessorLoginManager", "rewrite", datas);
				} else {
					stub.runSession("LoginManager", "rewrite", datas);
				}
				JOptionPane.showMessageDialog(null, "비밀번호를 변경하였습니다.", "비밀번호 변경 완료", 1);
			} else if (ID == null&&!professorLogin) {
				JOptionPane.showMessageDialog(null, "비밀번호 찾기를 취소합니다.", "비밀번호 찾기 취소", JOptionPane.ERROR_MESSAGE);
			} else if (result.equals("error")) {
				JOptionPane.showMessageDialog(null, "사용자 검색 결과가 없습니다.", "비밀번호 찾기 실패", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "비밀번호 찾기를 취소합니다.", "비밀번호 찾기 취소", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void setPW() {
		PW_Write.setText("");
	}

	class Close implements WindowListener {
		@Override
		public void windowOpened(WindowEvent e) {
		}

		@Override
		public void windowClosing(WindowEvent e) {
			stub.quit();
			System.exit(0);
		}

		@Override
		public void windowClosed(WindowEvent e) {
		}

		@Override
		public void windowIconified(WindowEvent e) {
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
		}

		@Override
		public void windowActivated(WindowEvent e) {
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
		};
	}
}
