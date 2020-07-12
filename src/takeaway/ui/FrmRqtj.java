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
import javax.swing.table.DefaultTableModel;

import takeaway.takeawayUtil;
import takeaway.model.BeanOrderInfo;
import takeaway.model.BeanSppj;
import takeaway.util.BaseException;

public class FrmRqtj extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnok = new Button("确定");
	
	private JPanel titleBar = new JPanel();
	private JLabel title = new JLabel("                   人气商品：                                                                                                                                                         商品评价：                             ");
	
	private Object tblrqproTitle[]=BeanOrderInfo.tblOrderInfoTitle1;
	private Object tblrqproData[][];
	DefaultTableModel tabrqproModel=new DefaultTableModel();
	private JTable dataTablerqpro=new JTable(tabrqproModel);
	
	private Object tblpropjTitle[]=BeanSppj.tableTitles2;
	private Object tblpropjData[][];
	DefaultTableModel tabpropjModel=new DefaultTableModel();
	private JTable dataTablepropj=new JTable(tabpropjModel);
	
	private BeanOrderInfo currqpro=null;
	List<BeanOrderInfo> rqpro=null;
	List<BeanSppj> propj=null;
	private void reloadrqpro(){
		try {
			rqpro=takeawayUtil.orderinfoManager.loadretj();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblrqproData = new Object[rqpro.size()][BeanOrderInfo.tblOrderInfoTitle1.length];
		for(int i=0;i<rqpro.size();i++){
			for(int j=0;j<BeanOrderInfo.tblOrderInfoTitle1.length;j++)
				tblrqproData[i][j]=rqpro.get(i).getCell1(j);
		}
		tabrqproModel.setDataVector(tblrqproData,tblrqproTitle);
		this.dataTablerqpro.validate();
		this.dataTablerqpro.repaint();
	}
	private void reloadpropjTabel(int rqproIdx){
		if(rqproIdx<0) return;
		currqpro=rqpro.get(rqproIdx);
		try {
			propj=takeawayUtil.sppjManager.loadpropj(currqpro);
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
	public FrmRqtj(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("查询商品");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		titleBar.add(title);
		this.getContentPane().add(titleBar, BorderLayout.NORTH);
		//加入一个显示dataTablerqpro的滚动条到页面的左边
	    this.getContentPane().add(new JScrollPane(this.dataTablerqpro), BorderLayout.WEST);
	    this.dataTablerqpro.addMouseListener(new MouseAdapter (){
			@Override
			//在组件上单击鼠标按钮时调用函数
			public void mouseClicked(MouseEvent e) {
				//返回所选第一行的索引
				int i=FrmRqtj.this.dataTablerqpro.getSelectedRow();
				//若没有选择行则返回-1
				if(i<0) {
					return;
				}
				//加载对应商品列表
				FrmRqtj.this.reloadpropjTabel(i);
			}
		});
	    this.reloadrqpro();
	    this.getContentPane().add(new JScrollPane(this.dataTablepropj), BorderLayout.CENTER);
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
