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

public class FrmAddMj extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	
	private JLabel newmoney = new JLabel("设置满减金额：");
	private JLabel newyh = new JLabel("设置满减优惠：");
	private JLabel newyhdj = new JLabel("叠加优惠券(true/false)：");
	
	private JTextField money = new JTextField(20);
	private JTextField yh = new JTextField(20);
	private JTextField yhdj= new JTextField(20);
	
	public FrmAddMj(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(newmoney);
		workPane.add(money);
		workPane.add(newyh);
		workPane.add(yh);
		workPane.add(newyhdj);
		workPane.add(yhdj);
		//新信息居中
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(380, 180);
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
			Float money=Float.parseFloat(this.money.getText());
			Float yh=Float.parseFloat(this.yh.getText());
			Boolean yhdj=Boolean.getBoolean(this.yhdj.getText());
			try {
				takeawayUtil.mjManager.Addmj(money, yh, yhdj);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		}
	}
}
