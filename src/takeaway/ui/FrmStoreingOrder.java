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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import takeaway.takeawayUtil;
import takeaway.model.BeanOrder;
import takeaway.model.BeanOrderInfo;
import takeaway.util.BaseException;

public class FrmStoreIngOrder extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnok = new Button("确定");
	private JPanel titleBar = new JPanel();
	private JLabel title = new JLabel("商品订单：                                                                                                                                                           订单详情：");
	
	private Object tblIngOrderTitle[]=BeanOrder.tableTitles;
	private Object tblIngOrderData[][];
	DefaultTableModel tabIngOrderModel=new DefaultTableModel();
	private JTable dataTableIngOrder=new JTable(tabIngOrderModel);
	
	private Object tblIngOrderInfoTitle[]=BeanOrderInfo.tblOrderInfoTitle;
	private Object tblIngOrderInfoData[][];
	DefaultTableModel tabIngOrderInfoModel=new DefaultTableModel();
	private JTable dataTableIngOrderInfo=new JTable(tabIngOrderInfoModel);
	
	private BeanOrder curIngOrder=null;
	List<BeanOrder> IngOrder=null;
	List<BeanOrderInfo> orderinfo=null;
	private void reloadIngOrder(){
		try {
			IngOrder=takeawayUtil.orderManager.loadstoreingOrder();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblIngOrderData = new Object[IngOrder.size()][BeanOrder.tableTitles.length];
		for(int i=0;i<IngOrder.size();i++){
			for(int j=0;j<BeanOrder.tableTitles.length;j++)
				tblIngOrderData[i][j]=IngOrder.get(i).getCell(j);
		}
		tabIngOrderModel.setDataVector(tblIngOrderData,tblIngOrderTitle);
		this.dataTableIngOrder.validate();
		this.dataTableIngOrder.repaint();
	}
	private void reloadIngOrderInfoTabel(int IngOrderIdx){
		if(IngOrderIdx<0) return;
		curIngOrder=IngOrder.get(IngOrderIdx);
		try {
			orderinfo=takeawayUtil.orderinfoManager.loadOrderInfo(curIngOrder);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		//定义一个二维对象，行大小为orderinfo.size()，列大小为BeanOrderInfo.tblOrderInfoTitle.length
		tblIngOrderInfoData =new Object[orderinfo.size()][BeanOrderInfo.tblOrderInfoTitle.length];
		for(int i=0;i<orderinfo.size();i++){
			for(int j=0;j<BeanOrderInfo.tblOrderInfoTitle.length;j++)
				//遍历输出每项
				tblIngOrderInfoData[i][j]=orderinfo.get(i).getCell(j);
		}
		//将实例中的值替换为数组中的值，行索引为tblOrderInfoData，列索引为tblOrderInfoTitle
		tabIngOrderInfoModel.setDataVector(tblIngOrderInfoData,tblIngOrderInfoTitle);
		this.dataTableIngOrderInfo.validate();
		this.dataTableIngOrderInfo.repaint();
	}
	public FrmStoreIngOrder(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("正在配送");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		titleBar.add(title);
		this.getContentPane().add(titleBar, BorderLayout.NORTH);
		//加入一个显示dataTableIngOrder的滚动条到页面的左边
	    this.getContentPane().add(new JScrollPane(this.dataTableIngOrder), BorderLayout.CENTER);
	    this.dataTableIngOrder.addMouseListener(new MouseAdapter (){
			@Override
			//在组件上单击鼠标按钮时调用函数
			public void mouseClicked(MouseEvent e) {
				//返回所选第一行的索引
				int i=FrmStoreIngOrder.this.dataTableIngOrder.getSelectedRow();
				//若没有选择行则返回-1
				if(i<0) {
					return;
				}
				//加载对应商品列表
				FrmStoreIngOrder.this.reloadIngOrderInfoTabel(i);
			}
		});
	    this.getContentPane().add(new JScrollPane(this.dataTableIngOrderInfo), BorderLayout.EAST);
	    this.reloadIngOrder();
		this.setSize(1100, 250);
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
