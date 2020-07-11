package takeaway.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import takeaway.takeawayUtil;
import takeaway.model.BeanAddress;
import takeaway.model.BeanOrder;
import takeaway.util.BaseException;

public class FrmGlyMain extends JFrame implements ActionListener {
	//定义程序序列化ID
		private static final long serialVersionUID = 1L;
		//创建菜单条
		private JMenuBar menubar=new JMenuBar(); 
		//创建菜单
		private JMenu menu_takeorder=new JMenu("我要接单");
		private JMenu menu_seeorder=new JMenu("查看订单");
		private JMenu menu_updateinfo=new JMenu("修改信息");
		//创建菜单项
		private JMenuItem  menuItem_takeorder=new JMenuItem("我要接单");//已完成
		
		private JMenuItem  menuItem_alrsend=new JMenuItem("已送达");//已完成
		private JMenuItem  menuItem_ingsend=new JMenuItem("正在配送");//已完成
		private JMenuItem  menuItem_mytake=new JMenuItem("我的入账");//已完成
		
		private JMenuItem  menuItem_updatename=new JMenuItem("修改信息");
		private JMenuItem  menuItem_myinfo=new JMenuItem("我的信息");
		//创建面板
		private JPanel statusBar = new JPanel();
		//订单列表
		//表项标题
		private Object tblOrderTitle[]=BeanOrder.tableTitles3;
		//二维表存储
		private Object tblOrderData[][];
		//创建表格模型
		DefaultTableModel tabOrderModel=new DefaultTableModel();
		//用tabOrderModel为模型构造表格
		private JTable dataTableOrder=new JTable(tabOrderModel);
		
		//用户地址列表
		private Object tblUserAddressTitle[]=BeanAddress.tableTitles;
		private Object tblUserAddressData[][];
		DefaultTableModel tabUserAddressModel=new DefaultTableModel();
		private JTable dataTableUserAddress=new JTable(tabUserAddressModel);
		
		private BeanOrder curOrder=null;
		List<BeanOrder> allOrder=null;
		List<BeanAddress> UserAddress=null;
		/*private void reloadOrderTable(){//这是测试数据，需要用实际数替换
			try {
				//加载所有的Order列表项
				allOrder=takeawayUtil.orderManager.loadByqs();
			} catch (BaseException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			tblOrderData =  new Object[allOrder.size()][BeanOrder.tableTitles3.length];
			for(int i=0;i<allOrder.size();i++){
				for(int j=0;j<BeanOrder.tableTitles3.length;j++)
					tblOrderData[i][j]=allOrder.get(i).getCell3(j);
			}
			tabOrderModel.setDataVector(tblOrderData,tblOrderTitle);
			this.dataTableOrder.validate();
			//验证容器及其子组件
			this.dataTableOrder.repaint();
			//重绘该组件
		}
		private void reloadUserAddressTabel(int orderIdx){
			if(orderIdx<0) return;
			//返回Order列表中该索引位置的Order
			curOrder=allOrder.get(orderIdx);
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
		}*/
		public FrmGlyMain() {
			//设置窗口最大化
			this.setExtendedState(Frame.MAXIMIZED_BOTH);
			//设置窗口标题
			this.setTitle("我是骑手");
			//将菜单项添加到菜单
			this.menu_takeorder.add(this.menuItem_takeorder); this.menuItem_takeorder.addActionListener(this);
			
			this.menu_seeorder.add(this.menuItem_alrsend); this.menuItem_alrsend.addActionListener(this);
			this.menu_seeorder.add(this.menuItem_ingsend); this.menuItem_ingsend.addActionListener(this);
			this.menu_seeorder.add(this.menuItem_mytake); this.menuItem_mytake.addActionListener(this);
			
			this.menu_updateinfo.add(this.menuItem_updatename); this.menuItem_updatename.addActionListener(this);
			this.menu_updateinfo.add(this.menuItem_myinfo); this.menuItem_myinfo.addActionListener(this);
			//将菜单添加到菜单栏
			menubar.add(menu_takeorder);
			menubar.add(menu_seeorder);
			menubar.add(menu_updateinfo);
			//将创建的菜单栏加入主窗口
			this.setJMenuBar(menubar);
			//加入一个显示dataTableOrder的滚动条到页面的左边
			this.getContentPane().add(new JScrollPane(this.dataTableOrder), BorderLayout.WEST);
			//添加鼠标监听器组件
			this.dataTableOrder.addMouseListener(new MouseAdapter (){
				@Override
				//在组件上单击鼠标按钮时调用函数
				public void mouseClicked(MouseEvent e) {
					//返回所选第一行的索引
					int i=FrmGlyMain.this.dataTableOrder.getSelectedRow();
					//若没有选择行则返回-1
					if(i<0) {
						return;
					}
					//加载对应商品列表
					/*FrmGlyMain.this.reloadUserAddressTabel(i);*/
				}
				
			});
			//加入一个显示dataTableUserAddress的滚动条到页面的中间
			this.getContentPane().add(new JScrollPane(this.dataTableUserAddress), BorderLayout.CENTER);
			
			/*this.reloadOrderTable();*/
			//创建一个状态栏在页面左端
			statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
			JLabel label=new JLabel("您好!");//修改成   您好！+登陆用户名
			statusBar.add(label);
			this.getContentPane().add(statusBar,BorderLayout.SOUTH);
			this.addWindowListener(new WindowAdapter(){   
				public void windowClosing(WindowEvent e){ 
					this.windowClosed(e);
				}
			});
			this.setVisible(true);
		}	
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==this.menuItem_alrsend) {
				FrmRiderHisOrder rho=new FrmRiderHisOrder(this,"已送达",true);
				rho.setVisible(true);
			}
			else if(e.getSource()==this.menuItem_ingsend) {
				FrmRiderIngOrder rio=new FrmRiderIngOrder(this,"正在配送",true);
				rio.setVisible(true);
			}
			else if(e.getSource()==this.menuItem_takeorder) {
				FrmTakeOrder to=new FrmTakeOrder(this,"我要接单",true);
				to.setVisible(true);
			}
			else if(e.getSource()==this.menuItem_mytake) {
				FrmMyTake mt=new FrmMyTake(this,"我的入账",true);
				mt.setVisible(true);
			}
			else if(e.getSource()==this.menuItem_updatename) {
				FrmUpdateRider ur=new FrmUpdateRider(this,"修改信息",true);
				ur.setVisible(true);
			}
			else if(e.getSource()==this.menuItem_myinfo) {
				FrmRiderInfo ri=new FrmRiderInfo(this,"我的信息",true);
				ri.setVisible(true);
			}
		}
}
