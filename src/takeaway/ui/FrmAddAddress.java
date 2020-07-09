package takeaway.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import takeaway.takeawayUtil;
import takeaway.util.BaseException;

public class FrmAddAddress extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");
	
	private JLabel NewSheng = new JLabel("ʡ��");
	private JLabel NewShi = new JLabel("�У�");
	private JLabel NewQu = new JLabel("����");
	private JLabel NewAddress = new JLabel("��ַ��");
	private JLabel NewName = new JLabel("�û�����");
	private JLabel NewPhnum = new JLabel("�ֻ��ţ�");
	
	private JTextField Sheng = new JTextField(20);
	private JTextField Shi = new JTextField(20);
	private JTextField Qu= new JTextField(20);
	private JTextField Address = new JTextField(20);
	private JTextField Name = new JTextField(20);
	private JTextField Phnum = new JTextField(20);
	
	public FrmAddAddress(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(NewSheng);
		workPane.add(Sheng);
		workPane.add(NewShi);
		workPane.add(Shi);
		workPane.add(NewQu);
		workPane.add(Qu);
		workPane.add(NewAddress);
		workPane.add(Address);
		workPane.add(NewName);
		workPane.add(Name);
		workPane.add(NewPhnum);
		workPane.add(Phnum);
		//����Ϣ����
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(280, 270);
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
			String sheng=this.Sheng.getText();
			String shi=this.Shi.getText();
			String qu=this.Qu.getText();
			String address=this.Address.getText();
			String name=this.Name.getText();
			String phnum=this.Phnum.getText();
			try {
				takeawayUtil.addressManager.addAddress(sheng, shi, qu, address, name, phnum);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}
}
