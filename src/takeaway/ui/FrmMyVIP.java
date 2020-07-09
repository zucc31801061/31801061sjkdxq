package takeaway.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import takeaway.takeawayUtil;
import takeaway.model.BeanUser;
import takeaway.util.BaseException;

public class FrmMyVIP  extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button pay = new Button("我要充值");
	private Button btnok = new Button("确定");
	//信息列表
	//表项标题
	private Object tblVIPTitle[]=BeanUser.tableTitles1;
	//二维表存储
	private Object tblVIPData[][];
	//创建表格模型
	DefaultTableModel tabVIPModel=new DefaultTableModel();
	//用tabMyVIPModel为模型构造表格
	private JTable dataTableVIP=new JTable(tabVIPModel);
	
	BeanUser VIP=null;
	private void reloadMyVIPTable(){//这是测试数据，需要用实际数替换
		try {
			//查询当前MyVIP
			VIP=takeawayUtil.userManager.SearchVIP();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblVIPData = new Object[1][BeanUser.tableTitles1.length];
		for(int i=0;i<1;i++){
			for(int j=0;j<BeanUser.tableTitles1.length;j++)
				tblVIPData[i][j]=VIP.getCell1(j);
		}
		tabVIPModel.setDataVector(tblVIPData,tblVIPTitle);
		this.dataTableVIP.validate();
		//验证容器及其子组件
		this.dataTableVIP.repaint();
		//重绘该组件
	}
	public FrmMyVIP(JFrame f, String s, boolean b) {
		super(f, s, b);
		//设置窗口标题
		this.setTitle("我的会员");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(pay);
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		//加入一个显示dataTableMyVIP的滚动条到页面的左边
	    this.getContentPane().add(new JScrollPane(this.dataTableVIP), BorderLayout.WEST);
		this.reloadMyVIPTable();
		this.setSize(460, 150);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.pay.addActionListener(this);
		this.btnok.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnok) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.pay){
			try {
				BeanUser user=takeawayUtil.userManager.PayVIP();
				this.setVisible(false);
			}catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}
}
