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

public class FrmAddYh extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	
	private JLabel newmoney = new JLabel("优惠金额：");
	private JLabel newnum = new JLabel("集单要求：");
	private JLabel startdate = new JLabel("起始日期（yyyy-MM-dd）：");
	private JLabel enddate = new JLabel("结束日期（yyyy-MM-dd）：");
	
	private JTextField money = new JTextField(20);
	private JTextField num = new JTextField(20);
	private JTextField start= new JTextField(20);
	private JTextField end= new JTextField(20);
	
	public FrmAddYh(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(newmoney);
		workPane.add(money);
		workPane.add(newnum);
		workPane.add(num);
		workPane.add(startdate);
		workPane.add(start);
		workPane.add(enddate);
		workPane.add(end);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(280, 300);
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
			int money=Integer.valueOf(this.money.getText());
			int num=Integer.valueOf(this.num.getText());
			Date start=new Date();
			try {
				start = simpleDateFormat.parse(this.start.getText());
			} catch (ParseException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			Date end=new Date();
			try {
				end = simpleDateFormat.parse(this.end.getText());
			} catch (ParseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				takeawayUtil.yhManager.Addyh(money, num, start, end);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		}
	}
}
