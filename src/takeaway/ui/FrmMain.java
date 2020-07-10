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
import takeaway.model.BeanStore;
import takeaway.model.BeanUser;
import takeaway.model.BeanProduct;
import takeaway.util.BaseException;


/*
 * public class FrmMain：这个是自定义的类，名字叫FrmMain
 * extends JFrame： 继承Java的JFrame类，JFrame是Java的窗体类，继承它可以重写它的一些方法达到更方便编程的作用
 * implements ActionListener：是实现 ActionListener接口，为动作监听接口，是Java swing监听窗体动作的一个接口
 */
public class FrmMain extends JFrame implements ActionListener {
	//定义程序序列化ID
	private static final long serialVersionUID = 1L;
	//创建菜单条
	private JMenuBar menubar=new JMenuBar(); 
	//创建菜单
	private JMenu menu_order=new JMenu("我要下单");
    private JMenu menu_myorder=new JMenu("我的订单");
    private JMenu menu_function=new JMenu("我的功能");
    private JMenu menu_more=new JMenu("更多");
    //创建菜单项
    private JMenuItem  menuItem_history=new JMenuItem("历史订单");//已完成
    private JMenuItem  menuItem_ing=new JMenuItem("正在配送");//已完成
    
    private JMenuItem  menuItem_discount=new JMenuItem("我的优惠券");
    private JMenuItem  menuItem_reception=new JMenuItem("我的评价");
    private JMenuItem  menuItem_info=new JMenuItem("我的信息");//已完成
    private JMenuItem  menuItem_address=new JMenuItem("我的地址");//已完成
    
    private JMenuItem  menuItem_vip=new JMenuItem("我的会员");//已完成
    private JMenuItem  menuItem_store=new JMenuItem("成为商家");//已完成
    private JMenuItem  menuItem_rider=new JMenuItem("成为骑手");//有问题
    private JMenuItem  menuItem_modifyPwd=new JMenuItem("修改密码");//已完成
    
    private FrmLogin dlgLogin=null;
	//创建一个面板
	private JPanel statusBar = new JPanel();
	//商家列表
	//表项标题
	private Object tblStoreTitle[]=BeanStore.tableTitles;
	//二维表存储
	private Object tblStoreData[][];
	//创建表格模型
	DefaultTableModel tabStoreModel=new DefaultTableModel();
	//用tabStoreModel为模型构造表格
	private JTable dataTableStore=new JTable(tabStoreModel);
	
	//商品列表
	private Object tblProductTitle[]=BeanProduct.tblProductTitle;
	private Object tblProductData[][];
	DefaultTableModel tabProductModel=new DefaultTableModel();
	private JTable dataTableProduct=new JTable(tabProductModel);
	
