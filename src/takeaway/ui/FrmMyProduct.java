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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import takeaway.takeawayUtil;
import takeaway.model.BeanAddress;
import takeaway.model.BeanProduct;
import takeaway.util.BaseException;

public class FrmMyProduct extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button addpro = new Button("增加商品");
	private Button updpro = new Button("修改商品");
	private Button delpro = new Button("删除商品");
	private Button btnok = new Button("确定");
	
	private JPanel workPane = new JPanel();
	private JLabel newname = new JLabel("商品：");
	private JLabel newkind = new JLabel("分类：");
	private JLabel startmoney = new JLabel("原价：");
	private JLabel endmoney = new JLabel("优惠价：");
	
	private JTextField name = new JTextField(10);
	private JTextField kind = new JTextField(10);
	private JTextField start= new JTextField(10);
	private JTextField end = new JTextField(10);
	
	//地址列表
	//表项标题
	private Object tblProTitle[]=BeanProduct.tblProductTitle;
	//二维表存储
	private Object tblProData[][];
	//创建表格模型
	DefaultTableModel tabProModel=new DefaultTableModel();
	//用tabProModel为模型构造表格
	private JTable dataTablePro=new JTable(tabProModel);
	
	BeanProduct curPro;
	List<BeanProduct> Pro=null;
	private void reloadProTable(){//这是测试数据，需要用实际数替换
		try {
			//查询当前Pro
			Pro=takeawayUtil.productManager.loadAll();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblProData = new Object[Pro.size()][BeanProduct.tblProductTitle.length];
		for(int i=0;i<Pro.size();i++){
			for(int j=0;j<BeanProduct.tblProductTitle.length;j++)
				tblProData[i][j]=Pro.get(i).getCell(j);
		}
		tabProModel.setDataVector(tblProData,tblProTitle);
		this.dataTablePro.validate();
		//验证容器及其子组件
		this.dataTablePro.repaint();
		//重绘该组件
	}
	public FrmMyProduct(JFrame f, String s, boolean b) {
		super(f, s, b);
		//设置窗口标题
		this.setTitle("我的商品");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(addpro);
		toolBar.add(updpro);
		toolBar.add(delpro);
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		workPane.add(newname);
		workPane.add(name);
		workPane.add(newkind);
		workPane.add(kind);
		workPane.add(startmoney);
		workPane.add(start);
		workPane.add(endmoney);
		workPane.add(end);
		//新信息居中
		this.getContentPane().add(workPane, BorderLayout.NORTH);
		
		//加入一个显示dataTableStore的滚动条到页面的左边
	    this.getContentPane().add(new JScrollPane(this.dataTablePro), BorderLayout.CENTER);
	  //添加鼠标监听器组件
	    this.dataTablePro.addMouseListener(new MouseAdapter (){
			@Override
	    	//在组件上单击鼠标按钮时调用函数
			public void mouseClicked(MouseEvent e) {
				//返回所选第一行的索引
				int i=FrmMyProduct.this.dataTablePro.getSelectedRow();
				//若没有选择行则返回-1
				if(i<0) {
					return;
				}
				curPro=Pro.get(i);
			}
	    });
	    this.reloadProTable();
		this.setSize(650, 250);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.addpro.addActionListener(this);
		this.updpro.addActionListener(this);
		this.delpro.addActionListener(this);
		this.btnok.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnok) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.addpro) {
			String name=this.name.getText();
			String kind=this.kind.getText();
			Float start=Float.parseFloat(this.start.getText());
			Float end=Float.parseFloat(this.end.getText());
			try {
				takeawayUtil.productManager.addproduct(name,kind,start,end);
				this.reloadProTable();
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		}
		else if(e.getSource()==this.updpro) {
			String name=this.name.getText();
			String kind=this.kind.getText();
			Float start=Float.parseFloat(this.start.getText());
			Float end=Float.parseFloat(this.end.getText());
			try {
				takeawayUtil.productManager.updateProduct(this.curPro,name,kind,start,end);
				this.reloadProTable();
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		}
		else if(e.getSource()==this.delpro) {
			if(this.curPro==null) {
				JOptionPane.showMessageDialog(null, "请选择商品", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.productManager.deleteProduct(this.curPro);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.reloadProTable();
		}
	}
}
