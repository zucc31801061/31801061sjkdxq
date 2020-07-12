package takeaway.ui;

import java.awt.BorderLayout;
import java.awt.Button;
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
import takeaway.model.BeanRider;
import takeaway.model.BeanStore;
import takeaway.model.BeanUser;
import takeaway.util.BaseException;

public class FrmGlyMain extends JFrame implements ActionListener {
	//定义程序序列化ID
		private static final long serialVersionUID = 1L;
		//创建菜单条
		private JMenuBar menubar=new JMenuBar(); 
		//创建菜单
		private JMenu menu_user=new JMenu("用户操作");
		private JMenu menu_store=new JMenu("商家操作");
		private JMenu menu_rider=new JMenu("骑手操作");
		private JMenu menu_more=new JMenu("更多");
		//创建菜单项
		private JMenuItem  menuItem_userxfinfo=new JMenuItem("用户消费情况");//已完成
		private JMenuItem  menuItem_userinfo=new JMenuItem("查询用户");//已完成
		
		private JMenuItem  menuItem_seefl=new JMenuItem("查看分类");//已完成
		private JMenuItem  menuItem_storeinfo=new JMenuItem("查询商家");//已完成
		
		private JMenuItem  menuItem_riderrz=new JMenuItem("骑手入账");//已完成
		private JMenuItem  menuItem_riderinfo=new JMenuItem("查询骑手");//已完成
		
		private JMenuItem  menuItem_updateinfo=new JMenuItem("我的信息");
		private JMenuItem  menuItem_updatemm=new JMenuItem("修改信息");
		
		private JPanel menuBar = new JPanel();
		private JLabel userl = new JLabel("骑手：                                                                                                                        ");
		private JLabel riderl = new JLabel("                                       用户：                                                                                 ");
		private JLabel storel = new JLabel("                                                                                     商家：");
		private JPanel statusBar = new JPanel();
		
