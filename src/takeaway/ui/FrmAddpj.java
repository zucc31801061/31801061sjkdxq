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

public class FrmAddpj extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");
	
	private JLabel Newddno = new JLabel("������ţ�");
	private JLabel Newpjnr = new JLabel("�������ݣ�");
	private JLabel Newphoto = new JLabel("ͼƬ(true/false)��");
	private JLabel Newpjstar = new JLabel("�Ǽ���");
	
	private JTextField ddno = new JTextField(20);
	private JTextField pjnr = new JTextField(20);
	private JTextField photo= new JTextField(20);
	private JTextField pjstar = new JTextField(20);
	
	public FrmAddpj(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(Newddno);
		workPane.add(ddno);
		workPane.add(Newpjnr);
		workPane.add(pjnr);
		workPane.add(Newphoto);
		workPane.add(photo);
		workPane.add(Newpjstar);
		workPane.add(pjstar);
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
			int ddno=Integer.parseInt(this.ddno.getText());
			String pjnr=this.pjnr.getText();
			boolean photo=Boolean.parseBoolean(this.photo.getText());
			int pjstar=Integer.parseInt(this.pjstar.getText());
			try {
				takeawayUtil.sppjManager.addSppj(pjnr, pjstar, photo, ddno);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}
}
