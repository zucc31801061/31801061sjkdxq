package takeaway.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import takeaway.takeawayUtil;
import takeaway.model.BeanOrder;
import takeaway.model.BeanOrderInfo;
import takeaway.model.BeanProduct;
import takeaway.model.BeanStore;
import takeaway.util.BaseException;

public class FrmStoreMain extends JFrame implements ActionListener {
	//定义程序序列化ID
	private static final long serialVersionUID = 1L;
	//创建菜单条
	private JMenuBar menubar=new JMenuBar(); 
	//创建菜单
	private JMenu menu_myproduct=new JMenu("我的商品");
    private JMenu menu_provideyh=new JMenu("发放优惠");
    private JMenu menu_userorder=new JMenu("用户订单");
	private JMenu menu_userpj=new JMenu("用户评价");
	private JMenu menu_updateinfo=new JMenu("修改信息");
    //创建菜单项
	private JMenuItem  menuItem_addproduct=new JMenuItem("增加商品");
	private JMenuItem  menuItem_delproduct=new JMenuItem("删除商品");
	private JMenuItem  menuItem_addkind=new JMenuItem("增加类别");
	private JMenuItem  menuItem_delkind=new JMenuItem("删除类别");
	
	private JMenuItem  menuItem_mymj=new JMenuItem("我的满减");
	private JMenuItem  menuItem_myyh=new JMenuItem("我的优惠券");
	
	private JMenuItem  menuItem_notsend=new JMenuItem("未配送");//已完成
	private JMenuItem  menuItem_ingsend=new JMenuItem("正在配送");
	
	private JMenuItem  menuItem_seepj=new JMenuItem("查看评价");
	
	private JMenuItem  menuItem_updatename=new JMenuItem("修改商家名");
	//创建面板
	private JPanel statusBar = new JPanel();
	//订单列表
	//表项标题
	private Object tblOrderTitle[]=BeanOrder.tableTitles;
	//二维表存储
	private Object tblOrderData[][];
	//创建表格模型
	DefaultTableModel tabOrderModel=new DefaultTableModel();
	//用tabOrderModel为模型构造表格
	private JTable dataTableOrder=new JTable(tabOrderModel);
	
	//订单详情列表
	private Object tblOrderInfoTitle[]=BeanOrderInfo.tblOrderInfoTitle;
	private Object tblOrderInfoData[][];
	DefaultTableModel tabOrderInfoModel=new DefaultTableModel();
	private JTable dataTableOrderInfo=new JTable(tabOrderInfoModel);
	
	private BeanOrder curOrder=null;
	List<BeanOrder> allOrder=null;
	List<BeanOrderInfo> orderinfo=null;
	private void reloadOrderTable(){//这是测试数据，需要用实际数替换
		try {
			//加载所有的Order列表项
			allOrder=takeawayUtil.orderManager.loadBysj();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblOrderData =  new Object[allOrder.size()][BeanOrder.tableTitles.length];
		for(int i=0;i<allOrder.size();i++){
			for(int j=0;j<BeanOrder.tableTitles.length;j++)
				tblOrderData[i][j]=allOrder.get(i).getCell(j);
		}
		tabOrderModel.setDataVector(tblOrderData,tblOrderTitle);
		this.dataTableOrder.validate();
		//验证容器及其子组件
		this.dataTableOrder.repaint();
		//重绘该组件
	}
	private void reloadOrderInfoTabel(int orderIdx){
		if(orderIdx<0) return;
			//返回Order列表中该索引位置的Order
			curOrder=allOrder.get(orderIdx);
			try {
				//加载对应的OrderInfo列表
				orderinfo=takeawayUtil.orderinfoManager.loadOrderInfo(curOrder);
			} catch (BaseException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			//定义一个二维对象，行大小为orderinfo.size()，列大小为BeanOrderInfo.tblOrderInfoTitle.length
			tblOrderInfoData =new Object[orderinfo.size()][BeanOrderInfo.tblOrderInfoTitle.length];
			for(int i=0;i<orderinfo.size();i++){
				for(int j=0;j<BeanOrderInfo.tblOrderInfoTitle.length;j++)
					//遍历输出每项
					tblOrderInfoData[i][j]=orderinfo.get(i).getCell(j);
			}
			//将实例中的值替换为数组中的值，行索引为tblOrderInfoData，列索引为tblOrderInfoTitle
			tabOrderInfoModel.setDataVector(tblOrderInfoData,tblOrderInfoTitle);
			this.dataTableOrderInfo.validate();
			this.dataTableOrderInfo.repaint();
		}
	public FrmStoreMain() {
		//设置窗口最大化
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		//设置窗口标题
		this.setTitle("我是商家");
		//将菜单项添加到菜单
		this.menu_myproduct.add(this.menuItem_addproduct); this.menuItem_addproduct.addActionListener(this);
		this.menu_myproduct.add(this.menuItem_delproduct); this.menuItem_delproduct.addActionListener(this);
		this.menu_myproduct.add(this.menuItem_addkind); this.menuItem_addkind.addActionListener(this);
		this.menu_myproduct.add(this.menuItem_delkind); this.menuItem_delkind.addActionListener(this);
		
		this.menu_provideyh.add(this.menuItem_mymj); this.menuItem_mymj.addActionListener(this);
		this.menu_provideyh.add(this.menuItem_myyh); this.menuItem_myyh.addActionListener(this);
		
		this.menu_userorder.add(this.menuItem_notsend); this.menuItem_notsend.addActionListener(this);
		this.menu_userorder.add(this.menuItem_ingsend); this.menuItem_ingsend.addActionListener(this);
		
		this.menu_userpj.add(this.menuItem_seepj); this.menuItem_seepj.addActionListener(this);
		
		this.menu_updateinfo.add(this.menuItem_updatename); this.menuItem_updatename.addActionListener(this);
		//将菜单添加到菜单栏
		menubar.add(menu_myproduct);
		menubar.add(menu_provideyh);
		menubar.add(menu_userorder);
		menubar.add(menu_userpj);
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
				int i=FrmStoreMain.this.dataTableOrder.getSelectedRow();
				//若没有选择行则返回-1
				if(i<0) {
					return;
				}
				//加载对应商品列表
				FrmStoreMain.this.reloadOrderInfoTabel(i);
			}
			
		});
		//加入一个显示dataTableOrderInfo的滚动条到页面的中间
		this.getContentPane().add(new JScrollPane(this.dataTableOrderInfo), BorderLayout.CENTER);
		
		this.reloadOrderTable();
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
		/*if(e.getSource()==this.btnBack) {
			this.setVisible(false);
		}
		else if(e.getSource()==this.becomesj){
			String name=this.edtsjname.getText();
			try {
				takeawayUtil.planManager.addStore(name);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}*/
	}
}