		//所有用户
		private Object tbluserTitle[]=BeanUser.tableTitles2;
		private Object tbluserData[][];
		DefaultTableModel tabuserModel=new DefaultTableModel();
		private JTable dataTableuser=new JTable(tabuserModel);
		List<BeanUser> user=null;
		private void reloaduser(){
			try {
				user=takeawayUtil.userManager.loadAll();
			} catch (BaseException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			tbluserData = new Object[user.size()][BeanUser.tableTitles2.length];
			for(int i=0;i<user.size();i++){
				for(int j=0;j<BeanUser.tableTitles2.length;j++)
					tbluserData[i][j]=user.get(i).getCell2(j);
			}
			tabuserModel.setDataVector(tbluserData,tbluserTitle);
			this.dataTableuser.validate();
			this.dataTableuser.repaint();
		}
		//所有商家
		private Object tblstoreTitle[]=BeanStore.tableTitles1;
		private Object tblstoreData[][];
		DefaultTableModel tabstoreModel=new DefaultTableModel();
		private JTable dataTablestore=new JTable(tabstoreModel);
		List<BeanStore> store=null;
		private void reloadstore(){
			try {
				store=takeawayUtil.storeManager.loadAll();
			} catch (BaseException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			tblstoreData = new Object[store.size()][BeanStore.tableTitles1.length];
			for(int i=0;i<store.size();i++){
				for(int j=0;j<BeanStore.tableTitles1.length;j++)
					tblstoreData[i][j]=store.get(i).getCell1(j);
			}
			tabstoreModel.setDataVector(tblstoreData,tblstoreTitle);
			this.dataTablestore.validate();
			this.dataTablestore.repaint();
		}
		//所有骑手
		private Object tblriderTitle[]=BeanRider.tableTitles1;
		private Object tblriderData[][];
		DefaultTableModel tabriderModel=new DefaultTableModel();
		private JTable dataTablerider=new JTable(tabriderModel);
		List<BeanRider> rider=null;
		private void reloadrider(){
			try {
				rider=takeawayUtil.riderManager.loadAll();
			} catch (BaseException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			tblriderData = new Object[rider.size()][BeanRider.tableTitles1.length];
			for(int i=0;i<rider.size();i++){
				for(int j=0;j<BeanRider.tableTitles1.length;j++)
					tblriderData[i][j]=rider.get(i).getCell1(j);
			}
			tabriderModel.setDataVector(tblriderData,tblriderTitle);
			this.dataTablerider.validate();
			this.dataTablerider.repaint();
		}
		
		public FrmGlyMain() {
			//设置窗口最大化
			this.setExtendedState(Frame.MAXIMIZED_BOTH);
			//设置窗口标题
			this.setTitle("我是管理员");
			//将菜单项添加到菜单
			this.menu_user.add(this.menuItem_userxfinfo); this.menuItem_userxfinfo.addActionListener(this);
			this.menu_user.add(this.menuItem_userinfo); this.menuItem_userinfo.addActionListener(this);
			
			this.menu_store.add(this.menuItem_seefl); this.menuItem_seefl.addActionListener(this);
			this.menu_store.add(this.menuItem_storeinfo); this.menuItem_storeinfo.addActionListener(this);
			
			this.menu_rider.add(this.menuItem_riderrz); this.menuItem_riderrz.addActionListener(this);
			this.menu_rider.add(this.menuItem_riderinfo); this.menuItem_riderinfo.addActionListener(this);
			
			this.menu_more.add(this.menuItem_updateinfo); this.menuItem_updateinfo.addActionListener(this);
			this.menu_more.add(this.menuItem_updatemm); this.menuItem_updatemm.addActionListener(this);
			//将菜单添加到菜单栏
			menubar.add(menu_user);
			menubar.add(menu_store);
			menubar.add(menu_rider);
			menubar.add(menu_more);
			//将创建的菜单栏加入主窗口
			this.setJMenuBar(menubar);
			
			menuBar.add(userl);
			menuBar.add(riderl);
			menuBar.add(storel);
			this.getContentPane().add(menuBar, BorderLayout.NORTH);
			
			//加入一个显示dataTableOrder的滚动条到页面的左边
			this.getContentPane().add(new JScrollPane(this.dataTableuser), BorderLayout.CENTER);
			this.getContentPane().add(new JScrollPane(this.dataTablerider), BorderLayout.WEST);
			this.getContentPane().add(new JScrollPane(this.dataTablestore), BorderLayout.EAST);
			
			this.reloaduser();
			this.reloadrider();
			this.reloadstore();
			/*this.reloadOrderTable();*/
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
			if(e.getSource()==this.menuItem_userxfinfo) {
				FrmUserXfInfo uxi=new FrmUserXfInfo(this,"用户消费情况",true);
				uxi.setVisible(true);
			}
			else if(e.getSource()==this.menuItem_userinfo) {
				FrmSelectUser su=new FrmSelectUser(this,"查询用户",true);
				su.setVisible(true);
			}
			else if(e.getSource()==this.menuItem_seefl) {
				FrmSearchFl sf=new FrmSearchFl(this,"查看分类",true);
				sf.setVisible(true);
			}
			else if(e.getSource()==this.menuItem_riderrz) {
				FrmAllQsrz aq=new FrmAllQsrz(this,"骑手入账",true);
				aq.setVisible(true);
			}
			else if(e.getSource()==this.menuItem_riderinfo) {
				FrmSelectRider sr=new FrmSelectRider(this,"查询骑手",true);
				sr.setVisible(true);
			}
			else if(e.getSource()==this.menuItem_updateinfo) {
				FrmGlyInfo gi=new FrmGlyInfo(this,"我的信息",true);
				gi.setVisible(true);
			}
			else if(e.getSource()==this.menuItem_updatemm) {
				FrmChangeYgmm cy=new FrmChangeYgmm(this,"修改密码",true);
				cy.setVisible(true);
			}
		}
}
