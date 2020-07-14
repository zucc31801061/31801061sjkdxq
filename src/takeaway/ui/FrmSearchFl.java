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
import takeaway.model.BeanKind;
import takeaway.model.BeanProduct;
import takeaway.util.BaseException;

public class FrmSearchFl extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button addkind = new Button("增加分类");
	private Button updkind = new Button("修改分类");
	private Button delkind = new Button("删除分类");
	private Button btnok = new Button("确定");
	
	private JPanel workPane = new JPanel();
	private JLabel newkind = new JLabel("分类名：");
	
	private JTextField kindt = new JTextField(12);
	
	private Object tblkindTitle[]=BeanKind.tableTitles;
	private Object tblkindData[][];
	DefaultTableModel tabkindModel=new DefaultTableModel();
	private JTable dataTablekind=new JTable(tabkindModel);
	
	private Object tblproductTitle[]=BeanProduct.tblProductTitle1;
	private Object tblproductData[][];
	DefaultTableModel tabproductModel=new DefaultTableModel();
	private JTable dataTableproduct=new JTable(tabproductModel);
	
	BeanKind curkind;
	List<BeanKind> kind=null;
	List<BeanProduct> product=null;
	private void reloadkindTable(){
		try {
			kind=takeawayUtil.kindManager.loadAll();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblkindData = new Object[kind.size()][BeanKind.tableTitles.length];
		for(int i=0;i<kind.size();i++){
			for(int j=0;j<BeanKind.tableTitles.length;j++)
				tblkindData[i][j]=kind.get(i).getCell(j);
		}
		tabkindModel.setDataVector(tblkindData,tblkindTitle);
		this.dataTablekind.validate();
		this.dataTablekind.repaint();
	}
	private void reloadproductTabel(int kindIdx){
		if(kindIdx<0) return;
		curkind=kind.get(kindIdx);
		try {
			product=takeawayUtil.productManager.loadProductbykind(curkind);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		//定义一个二维对象，行大小为product.size()，列大小为BeanProduct.tableTitles2.length
		tblproductData =new Object[product.size()][BeanProduct.tblProductTitle1.length];
		for(int i=0;i<product.size();i++){
			for(int j=0;j<BeanProduct.tblProductTitle1.length;j++)
				//遍历输出每项
				tblproductData[i][j]=product.get(i).getCell1(j);
		}
		//将实例中的值替换为数组中的值，行索引为tblproductData，列索引为tableTitles2
		tabproductModel.setDataVector(tblproductData,tblproductTitle);
		this.dataTableproduct.validate();
		this.dataTableproduct.repaint();
	}
	public FrmSearchFl(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("查看分类");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(addkind);
		toolBar.add(updkind);
		toolBar.add(delkind);
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(newkind);
		workPane.add(kindt);
		this.getContentPane().add(workPane, BorderLayout.NORTH);
	    this.getContentPane().add(new JScrollPane(this.dataTablekind), BorderLayout.CENTER);
	    this.dataTablekind.addMouseListener(new MouseAdapter (){
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=FrmSearchFl.this.dataTablekind.getSelectedRow();
				if(i<0) {
					return;
				}
				curkind=kind.get(i);
				FrmSearchFl.this.reloadproductTabel(i);
			}
	    });
	    this.reloadkindTable();
	    this.getContentPane().add(new JScrollPane(this.dataTableproduct), BorderLayout.EAST);
	    this.setSize(750, 300);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.addkind.addActionListener(this);
		this.updkind.addActionListener(this);
		this.delkind.addActionListener(this);
		this.btnok.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnok) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.addkind) {
			String name = this.kindt.getText();
			try {
				takeawayUtil.kindManager.Addfl(name);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.reloadkindTable();
		}
		else if(e.getSource()==this.updkind) {
			String name = this.kindt.getText();
			try {
				takeawayUtil.kindManager.Updfl(this.curkind, name);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.reloadkindTable();
		}
		else if(e.getSource()==this.delkind) {
			if(this.curkind==null) {
				JOptionPane.showMessageDialog(null, "请选择分类", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.kindManager.Delfl(this.curkind);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.reloadkindTable();
		}
	}
}
