package takeaway.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Visibility;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import takeaway.takeawayUtil;
import takeaway.model.BeanRider;
import takeaway.util.BaseException;

public class FrmRider extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button becomeqs = new Button("成为骑手");
	private Button alreadyqs = new Button("已是骑手");
	private Button cancel = new Button("取消");
	private JLabel qsname = new JLabel("请输入姓名：");
	private JTextField edtqsname = new JTextField(20);
	
	public FrmRider(JFrame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(becomeqs);
		toolBar.add(alreadyqs);
		toolBar.add(cancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(qsname);
		workPane.add(edtqsname);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(320, 150);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.becomeqs.addActionListener(this);
		this.alreadyqs.addActionListener(this);
		this.cancel.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.cancel) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.becomeqs){
			String name=this.edtqsname.getText();
			try {
				takeawayUtil.riderManager.addRider(name);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.alreadyqs){
			try {
				BeanRider.currentLoginrider= takeawayUtil.riderManager.login();
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
			FrmRiderMain rm=new FrmRiderMain();
			rm.setVisible(true);
		}
		
	}
}
