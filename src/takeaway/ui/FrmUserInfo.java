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

public class FrmUserInfo extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button updateinfo = new Button("修改信息");
	private Button btnok = new Button("确定");
	//信息列表
	//表项标题
	private Object tblUserInfoTitle[]=BeanUser.tableTitles;
	//二维表存储
	private Object tblUserInfoData[][];
	//创建表格模型
	DefaultTableModel tabUserInfoModel=new DefaultTableModel();
	//用tabUserInfoModel为模型构造表格
	private JTable dataTableUserInfo=new JTable(tabUserInfoModel);
	
	BeanUser UserInfo=null;
	private void reloadUserInfoTable(){//这是测试数据，需要用实际数替换
		try {
			//查询当前UserInfo
			UserInfo=takeawayUtil.userManager.SearchInfo();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblUserInfoData = new Object[1][BeanUser.tableTitles.length];
		for(int i=0;i<1;i++){
			for(int j=0;j<BeanUser.tableTitles.length;j++)
				tblUserInfoData[i][j]=UserInfo.getCell(j);
		}
		tabUserInfoModel.setDataVector(tblUserInfoData,tblUserInfoTitle);
		this.dataTableUserInfo.validate();
		//验证容器及其子组件
		this.dataTableUserInfo.repaint();
		//重绘该组件
	}
	public FrmUserInfo(JFrame f, String s, boolean b) {
		super(f, s, b);
		//设置窗口标题
		this.setTitle("我的信息");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(updateinfo);
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		//加入一个显示dataTableUserInfo的滚动条到页面的左边
	    this.getContentPane().add(new JScrollPane(this.dataTableUserInfo), BorderLayout.WEST);
		this.reloadUserInfoTable();
		this.setSize(460, 150);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.updateinfo.addActionListener(this);
		this.btnok.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnok) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.updateinfo){
			FrmUpdateInfo uf=new FrmUpdateInfo(this,"修改信息",true);
			uf.setVisible(true);
			this.reloadUserInfoTable();
		}
	}
}
