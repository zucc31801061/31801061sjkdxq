package takeaway.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

public class FrmTakeOrder extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button send = new Button("接单");
	private Button btnok = new Button("确定");
	//表项标题
	private Object tblFreeOrderTitle[]=BeanOrder.tableTitles2;
	//二维表存储
	private Object tblFreeOrderData[][];
	//创建表格模型
	DefaultTableModel tabFreeOrderModel=new DefaultTableModel();
	//用tabFreeOrderModel为模型构造表格
	private JTable dataTableFreeOrder=new JTable(tabFreeOrderModel);
	BeanOrder curOrder;
	List<BeanOrder> FreeOrder=null;
	private void reloadFreeOrder(){//这是测试数据，需要用实际数替换
		try {
			//查询当前FreeOrder
			FreeOrder=takeawayUtil.orderManager.loadriderfreeOrder();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblFreeOrderData = new Object[FreeOrder.size()][BeanOrder.tableTitles2.length];
		for(int i=0;i<FreeOrder.size();i++){
			for(int j=0;j<BeanOrder.tableTitles2.length;j++)
				tblFreeOrderData[i][j]=FreeOrder.get(i).getCell2(j);
		}
		tabFreeOrderModel.setDataVector(tblFreeOrderData,tblFreeOrderTitle);
		this.dataTableFreeOrder.validate();
		//验证容器及其子组件
		this.dataTableFreeOrder.repaint();
		//重绘该组件
	}
	public FrmTakeOrder(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("可接单");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(send);
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		//加入一个显示dataTableFreeOrder的滚动条到页面的左边
	    this.getContentPane().add(new JScrollPane(this.dataTableFreeOrder), BorderLayout.WEST);
	    this.dataTableFreeOrder.addMouseListener(new MouseAdapter (){
			@Override
	    	//在组件上单击鼠标按钮时调用函数
			public void mouseClicked(MouseEvent e) {
				//返回所选第一行的索引
				int i=FrmTakeOrder.this.dataTableFreeOrder.getSelectedRow();
				//若没有选择行则返回-1
				if(i<0) {
					return;
				}
				curOrder=FreeOrder.get(i);
			}
	    });
	    this.reloadFreeOrder();
		this.setSize(460, 250);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.send.addActionListener(this);
		this.btnok.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnok) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.send) {
			if(this.curOrder==null) {
				JOptionPane.showMessageDialog(null, "请选择订单", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.orderManager.takeOrder(this.curOrder);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		}
	}
}
