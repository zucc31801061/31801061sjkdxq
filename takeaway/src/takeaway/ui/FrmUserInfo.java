package takeaway.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import takeaway.takeawayUtil;
import takeaway.util.BaseException;


public class FrmUserInfo extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	/*private JPanel workPane1 = new JPanel();*/
	private JPanel workPane2 = new JPanel();
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");
	
	private JLabel NewName = new JLabel("���û�����");
	private JLabel NewSex = new JLabel("���Ա�");
	private JLabel NewPhnum = new JLabel("�µ绰�ţ�");
	private JLabel NewEmail = new JLabel("�������ַ��");
	private JLabel NewCity = new JLabel("�³��У�");
	
	private JTextField Name = new JTextField(20);
	private JTextField Sex = new JTextField(20);
	private JTextField Phnum = new JTextField(20);
	private JTextField Email = new JTextField(20);
	private JTextField City = new JTextField(20);
	
	public FrmUserInfo(JFrame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		//��������
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		/*workPane1.add(OldInfo);
		//ԭ��Ϣ����
		this.getContentPane().add(workPane1, BorderLayout.NORTH);*/
		workPane2.add(NewName);
		workPane2.add(Name);
		workPane2.add(NewSex);
		workPane2.add(Sex);
		workPane2.add(NewPhnum);
		workPane2.add(Phnum);
		workPane2.add(NewEmail);
		workPane2.add(Email);
		workPane2.add(NewCity);
		workPane2.add(City);
		//����Ϣ����
		this.getContentPane().add(workPane2, BorderLayout.CENTER);
		this.setSize(320, 210);
		// ���ھ���
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.btnOk){
			String name=this.Name.getText();
			String sex=this.Sex.getText();
			String phnum=this.Phnum.getText();
			String email=this.Email.getText();
			String city=this.City.getText();
			try {
				takeawayUtil.userManager.updateinfo(name,sex,phnum,email,city);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
	}
	
	
}
