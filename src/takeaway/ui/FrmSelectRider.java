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
import takeaway.model.BeanOrder;
import takeaway.model.BeanRider;
import takeaway.util.BaseException;

public class FrmSelectRider extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnok = new Button("确定");
	private Button del = new Button("注销骑手");
	private Button updateriderid = new Button("修改骑手称号");
	private JPanel titleBar = new JPanel();
	private JLabel title = new JLabel("请输入骑手账号：");
	private JTextField name = new JTextField(20);
	private Button select = new Button("查询");
	private JLabel title1 = new JLabel("                                                                                                               骑手入账：                                            ");

	private Object tblselriderTitle[]=BeanRider.tableTitles1;
	private Object tblselriderData[][];
	DefaultTableModel tabselriderModel=new DefaultTableModel();
	private JTable dataTableselrider=new JTable(tabselriderModel);
	
	private Object tblqsrzTitle[]=BeanOrder.tableTitles9;
	private Object tblqsrzData[][];
	DefaultTableModel tabqsrzModel=new DefaultTableModel();
	private JTable dataTableqsrz=new JTable(tabqsrzModel);
	
	private BeanRider curselrider=null;
	List<BeanRider> selrider=null;
	List<BeanOrder> qsrz=null;
	private void reloadselrider(String name){
		try {
			selrider=takeawayUtil.riderManager.searchrider(name);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblselriderData = new Object[selrider.size()][BeanRider.tableTitles1.length];
		for(int i=0;i<selrider.size();i++){
			for(int j=0;j<BeanRider.tableTitles1.length;j++)
				tblselriderData[i][j]=selrider.get(i).getCell1(j);
		}
		tabselriderModel.setDataVector(tblselriderData,tblselriderTitle);
		this.dataTableselrider.validate();
		this.dataTableselrider.repaint();
	}
	private void reloadqsrzTabel(int selriderIdx){
		if(selriderIdx<0) return;
		curselrider=selrider.get(selriderIdx);
		try {
			qsrz=takeawayUtil.orderManager.selectridermoney(curselrider);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		//定义一个二维对象，行大小为qsrz.size()，列大小为BeanOrder.tableTitles2.length
		tblqsrzData =new Object[qsrz.size()][BeanOrder.tableTitles9.length];
		for(int i=0;i<qsrz.size();i++){
			for(int j=0;j<BeanOrder.tableTitles9.length;j++)
				//遍历输出每项
				tblqsrzData[i][j]=qsrz.get(i).getCell9(j);
		}
		//将实例中的值替换为数组中的值，行索引为tblqsrzData，列索引为tableTitles2
		tabqsrzModel.setDataVector(tblqsrzData,tblqsrzTitle);
		this.dataTableqsrz.validate();
		this.dataTableqsrz.repaint();
	}
	public FrmSelectRider(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("查询用户：");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(updateriderid);
		toolBar.add(del);
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		titleBar.add(title);
		titleBar.add(name);
		titleBar.add(select);
		titleBar.add(title1);
		this.getContentPane().add(titleBar, BorderLayout.NORTH);
		//加入一个显示dataTableselrider的滚动条到页面的左边
	    this.getContentPane().add(new JScrollPane(this.dataTableselrider), BorderLayout.CENTER);
	    this.dataTableselrider.addMouseListener(new MouseAdapter (){
			@Override
			//在组件上单击鼠标按钮时调用函数
			public void mouseClicked(MouseEvent e) {
				//返回所选第一行的索引
				int i=FrmSelectRider.this.dataTableselrider.getSelectedRow();
				//若没有选择行则返回-1
				if(i<0) {
					return;
				}
				//加载对应商品列表
				FrmSelectRider.this.reloadqsrzTabel(i);
			}
		});
	    this.getContentPane().add(new JScrollPane(this.dataTableqsrz), BorderLayout.EAST);
		this.setSize(1100, 300);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.select.addActionListener(this);
		this.updateriderid.addActionListener(this);
		this.del.addActionListener(this);
		this.btnok.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnok) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.select) {
			String name=this.name.getText();
			this.reloadselrider(name);
		}
		else if(e.getSource()==this.updateriderid) {
			FrmUpdateRiderid ur=new FrmUpdateRiderid(this, "修改骑手称号", true);
			ur.setVisible(true);
		}
		else if(e.getSource()==this.del) {if(this.curselrider==null) {
				JOptionPane.showMessageDialog(null, "请选择骑手", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.riderManager.deleteRider(this.curselrider);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		}
	}
}
