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
import takeaway.model.BeanAddress;
import takeaway.model.BeanOrder;
import takeaway.util.BaseException;

public class FrmRiderHisOrder extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnok = new Button("确定");
	
	private Object tblHisOrderTitle[]=BeanOrder.tableTitles2;
	private Object tblHisOrderData[][];
	DefaultTableModel tabHisOrderModel=new DefaultTableModel();
	private JTable dataTableHisOrder=new JTable(tabHisOrderModel);
	
	private Object tblUserAddressTitle[]=BeanAddress.tableTitles;
	private Object tblUserAddressData[][];
	DefaultTableModel tabUserAddressModel=new DefaultTableModel();
	private JTable dataTableUserAddress=new JTable(tabUserAddressModel);
	
	BeanOrder curOrder;
	List<BeanOrder> HisOrder=null;
	List<BeanAddress> UserAddress=null;
	private void reloadHisOrder(){
		try {
			//查询当前HisOrder
			HisOrder=takeawayUtil.orderManager.loadriderHisOrder();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblHisOrderData = new Object[HisOrder.size()][BeanOrder.tableTitles2.length];
		for(int i=0;i<HisOrder.size();i++){
			for(int j=0;j<BeanOrder.tableTitles2.length;j++)
				tblHisOrderData[i][j]=HisOrder.get(i).getCell2(j);
		}
		tabHisOrderModel.setDataVector(tblHisOrderData,tblHisOrderTitle);
		this.dataTableHisOrder.validate();
		//验证容器及其子组件
		this.dataTableHisOrder.repaint();
		//重绘该组件
	}
	private void reloadUserAddressTabel(int orderIdx){
		if(orderIdx<0) return;
		//返回Order列表中该索引位置的Order
		curOrder=HisOrder.get(orderIdx);
		try {
			//加载对应的UserAddress列表
			UserAddress=takeawayUtil.addressManager.loadselect(curOrder);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		//定义一个二维对象，行大小为UserAddress.size()，列大小为BeanAddressAddress.tblUserAddressTitle.length
		tblUserAddressData =new Object[UserAddress.size()][BeanAddress.tableTitles.length];
		for(int i=0;i<UserAddress.size();i++){
			for(int j=0;j<BeanAddress.tableTitles.length;j++)
				//遍历输出每项
				tblUserAddressData[i][j]=UserAddress.get(i).getCell(j);
		}
		//将实例中的值替换为数组中的值，行索引为tblUserAddressData，列索引为tblUserAddressTitle
		tabUserAddressModel.setDataVector(tblUserAddressData,tblUserAddressTitle);
		this.dataTableUserAddress.validate();
		this.dataTableUserAddress.repaint();
	}
	public FrmRiderHisOrder(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("已送达");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		//加入一个显示dataTableHisOrder的滚动条到页面的左边
	    this.getContentPane().add(new JScrollPane(this.dataTableHisOrder), BorderLayout.CENTER);
	    this.dataTableHisOrder.addMouseListener(new MouseAdapter (){
			@Override
			//在组件上单击鼠标按钮时调用函数
			public void mouseClicked(MouseEvent e) {
				//返回所选第一行的索引
				int i=FrmRiderHisOrder.this.dataTableHisOrder.getSelectedRow();
				//若没有选择行则返回-1
				if(i<0) {
					return;
				}
				FrmRiderHisOrder.this.reloadUserAddressTabel(i);
			}
		});
		//加入一个显示dataTableUserAddress的滚动条到页面的中间
		this.getContentPane().add(new JScrollPane(this.dataTableUserAddress), BorderLayout.EAST);
	    this.reloadHisOrder();
		this.setSize(1100, 300);
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
