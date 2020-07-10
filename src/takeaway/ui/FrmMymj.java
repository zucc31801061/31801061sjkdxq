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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import takeaway.takeawayUtil;
import takeaway.model.BeanMjMethod;
import takeaway.util.BaseException;

public class FrmMymj extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button addmethod = new Button("增加方案");
	private Button delmethod = new Button("删除方案");
	private Button btnok = new Button("确定");
	//地址列表
	//表项标题
	private Object tblMethodTitle[]=BeanMjMethod.tblMjTitle;
	//二维表存储
	private Object tblMethodData[][];
	//创建表格模型
	DefaultTableModel tabMethodModel=new DefaultTableModel();
	//用tabMethodModel为模型构造表格
	private JTable dataTableMethod=new JTable(tabMethodModel);
	
	BeanMjMethod curMethod;
	List<BeanMjMethod> Method=null;
	private void reloadMethodTable(){//这是测试数据，需要用实际数替换
		try {
			//查询当前Method
			Method=takeawayUtil.mjManager.loadAll();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblMethodData = new Object[Method.size()][BeanMjMethod.tblMjTitle.length];
		for(int i=0;i<Method.size();i++){
			for(int j=0;j<BeanMjMethod.tblMjTitle.length;j++)
				tblMethodData[i][j]=Method.get(i).getCell(j);
		}
		tabMethodModel.setDataVector(tblMethodData,tblMethodTitle);
		this.dataTableMethod.validate();
		//验证容器及其子组件
		this.dataTableMethod.repaint();
		//重绘该组件
	}
	public FrmMymj(JFrame f, String s, boolean b) {
		super(f, s, b);
		//设置窗口标题
		this.setTitle("我的商品");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(addmethod);
		toolBar.add(delmethod);
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		//加入一个显示dataTableStore的滚动条到页面的左边
	    this.getContentPane().add(new JScrollPane(this.dataTableMethod), BorderLayout.WEST);
	  //添加鼠标监听器组件
	    this.dataTableMethod.addMouseListener(new MouseAdapter (){
			@Override
	    	//在组件上单击鼠标按钮时调用函数
			public void mouseClicked(MouseEvent e) {
				//返回所选第一行的索引
				int i=FrmMymj.this.dataTableMethod.getSelectedRow();
				//若没有选择行则返回-1
				if(i<0) {
					return;
				}
				curMethod=Method.get(i);
			}
	    });
	    this.reloadMethodTable();
		this.setSize(460, 250);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.addmethod.addActionListener(this);
		this.delmethod.addActionListener(this);
		this.btnok.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnok) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.addmethod) {
			FrmAddMj am=new FrmAddMj(this, "增加方案", true);
			am.setVisible(true);
		}
		else if(e.getSource()==this.delmethod) {
			if(this.curMethod==null) {
				JOptionPane.showMessageDialog(null, "请选择方案", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.mjManager.Delmj(this.curMethod);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		}
	}
}
