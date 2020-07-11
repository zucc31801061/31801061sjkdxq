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

public class FrmHisOrder extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnok = new Button("确定");
	private JPanel titleBar = new JPanel();
	private JLabel title = new JLabel("商品订单：                                                                                                                                                           订单详情：");

	private Object tblHisOrderTitle[]=BeanOrder.tableTitles1;
	private Object tblHisOrderData[][];
	DefaultTableModel tabHisOrderModel=new DefaultTableModel();
	private JTable dataTableHisOrder=new JTable(tabHisOrderModel);
	
	private Object tblHisOrderInfoTitle[]=BeanOrderInfo.tblOrderInfoTitle;
	private Object tblHisOrderInfoData[][];
	DefaultTableModel tabHisOrderInfoModel=new DefaultTableModel();
	private JTable dataTableHisOrderInfo=new JTable(tabHisOrderInfoModel);
	
	private BeanOrder curHisOrder=null;
	List<BeanOrder> HisOrder=null;
	List<BeanOrderInfo> orderinfo=null;
	private void reloadHisOrder(){
		try {
			HisOrder=takeawayUtil.orderManager.loaduserHisOrder();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblHisOrderData = new Object[HisOrder.size()][BeanOrder.tableTitles1.length];
		for(int i=0;i<HisOrder.size();i++){
			for(int j=0;j<BeanOrder.tableTitles1.length;j++)
				tblHisOrderData[i][j]=HisOrder.get(i).getCell1(j);
		}
		tabHisOrderModel.setDataVector(tblHisOrderData,tblHisOrderTitle);
		this.dataTableHisOrder.validate();
		this.dataTableHisOrder.repaint();
	}
	private void reloadHisOrderInfoTabel(int HisOrderIdx){
		if(HisOrderIdx<0) return;
		curHisOrder=HisOrder.get(HisOrderIdx);
		try {
			orderinfo=takeawayUtil.orderinfoManager.loadOrderInfo(curHisOrder);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		//定义一个二维对象，行大小为orderinfo.size()，列大小为BeanOrderInfo.tblOrderInfoTitle.length
		tblHisOrderInfoData =new Object[orderinfo.size()][BeanOrderInfo.tblOrderInfoTitle.length];
		for(int i=0;i<orderinfo.size();i++){
			for(int j=0;j<BeanOrderInfo.tblOrderInfoTitle.length;j++)
				//遍历输出每项
				tblHisOrderInfoData[i][j]=orderinfo.get(i).getCell(j);
		}
		//将实例中的值替换为数组中的值，行索引为tblOrderInfoData，列索引为tblOrderInfoTitle
		tabHisOrderInfoModel.setDataVector(tblHisOrderInfoData,tblHisOrderInfoTitle);
		this.dataTableHisOrderInfo.validate();
		this.dataTableHisOrderInfo.repaint();
	}
	public FrmHisOrder(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("历史订单");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		titleBar.add(title);
		this.getContentPane().add(titleBar, BorderLayout.NORTH);
		//加入一个显示dataTableHisOrder的滚动条到页面的左边
	    this.getContentPane().add(new JScrollPane(this.dataTableHisOrder), BorderLayout.WEST);
	    this.dataTableHisOrder.addMouseListener(new MouseAdapter (){
			@Override
			//在组件上单击鼠标按钮时调用函数
			public void mouseClicked(MouseEvent e) {
				//返回所选第一行的索引
				int i=FrmHisOrder.this.dataTableHisOrder.getSelectedRow();
				//若没有选择行则返回-1
				if(i<0) {
					return;
				}
				//加载对应商品列表
				FrmHisOrder.this.reloadHisOrderInfoTabel(i);
			}
		});
	    this.getContentPane().add(new JScrollPane(this.dataTableHisOrderInfo), BorderLayout.EAST);
	    this.reloadHisOrder();
		this.setSize(1000, 300);
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
