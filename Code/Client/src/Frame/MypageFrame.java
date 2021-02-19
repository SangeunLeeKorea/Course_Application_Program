package Frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import main.Stub;

public class MypageFrame extends JFrame {

	Label LId = new Label("아이디: ");
	Label LPw = new Label("비밀번호: ");
	Label LName = new Label("이름: ");
	Label LStudentNumber = new Label("학번: ");
	Label LMajor = new Label("전공: ");
	Label LPhone = new Label("전화번호: ");
	Label TId = new Label();
	JLabel picture;
	TextField TPw = new TextField();
	TextField TName = new TextField();
	TextField TStudentNumber = new TextField();
	TextField TMajor = new TextField();
	TextField TPhone = new TextField();

	TextField PWFix = new TextField();
	TextField NameFix = new TextField();
	TextField StudentNumberFix = new TextField();
	TextField MajorFix = new TextField();
	TextField PhoneFix = new TextField();

	JButton fix = new JButton("수정하기");
	JButton checkPW = new JButton("비밀번호 보이기");
	JButton fixFinished = new JButton("수정 완료");
	JButton fixPic = new JButton("프로필 사진 변경");
	String real;

	ImageIcon logo;
	Container logoContainer;

	GridBagConstraints gbc;
	GridBagLayout gbl;

	String userID;
	Stub stub;

	public MypageFrame(String UserID, Stub stub) {
		this.stub = stub;
		this.userID = UserID;
		this.setLocationRelativeTo(null);
		this.setSize(1000, 700);
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 20, 0, 20);
		this.setLayout(gbl);

		gbc = layout(gbc, 0, 0, 0, 0, 1, 1);
		gbl.setConstraints(LName, gbc);
		gbc = layout(gbc, 0, 0, 1, 0, 1, 1);
		gbl.setConstraints(TName, gbc);

		gbc = layout(gbc, 0, 0, 0, 1, 1, 1);
		gbl.setConstraints(LId, gbc);
		gbc = layout(gbc, 0, 0, 1, 1, 1, 1);
		gbl.setConstraints(TId, gbc);

		gbc = layout(gbc, 0, 0, 0, 2, 1, 1);
		gbl.setConstraints(LPw, gbc);
		gbc = layout(gbc, 0, 0, 1, 2, 1, 1);
		gbl.setConstraints(TPw, gbc);
		gbc = layout(gbc, 0, 0, 2, 2, 1, 1);
		gbl.setConstraints(checkPW, gbc);

		gbc = layout(gbc, 0, 0, 0, 3, 1, 1);
		gbl.setConstraints(LStudentNumber, gbc);
		gbc = layout(gbc, 0, 0, 1, 3, 1, 1);
		gbl.setConstraints(TStudentNumber, gbc);

		gbc = layout(gbc, 0, 0, 0, 4, 1, 1);
		gbl.setConstraints(LMajor, gbc);
		gbc = layout(gbc, 0, 0, 1, 4, 1, 1);
		gbl.setConstraints(TMajor, gbc);

		gbc = layout(gbc, 0, 0, 0, 5, 1, 1);
		gbl.setConstraints(LPhone, gbc);
		gbc = layout(gbc, 0, 0, 1, 5, 1, 1);
		gbl.setConstraints(TPhone, gbc);

		gbc = layout(gbc, 0, 0, 0, 6, 1, 1);
		gbl.setConstraints(fix, gbc);
		gbc = layout(gbc, 0, 0, 1, 6, 1, 1);
		gbl.setConstraints(fixFinished, gbc);
		TextFieldSetEnable(false);

		picture = setPicture("initialize");
		gbc = layout(gbc, 0, 0, 3, 0, 3, 5);
		gbl.setConstraints(picture, gbc);
		gbc = layout(gbc, 0, 0, 3, 6, 2, 1);
		gbl.setConstraints(fixPic, gbc);

		this.add(LId);
		this.add(LPw);
		this.add(LName);
		this.add(LStudentNumber);
		this.add(LMajor);
		this.add(LPhone);
		this.add(TId);
		this.add(TPw);
		this.add(TName);
		this.add(TStudentNumber);
		this.add(TMajor);
		this.add(TPhone);
		this.add(checkPW);
		this.add(fix);
		this.add(fixFinished);
		this.add(fixPic);
		fixFinished.setVisible(false);
		this.setVisible(true);

