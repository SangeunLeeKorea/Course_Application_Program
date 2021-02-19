package Frame;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import main.Stub;

public class AddFreeclassFrame extends JFrame {
	
	String fileName;
	Stub stub;
	GridBagConstraints gbc;
	
	Label LName = new Label("���� �̸�");
	Label LDay = new Label ("����");
	Label LTime = new Label("�ð�");
	Label dash = new Label("-");
	Label dot1 = new Label(":");
	Label dot2 = new Label(":");
	TextField TName = new TextField();
	TextField TDay = new TextField();
	TextField TTime1 = new TextField();
	TextField TTime2 = new TextField();
	TextField TTime3 = new TextField();
	TextField TTime4 = new TextField();
	
	JButton add = new JButton("�߰��ϱ�");
	JButton cancel = new JButton("���");
	
	public AddFreeclassFrame(String userID, ActionListener buttonPushed, Stub stub) {
		super("���� ���� �߰�");
		
		this.setSize(900, 500);
		GridBagLayout gbl;
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 0, 20);
		this.setLayout(gbl);
		
		gbc = layout(gbc, 0, 0, 0, 0, 1, 1);
		gbl.setConstraints(LName, gbc);
		gbc = layout(gbc, 0, 0, 1, 0, 7, 1);
		gbl.setConstraints(TName, gbc);
		
		gbc = layout(gbc, 0, 0, 0, 1, 1, 1);
		gbl.setConstraints(LDay, gbc);
		gbc = layout(gbc, 0, 0, 1, 1, 7, 1);
		gbl.setConstraints(TDay, gbc);
		
		gbc = layout(gbc, 0, 0, 0, 2, 1, 1);
		gbl.setConstraints(LTime, gbc);
		gbc = layout(gbc, 1, 0, 1, 2, 1, 1);
		gbl.setConstraints(TTime1, gbc);
		gbc = layout(gbc, 0, 0, 2, 2, 1, 1);
		gbl.setConstraints(dot1, gbc);
		gbc = layout(gbc, 1, 0, 3, 2, 1, 1);
		gbl.setConstraints(TTime2, gbc);
		gbc = layout(gbc, 0, 0, 4, 2, 1, 1);
		gbl.setConstraints(dash, gbc);
		gbc = layout(gbc, 1, 0, 5, 2, 1, 1);
		gbl.setConstraints(TTime3, gbc);
		gbc = layout(gbc, 0, 0, 6, 2, 1, 1);
		gbl.setConstraints(dot2, gbc);
		gbc = layout(gbc, 1, 0, 7, 2, 1, 1);
		gbl.setConstraints(TTime4, gbc);
		
		gbc = layout(gbc, 0, 0, 5, 3, 1, 1);
		gbl.setConstraints(add, gbc);
		gbc = layout(gbc, 0, 0, 7, 3, 1, 1);
		gbl.setConstraints(cancel, gbc);
		
		add.addActionListener(buttonPushed);
		cancel.addActionListener(buttonPushed);
		
		this.add(LName);
		this.add(TName);
		this.add(LDay);
		this.add(TDay);
		this.add(LTime);
		this.add(TTime1);
		this.add(TTime2);
		this.add(TTime3);
		this.add(TTime4);
		this.add(dash);
		this.add(dot1);
		this.add(dot2);
		this.add(add);
		this.add(cancel);
		
		this.setVisible(true);
		
		fileName = "Freeclass\\"+userID+"Freeclass.txt";
		this.stub = stub;
	}
	
	public GridBagConstraints layout(GridBagConstraints gbc, double xleft, double yleft, int x, int y, int width, int height){
		gbc.weightx = xleft; //x����
		gbc.weighty = yleft; //y����
		gbc.gridx=x; // ������ġ x
		gbc.gridy=y; // ������ġ y
		gbc.gridwidth=width; // �����̳� �ʺ�
		gbc.gridheight=height;  // �����̳� ����
		return gbc;
	 }
	
	public void doCANCEL() {
		int result = JOptionPane.showConfirmDialog(null, "������ ����Ͻðڽ��ϱ�?", "", JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			dispose();
		}
	}
	
	public boolean doADD() {
		String time = TTime1.getText()+TTime2.getText()+"-"+TTime3.getText()+TTime4.getText();
		String[] day = TDay.getText().split("");
		boolean daycheck = true;
		for (int i=0;i<day.length;i++) {
			if (!(day[i].equals("��")||day[i].equals("ȭ")||day[i].equals("��")||day[i].equals("��")||day[i].equals("��"))) {
				daycheck = false;
			}
		}
		if (TName.getText().equals("")||TDay.getText().equals("")||time.equals("-")) {
			JOptionPane.showMessageDialog(null, "����ִ� ���� �ֽ��ϴ�.", "���� �Ұ�", JOptionPane.ERROR_MESSAGE);
		} else if (Integer.parseInt(TTime1.getText()+TTime2.getText())>Integer.parseInt(TTime3.getText()+TTime4.getText())) {
			JOptionPane.showMessageDialog(null, "������ �ð��� ���� �ð����� �̸��ϴ�.", "���� �Ұ�", JOptionPane.ERROR_MESSAGE);
		} else if (!daycheck) {
			JOptionPane.showMessageDialog(null, "��~�ݿ����� ������ �Է� �����մϴ�.\n�Է� ���� ����: ��ȭ", "���� �Ұ�", JOptionPane.ERROR_MESSAGE);
		} else {
			Vector<Object> data = new Vector<Object>();
			data.add(fileName);
			data.add(TName.getText());
			data.add(TDay.getText());
			data.add(time);
			Object check = stub.runSession("FreeclassManager", "addFreeclass", data).get(0);
			if (!check.equals("timeOut")) {
				JOptionPane.showMessageDialog(null, "���� �Ϸ��Ͽ����ϴ�.", "���� �Ϸ�", JOptionPane.PLAIN_MESSAGE);
				return true;
			}
		}
		return false;
	}
	
	public JButton getAdd() {
		return add;
	}

	public JButton getCancel() {
		return cancel;
	}
}
