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
import takeaway.model.BeanProduct;
import takeaway.model.BeanSppj;
import takeaway.model.BeanSppj;
import takeaway.util.BaseException;

public class FrmSelectPro extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnok = new Button("确定");
	private JPanel titleBar = new JPanel();
	private JLabel title = new JLabel("查询商品：");
	private JTextField name = new JTextField(20);
	private Button select = new Button("查询");
	private JLabel title1 = new JLabel("                                                                                           商品评价：                                            ");

	private Object tblselproTitle[]=BeanProduct.tblProductTitle1;
	private Object tblselproData[][];
	DefaultTableModel tabselproModel=new DefaultTableModel();
	private JTable dataTableselpro=new JTable(tabselproModel);
	
	private Object tblpropjTitle[]=BeanSppj.tableTitles2;
	private Object tblpropjData[][];
	DefaultTableModel tabpropjModel=new DefaultTableModel();
	private JTable dataTablepropj=new JTable(tabpropjModel);
	
	private BeanProduct curselpro=null;
	List<BeanProduct> selpro=null;
	List<BeanSppj> propj=null;
	private void reloadselpro(String name){
		try {
			selpro=takeawayUtil.productManager.selectproduct(name);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblselproData = new Object[selpro.size()][BeanProduct.tblProductTitle1.length];
		for(int i=0;i<selpro.size();i++){
			for(int j=0;j<BeanProduct.tblProductTitle1.length;j++)
				tblselproData[i][j]=selpro.get(i).getCell1(j);
		}
		tabselproModel.setDataVector(tblselproData,tblselproTitle);
		this.dataTableselpro.validate();
		this.dataTableselpro.repaint();
	}
	private void reloadpropjTabel(int selproIdx){
		if(selproIdx<0) return;
		curselpro=selpro.get(selproIdx);
		try {
			propj=takeawayUtil.sppjManager.loadpropj(curselpro);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		//定义一个二维对象，行大小为propj.size()，列大小为BeanSppj.tableTitles2.length
		tblpropjData =new Object[propj.size()][BeanSppj.tableTitles2.length];
		for(int i=0;i<propj.size();i++){
			for(int j=0;j<BeanSppj.tableTitles2.length;j++)
				//遍历输出每项
				tblpropjData[i][j]=propj.get(i).getCell2(j);
		}
		//将实例中的值替换为数组中的值，行索引为tblpropjData，列索引为tableTitles2
		tabpropjModel.setDataVector(tblpropjData,tblpropjTitle);
		this.dataTablepropj.validate();
		this.dataTablepropj.repaint();
	}
	public FrmSelectPro(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("查询商品");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		titleBar.add(title);
		titleBar.add(name);
		titleBar.add(select);
		titleBar.add(title1);
		this.getContentPane().add(titleBar, BorderLayout.NORTH);
		//加入一个显示dataTableselpro的滚动条到页面的左边
	    this.getContentPane().add(new JScrollPane(this.dataTableselpro), BorderLayout.WEST);
	    this.dataTableselpro.addMouseListener(new MouseAdapter (){
			@Override
			//在组件上单击鼠标按钮时调用函数
			public void mouseClicked(MouseEvent e) {
				//返回所选第一行的索引
				int i=FrmSelectPro.this.dataTableselpro.getSelectedRow();
				//若没有选择行则返回-1
				if(i<0) {
					return;
				}
				//加载对应商品列表
				FrmSelectPro.this.reloadpropjTabel(i);
			}
		});
	    this.getContentPane().add(new JScrollPane(this.dataTablepropj), BorderLayout.EAST);
		this.setSize(1000, 300);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.select.addActionListener(this);
		this.btnok.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnok) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.select) {
			String name=this.name.getText();
			this.reloadselpro(name);
		}
	}
}