		ActionListener checkPWPushed = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TPw.setText(real);
			}
		};
		checkPW.addActionListener(checkPWPushed);

		ActionListener fixPushed = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fix.setVisible(false);
				TPw.setText(real);
				TextFieldSetEnable(true);
				fixFinished.setVisible(true);
			}
		};
		fix.addActionListener(fixPushed);

		ActionListener FinishedPush = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					fix();
					TextFieldSetEnable(false);
					fixFinished.setVisible(false);
					setTPwStar();
					fix.setVisible(true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		};
		fixFinished.addActionListener(FinishedPush);

		ActionListener fixPicPushed = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!(picture == null)) {
					picture.removeAll();
				}
				findPicture findPic = new findPicture();
				String found = findPic.find();
				picture = setPicture(found);
				if (!picture.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "선택한 사진으로 프로필 사진을 바꾸었습니다.\n수정된 사진은 재접속 시 적용됩니다.",
							"프로필 사진 수정 성공", JOptionPane.PLAIN_MESSAGE);
				}
			}
		};
		fixPic.addActionListener(fixPicPushed);
	}

	private void TextFieldSetEnable(boolean b) {
		TPw.setEnabled(b);
		TName.setEnabled(b);
		TStudentNumber.setEnabled(b);
		TMajor.setEnabled(b);
		TPhone.setEnabled(b);
	}

	private void setTPwStar() {
		real = TPw.getText();
		String star = "";
		for (int i = 0; i < real.length(); i++) {
			star += "*";
		}
		TPw.setText(star);
	}

	public void getProfile() throws FileNotFoundException {
		Vector<Object> datas = new Vector<Object>();
		datas.add(userID);
		Vector<Object> result = stub.runSession("ProfileManager", "getProfile", datas);
		if (result.get(0).equals("timeOut")) {
			this.dispose();
		} else if (result.size() == 6) {
			TId.setText((String) result.get(0));
			TName.setText((String) result.get(1));
			TPw.setText((String) result.get(2));
			TStudentNumber.setText((String) result.get(3));
			TMajor.setText((String) result.get(4));
			TPhone.setText((String) result.get(5));
			setTPwStar();
		}
	}

	public void fix() throws IOException {
		Vector<Object> datas = new Vector<Object>();
		datas.add(TId.getText());
		datas.add(TName.getText());
		datas.add(TPw.getText());
		datas.add(TStudentNumber.getText());
		datas.add(TMajor.getText());
		datas.add(TPhone.getText());
		Object check = stub.runSession("ProfileManager", "fixProfile", datas).get(0);
		if (!check.equals("timeOut")) {
			JOptionPane.showMessageDialog(null, "입력한 회원 정보를 수정하였습니다.", "회원정보 수정 성공", JOptionPane.PLAIN_MESSAGE);
		} else {
			this.dispose();
		}
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

	public JLabel setPicture(String doWhat) {
		String imgFormat = "png";
		int newWidth = 420;
		int newHeight = 355;

		Image image = null;
		Image fixedImage = null;
		BufferedImage ProfileImage = null;

		Object check = null;
		JLabel logoLabel = new JLabel();

		boolean run = true;

		try {
			if (!doWhat.equals("initialize")) {
				image = ImageIO.read(new File(doWhat));
				fixedImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
				BufferedImage buffered = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g = buffered.createGraphics();
				g.drawImage(fixedImage, 0, 0, null);
				g.dispose();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(buffered, imgFormat, baos);
				baos.flush();
				byte[] imageInByte = baos.toByteArray();
				Vector<Object> datas = new Vector<Object>();
				datas.add(userID);
				datas.add(imageInByte);
				check = stub.runSession("ProfileManager", "setPicture", datas).get(0);
				if (check.equals("timeOut")) {
					run = false;
					this.dispose();
				}
			}
			if (run) {
				Vector<Object> datas = new Vector<Object>();
				datas.add(userID);
				Vector<Object> result = stub.runSession("ProfileManager", "getPicture", datas);
				if (result.get(0).equals("timeOut")) {
					this.dispose();
				} else {
					byte[] imageInByte = (byte[]) result.get(0);
					ByteArrayInputStream inputStream = new ByteArrayInputStream(imageInByte);
					ProfileImage = ImageIO.read(inputStream);
					logo = new ImageIcon(ProfileImage);
					logoLabel = new JLabel(logo);
					logoContainer = getContentPane();
					logoContainer.add(logoLabel);
					logoContainer.setBackground(Color.WHITE);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return logoLabel;
	}

	class findPicture {
		public String find() {
			File savefile;
			String savepathname;

			JFileChooser chooser = new JFileChooser();// 객체 생성
			chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PNG", "png"));
			chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("JPEG", "jpeg"));
			chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("JPG", "jpg"));
			chooser.setCurrentDirectory(new File("C:\\Users\\User\\Desktop"));
			chooser.setFileSelectionMode(chooser.FILES_ONLY);

			int re = chooser.showSaveDialog(null);
			if (re == JFileChooser.APPROVE_OPTION) {
				savefile = chooser.getSelectedFile();
				savepathname = savefile.getAbsolutePath();
				File f = new File("color&image//" + userID + "Picture.png");
				String finalPath = f.getAbsolutePath();
				return savepathname;
			} else {
				JOptionPane.showMessageDialog(null, "경로를 선택하지않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				return null;
			}
		}
	}

}
