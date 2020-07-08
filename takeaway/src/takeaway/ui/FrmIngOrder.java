package takeaway.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import takeaway.takeawayUtil;
import takeaway.model.BeanOrder;
import takeaway.util.BaseException;

public class FrmIngOrder extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnok = new Button("确定");
	//表项标题
	private Object tblIngOrderTitle[]=BeanOrder.tableTitles1;
	//二维表存储
	private Object tblIngOrderData[][];
	//创建表格模型
	DefaultTableModel tabIngOrderModel=new DefaultTableModel();
	//用tabIngOrderModel为模型构造表格
	private JTable dataTableIngOrder=new JTable(tabIngOrderModel);
	List<BeanOrder> IngOrder=null;
	private void reloadIngOrder(){//这是测试数据，需要用实际数替换
		try {
			//查询当前IngOrder
			IngOrder=takeawayUtil.orderManager.loaduserIngOrder();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblIngOrderData = new Object[IngOrder.size()][BeanOrder.tableTitles1.length];
		for(int i=0;i<IngOrder.size();i++){
			for(int j=0;j<BeanOrder.tableTitles1.length;j++)
				tblIngOrderData[i][j]=IngOrder.get(i).getCell1(j);
		}
		tabIngOrderModel.setDataVector(tblIngOrderData,tblIngOrderTitle);
		this.dataTableIngOrder.validate();
		//验证容器及其子组件
		this.dataTableIngOrder.repaint();
		//重绘该组件
	}
	public FrmIngOrder(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("我的信息");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		//加入一个显示dataTableIngOrder的滚动条到页面的左边
	    this.getContentPane().add(new JScrollPane(this.dataTableIngOrder), BorderLayout.WEST);
		this.reloadIngOrder();
		this.setSize(460, 150);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnok.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnok) {
			this.setVisible(false);
			return;
		}
	}
}