	private BeanStore curStore=null;
	List<BeanStore> allStore=null;
	List<BeanProduct> Product=null;
	private void reloadStoreTable(){//这是测试数据，需要用实际数替换
		try {
			//加载所有的Store列表项
			allStore=takeawayUtil.storeManager.loadAll();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblStoreData = new Object[allStore.size()][BeanStore.tableTitles.length];
		for(int i=0;i<allStore.size();i++){
			for(int j=0;j<BeanStore.tableTitles.length;j++)
				tblStoreData[i][j]=allStore.get(i).getCell(j);
		}
		tabStoreModel.setDataVector(tblStoreData,tblStoreTitle);
		this.dataTableStore.validate();
		//验证容器及其子组件
		this.dataTableStore.repaint();
		//重绘该组件
	}
	private void reloadProductTabel(int StoreIdx){
		if(StoreIdx<0) return;
		//返回Store列表中该索引位置的Store
		curStore=allStore.get(StoreIdx);
		try {
			//加载该商家的商品列表
			Product=takeawayUtil.productManager.loadProducts(curStore);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		//定义一个二维对象，行大小为Products.size()，列大小为BeanProduct.tblProductTitle.length
		tblProductData =new Object[Product.size()][BeanProduct.tblProductTitle.length];
		for(int i=0;i<Product.size();i++){
			for(int j=0;j<BeanProduct.tblProductTitle.length;j++)
				//遍历输出每项
				tblProductData[i][j]=Product.get(i).getCell(j);
		}
		//将实例中的值替换为数组中的值，行索引为tblProductData，列索引为tblProductTitle
		tabProductModel.setDataVector(tblProductData,tblProductTitle);
		this.dataTableProduct.validate();
		this.dataTableProduct.repaint();
	}
	public FrmMain(){
		//设置窗口最大化
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		//设置窗口标题
		this.setTitle("外卖助手");
		dlgLogin=new FrmLogin(this,"登陆",true);
		dlgLogin.setVisible(true);
		//将菜单项添加到菜单
	    this.menu_myorder.add(this.menuItem_history); this.menuItem_history.addActionListener(this);
	    this.menu_myorder.add(this.menuItem_ing); this.menuItem_ing.addActionListener(this);
	    
	    this.menu_function.add(this.menuItem_discount); this.menuItem_discount.addActionListener(this);
	    this.menu_function.add(this.menuItem_reception); this.menuItem_reception.addActionListener(this);
	    this.menu_function.add(this.menuItem_info); this.menuItem_info.addActionListener(this);
	    this.menu_function.add(this.menuItem_address); this.menuItem_address.addActionListener(this);
	    
	    this.menu_more.add(this.menuItem_vip); this.menuItem_vip.addActionListener(this);
	    this.menu_more.add(this.menuItem_store); this.menuItem_store.addActionListener(this);
	    this.menu_more.add(this.menuItem_rider); this.menuItem_rider.addActionListener(this);
	    this.menu_more.add(this.menuItem_modifyPwd); this.menuItem_modifyPwd.addActionListener(this);
	    //将菜单添加到菜单栏
	    menubar.add(menu_order);
	    menubar.add(menu_myorder);
	    menubar.add(menu_function);
	    menubar.add(menu_more);
	    //将创建的菜单栏加入主窗口
	    this.setJMenuBar(menubar);
	    //加入一个显示dataTableStore的滚动条到页面的左边
	    this.getContentPane().add(new JScrollPane(this.dataTableStore), BorderLayout.WEST);
	    //添加鼠标监听器组件
	    this.dataTableStore.addMouseListener(new MouseAdapter (){
			@Override
	    	//在组件上单击鼠标按钮时调用函数
			public void mouseClicked(MouseEvent e) {
				//返回所选第一行的索引
				int i=FrmMain.this.dataTableStore.getSelectedRow();
				//若没有选择行则返回-1
				if(i<0) {
					return;
				}
				//加载对应商品列表
				FrmMain.this.reloadProductTabel(i);
			}
	    	
	    });
	    //加入一个显示dataTableProduct的滚动条到页面的中间
	    this.getContentPane().add(new JScrollPane(this.dataTableProduct), BorderLayout.CENTER);
	    
	    this.reloadStoreTable();
	    //创建一个状态栏在页面左端
	    statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
	    JLabel label=new JLabel("您好!");//修改成   您好！+登陆用户名
	    statusBar.add(label);
	    this.getContentPane().add(statusBar,BorderLayout.SOUTH);
	    this.addWindowListener(new WindowAdapter(){   
	    	public void windowClosing(WindowEvent e){ 
	    		System.exit(0);
             }
        });
	    this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.menuItem_info){
			FrmUserInfo ui=new FrmUserInfo(this,"我的信息",true);
			ui.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_address){
			FrmMyAddress ma=new FrmMyAddress(this,"我的地址",true);
			ma.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_history){
			FrmHisOrder ho=new FrmHisOrder(this,"历史订单",true);
			ho.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_ing){
			FrmIngOrder io=new FrmIngOrder(this,"正在配送",true);
			io.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_discount){
			FrmMyyh my=new FrmMyyh(this,"我的优惠",true);
			my.setVisible(true);
		}
		/*else if(e.getSource()==this.menuItem_DeleteStore){
			if(this.curStore==null) {
				JOptionPane.showMessageDialog(null, "请选择计划", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.StoreManager.deleteStore(this.curStore);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}*/
		else if(e.getSource()==this.menuItem_store){
			FrmStore st=new FrmStore(this,"成为商家",true);
			st.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_rider){
			FrmRider ri=new FrmRider(this,"成为骑手",true);
			ri.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_vip){
			FrmMyVIP mv=new FrmMyVIP(this,"我的会员",true);
			mv.setVisible(true);
		}/*
		else if(e.getSource()==this.menuItem_DeleteProduct){
			int i=FrmMain.this.dataTableProduct.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "请选择步骤", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.ProductManager.deleteProduct(this.Product.get(i));
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.menuItem_startProduct){
			int i=FrmMain.this.dataTableProduct.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "请选择步骤", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.ProductManager.startProduct(this.Product.get(i));
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.menuItem_finishProduct){
			int i=FrmMain.this.dataTableProduct.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "请选择步骤", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.ProductManager.finishProduct(this.Product.get(i));
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.menuItem_moveUpProduct){
			int i=FrmMain.this.dataTableProduct.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "请选择步骤", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.ProductManager.moveUp(this.Product.get(i));
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.menuItem_moveDownProduct){
			int i=FrmMain.this.dataTableProduct.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "请选择步骤", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.ProductManager.moveDown(this.Product.get(i));
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.menuItem_static1){
			
		}*/
		else if(e.getSource()==this.menuItem_modifyPwd){
			FrmModifyPwd mp=new FrmModifyPwd(this,"修改密码",true);
			mp.setVisible(true);
		}
	}
}
