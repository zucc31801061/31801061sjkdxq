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

public class FrmAddProduct extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	
	private JLabel newname = new JLabel("商品：");
	private JLabel newkind = new JLabel("分类：");
	private JLabel startmoney = new JLabel("原价：");
	private JLabel endmoney = new JLabel("优惠价：");
	
	private JTextField name = new JTextField(20);
	private JTextField kind = new JTextField(20);
	private JTextField start= new JTextField(20);
	private JTextField end = new JTextField(20);
	
	public FrmAddProduct(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(newname);
		workPane.add(name);
		workPane.add(newkind);
		workPane.add(kind);
		workPane.add(startmoney);
		workPane.add(start);
		workPane.add(endmoney);
		workPane.add(end);
		//新信息居中
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(280, 200);
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
			String name=this.name.getText();
			String kind=this.kind.getText();
			Float start=Float.parseFloat(this.start.getText());
			Float end=Float.parseFloat(this.end.getText());
			try {
				takeawayUtil.productManager.addproduct(name,kind,start,end);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		}
	}
}
