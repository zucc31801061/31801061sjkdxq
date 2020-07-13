package takeaway.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import takeaway.model.BeanYhInfo;
import takeaway.util.BaseException;

public class FrmOrder extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel menuBar = new JPanel();
	private JPanel storeinfo = new JPanel();
	private JPanel productinfo = new JPanel();
	private JPanel orderinfo = new JPanel();
	
	private JLabel searchstore = new JLabel("商店名：");
	private JTextField storename = new JTextField(15);
	private Button selectstore = new Button("查询");
	private Button addstore = new Button("确认");
	private JLabel searchpro = new JLabel("                      商品名：");
	private JTextField proname = new JTextField(15);
	private Button selectpro = new Button("查询");
	private JLabel numadd = new JLabel("数量：");
	private JTextField pronum = new JTextField(10);
	private Button addpro = new Button("确认");
	private JLabel numchange = new JLabel("                                            数量：");
	private JTextField changepronum = new JTextField(10);
	private Button changenum = new Button("修改");
	private Button delpro = new Button("移除");
	
	private Button btnok = new Button("下单");
	private Button cancel = new Button("取消");
	
	private Object tblselstoreTitle[]=BeanStore.tableTitles1;
	private Object tblselstoreData[][];
	DefaultTableModel tabselstoreModel=new DefaultTableModel();
	private JTable dataTableselstore=new JTable(tabselstoreModel);
	
	private Object tblproductTitle[]=BeanProduct.tblProductTitle;
	private Object tblproductData[][];
	DefaultTableModel tabproductModel=new DefaultTableModel();
	private JTable dataTableproduct=new JTable(tabproductModel);
	
	private BeanStore curselstore=null;
	private BeanProduct curselpro=null;
	List<BeanStore> selstore=null;
	List<BeanProduct> product=null;
	private void reloadselstore(){
		try {
			selstore=takeawayUtil.storeManager.loadAll();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblselstoreData = new Object[selstore.size()][BeanStore.tableTitles1.length];
		for(int i=0;i<selstore.size();i++){
			for(int j=0;j<BeanStore.tableTitles1.length;j++)
				tblselstoreData[i][j]=selstore.get(i).getCell1(j);
		}
		tabselstoreModel.setDataVector(tblselstoreData,tblselstoreTitle);
		this.dataTableselstore.validate();
		this.dataTableselstore.repaint();
	}
	private void reloadselstore(String name){
		try {
			selstore=takeawayUtil.storeManager.selectstorebyname(name);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblselstoreData = new Object[selstore.size()][BeanStore.tableTitles1.length];
		for(int i=0;i<selstore.size();i++){
			for(int j=0;j<BeanStore.tableTitles1.length;j++)
				tblselstoreData[i][j]=selstore.get(i).getCell1(j);
		}
		tabselstoreModel.setDataVector(tblselstoreData,tblselstoreTitle);
		this.dataTableselstore.validate();
		this.dataTableselstore.repaint();
	}
	private void reloadproductTabel(int selstoreIdx){
		if(selstoreIdx<0) return;
		curselstore=selstore.get(selstoreIdx);
		try {
			product=takeawayUtil.productManager.loadProducts(curselstore);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblproductData =new Object[product.size()][BeanProduct.tblProductTitle.length];
		for(int i=0;i<product.size();i++){
			for(int j=0;j<BeanProduct.tblProductTitle.length;j++)
				tblproductData[i][j]=product.get(i).getCell(j);
		}
		tabproductModel.setDataVector(tblproductData,tblproductTitle);
		this.dataTableproduct.validate();
		this.dataTableproduct.repaint();
	}
	private void reloadproductTabel(BeanStore curselstore,String name){
		try {
			product=takeawayUtil.productManager.loadProductsbyname(curselstore,name);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblproductData =new Object[product.size()][BeanProduct.tblProductTitle.length];
		for(int i=0;i<product.size();i++){
			for(int j=0;j<BeanProduct.tblProductTitle.length;j++)
				tblproductData[i][j]=product.get(i).getCell(j);
		}
		tabproductModel.setDataVector(tblproductData,tblproductTitle);
		this.dataTableproduct.validate();
		this.dataTableproduct.repaint();
	}
	private Object tblddinfoTitle[]=BeanOrderInfo.tblOrderInfoTitle;
	private Object tblddinfoData[][];
	DefaultTableModel tabddinfoModel=new DefaultTableModel();
	private JTable dataTableddinfo=new JTable(tabddinfoModel);
	List<BeanOrderInfo> ddinfo=null;
	private void reloadddinfo(BeanOrder order){
		try {
			ddinfo=takeawayUtil.orderinfoManager.loadOrderInfo(order);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblddinfoData = new Object[ddinfo.size()][BeanOrderInfo.tblOrderInfoTitle.length];
		for(int i=0;i<ddinfo.size();i++){
			for(int j=0;j<BeanOrderInfo.tblOrderInfoTitle.length;j++)
				tblddinfoData[i][j]=ddinfo.get(i).getCell(j);
		}
		tabddinfoModel.setDataVector(tblddinfoData,tblddinfoTitle);
		this.dataTableddinfo.validate();
		this.dataTableddinfo.repaint();
	}
	public FrmOrder(JFrame f, String s, boolean b) {
		super(f, s, b);
		try {
			BeanOrder.currentLoginOrder=takeawayUtil.orderManager.addOrder();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		this.setTitle("我要下单");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnok);
		toolBar.add(cancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		//加入一个显示dataTableusedyh的滚动条到页面的左边
		menuBar.add(searchstore);
		menuBar.add(storename);
		menuBar.add(selectstore);
		menuBar.add(addstore);
		menuBar.add(searchpro);
		menuBar.add(proname);
		menuBar.add(selectpro);
		menuBar.add(numadd);
		menuBar.add(pronum);
		menuBar.add(addpro);
		menuBar.add(numchange);
		menuBar.add(changepronum);
		menuBar.add(changenum);
		menuBar.add(delpro);
		this.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		storeinfo.add(new JScrollPane(dataTableselstore));
		this.getContentPane().add(storeinfo, BorderLayout.WEST);
		this.reloadselstore();
		this.dataTableselstore.addMouseListener(new MouseAdapter (){
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=FrmOrder.this.dataTableselstore.getSelectedRow();
				if(i<0) {
					return;
				}
				FrmOrder.this.reloadproductTabel(i);
			}
		});
		
		productinfo.add(new JScrollPane(dataTableproduct));
		this.getContentPane().add(productinfo, BorderLayout.CENTER);
		this.dataTableproduct.addMouseListener(new MouseAdapter (){
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=FrmOrder.this.dataTableproduct.getSelectedRow();
				if(i<0) {
					return;
				}
				FrmOrder.this.curselpro=product.get(i);
			}
		});
		
		orderinfo.add(new JScrollPane(dataTableddinfo));
		this.getContentPane().add(orderinfo, BorderLayout.EAST);
		this.reloadddinfo(BeanOrder.currentLoginOrder);

		this.setSize(1400, 550);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.addWindowListener(new WindowAdapter(){   
	    	public void windowClosing(WindowEvent e){ 
	    		try {
					takeawayUtil.orderManager.deleteOrder();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
					return;
				}
	    		this.windowClosed(e);
             }
        });
		this.selectstore.addActionListener(this);
		this.addstore.addActionListener(this);
		this.selectpro.addActionListener(this);
		this.addpro.addActionListener(this);
		this.changenum.addActionListener(this);
		this.delpro.addActionListener(this);
		this.btnok.addActionListener(this);
		this.cancel.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnok) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.selectstore) {
			String name=this.storename.getText();
			this.reloadselstore(name);
		}
		else if(e.getSource()==this.selectpro) {
			String name=this.proname.getText();
			this.reloadproductTabel(curselstore, name);
		}
		else if(e.getSource()==this.addstore) {
			if(this.curselstore==null) {
				JOptionPane.showMessageDialog(null, "请选择商家", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.orderManager.addstore(curselstore);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.reloadddinfo(BeanOrder.currentLoginOrder);
		}
		else if(e.getSource()==this.addpro) {
			int num=Integer.parseInt(this.pronum.getText());
			if(this.curselpro==null) {
				JOptionPane.showMessageDialog(null, "请选择商品", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.orderinfoManager.addproduct(curselpro, num);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.reloadddinfo(BeanOrder.currentLoginOrder);
		}
		else if(e.getSource()==this.cancel) {
			try {
				takeawayUtil.orderManager.deleteOrder();
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		}
	}
}
