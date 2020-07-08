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
import takeaway.model.BeanStore;
import takeaway.util.BaseException;


public class FrmStore extends JDialog implements ActionListener {
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button becomesj = new Button("成为商家");
	private Button alreadysj = new Button("已是商家");
	private Button cancel = new Button("取消");
	private JLabel sjname = new JLabel("请输入商家名：");
	private JTextField edtsjname = new JTextField(20);
	
	public FrmStore(JFrame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(becomesj);
		toolBar.add(alreadysj);
		toolBar.add(cancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(sjname);
		workPane.add(edtsjname);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(320, 150);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.becomesj.addActionListener(this);
		this.alreadysj.addActionListener(this);
		this.cancel.addActionListener(this);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.cancel) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.becomesj){
			String name=this.edtsjname.getText();
			try {
				takeawayUtil.storeManager.addStore(name);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.alreadysj){
			try {
				BeanStore.currentLoginstore= takeawayUtil.storeManager.login();
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
			FrmStoreMain fsm=new FrmStoreMain();
			fsm.setVisible(true);
		}
		
	}
	
	
}
