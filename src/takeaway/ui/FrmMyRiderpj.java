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

public class FrmMyRiderpj extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button goodpj = new Button("好评");
	private Button badpj = new Button("差评");
	private Button delpj = new Button("删除评价");
	private Button btnok = new Button("确定");
	//地址列表
	//表项标题
	private Object tblpjTitle[]=BeanOrder.tableTitles5;
	//二维表存储
	private Object tblpjData[][];
	//创建表格模型
	DefaultTableModel tabpjModel=new DefaultTableModel();
	//用tabpjModel为模型构造表格
	private JTable dataTablepj=new JTable(tabpjModel);
	
	BeanOrder curpj;
	List<BeanOrder> pj=null;
	private void reloadpjTable(){//这是测试数据，需要用实际数替换
		try {
			pj=takeawayUtil.orderManager.loaduserpj();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblpjData = new Object[pj.size()][BeanOrder.tableTitles5.length];
		for(int i=0;i<pj.size();i++){
			for(int j=0;j<BeanOrder.tableTitles5.length;j++)
				tblpjData[i][j]=pj.get(i).getCell5(j);
		}
		tabpjModel.setDataVector(tblpjData,tblpjTitle);
		this.dataTablepj.validate();
		//验证容器及其子组件
		this.dataTablepj.repaint();
		//重绘该组件
	}
	public FrmMyRiderpj(JFrame f, String s, boolean b) {
		super(f, s, b);
		//设置窗口标题
		this.setTitle("骑手评价");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(goodpj);
		toolBar.add(badpj);
		toolBar.add(delpj);
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		//加入一个显示dataTableStore的滚动条到页面的左边
	    this.getContentPane().add(new JScrollPane(this.dataTablepj), BorderLayout.WEST);
	  //添加鼠标监听器组件
	    this.dataTablepj.addMouseListener(new MouseAdapter (){
			@Override
	    	//在组件上单击鼠标按钮时调用函数
			public void mouseClicked(MouseEvent e) {
				//返回所选第一行的索引
				int i=FrmMyRiderpj.this.dataTablepj.getSelectedRow();
				//若没有选择行则返回-1
				if(i<0) {
					return;
				}
				curpj=pj.get(i);
			}
	    });
	    this.reloadpjTable();
		this.setSize(460, 300);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.goodpj.addActionListener(this);
		this.badpj.addActionListener(this);
		this.delpj.addActionListener(this);
		this.btnok.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnok) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.goodpj) {
			if(this.curpj==null) {
				JOptionPane.showMessageDialog(null, "请选择订单", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.orderManager.updhppj(this.curpj);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		}
		else if(e.getSource()==this.badpj) {
			if(this.curpj==null) {
				JOptionPane.showMessageDialog(null, "请选择订单", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.orderManager.updcppj(this.curpj);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		}
		else if(e.getSource()==this.delpj) {
			if(this.curpj==null) {
				JOptionPane.showMessageDialog(null, "请选择订单", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.orderManager.delpj(this.curpj);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		}
	}
}
