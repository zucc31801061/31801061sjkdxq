package takeaway.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import takeaway.takeawayUtil;
import takeaway.util.BaseException;

public class FrmUpdateVip extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	
	private JLabel userno = new JLabel("用户账号：");
	private JLabel updatevip= new JLabel("修改会员(true/false)：");
	private JLabel updatedate = new JLabel("会员到期时间(yyyy-MM-dd)：");
	
	private JTextField user = new JTextField(20);
	private JTextField vip = new JTextField(20);
	private JTextField date = new JTextField(20);
	
	public FrmUpdateVip(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		//按键居下
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(userno);
		workPane.add(user);
		workPane.add(updatevip);
		workPane.add(vip);
		workPane.add(updatedate);
		workPane.add(date);
		//新信息居中
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(250, 250);
		// 窗口居中
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
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String user=this.user.getText();
			boolean vip=Boolean.parseBoolean(this.vip.getText());
			Date date1=new Date();
			try {
				date1 = simpleDateFormat.parse(this.date.getText());
				
			} catch (ParseException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			java.sql.Date date = new java.sql.Date(date1.getTime());
			try {
				takeawayUtil.userManager.changevip(user, vip, date);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
	}
}
