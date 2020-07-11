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
import takeaway.model.BeanSppj;
import takeaway.util.BaseException;

public class FrmMyStorepj extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button addpj = new Button("修改评价");
	private Button delpj = new Button("删除评价");
	private Button btnok = new Button("确定");
	
	private JPanel workPane = new JPanel();
	private JLabel Newpjnr = new JLabel("评价内容：");
	private JLabel Newphoto = new JLabel("图片(true/false)：");
	private JLabel Newpjstar = new JLabel("星级：");
	private JTextField pjnr = new JTextField(13);
	private JTextField photo= new JTextField(13);
	private JTextField pjstar = new JTextField(13);
	
	//地址列表
	//表项标题
	private Object tblpjTitle[]=BeanSppj.tableTitles;
	//二维表存储
	private Object tblpjData[][];
	//创建表格模型
	DefaultTableModel tabpjModel=new DefaultTableModel();
	//用tabpjModel为模型构造表格
	private JTable dataTablepj=new JTable(tabpjModel);
	
	BeanSppj curpj;
	List<BeanSppj> pj=null;
	private void reloadpjTable(){//这是测试数据，需要用实际数替换
		try {
			pj=takeawayUtil.sppjManager.loadbyuser();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblpjData = new Object[pj.size()][BeanSppj.tableTitles.length];
		for(int i=0;i<pj.size();i++){
			for(int j=0;j<BeanSppj.tableTitles.length;j++)
				tblpjData[i][j]=pj.get(i).getCell(j);
		}
		tabpjModel.setDataVector(tblpjData,tblpjTitle);
		this.dataTablepj.validate();
		//验证容器及其子组件
		this.dataTablepj.repaint();
		//重绘该组件
	}
	public FrmMyStorepj(JFrame f, String s, boolean b) {
		super(f, s, b);
		//设置窗口标题
		this.setTitle("商家评价");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(addpj);
		toolBar.add(delpj);
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(Newpjnr);
		workPane.add(pjnr);
		workPane.add(Newphoto);
		workPane.add(photo);
		workPane.add(Newpjstar);
		workPane.add(pjstar);
		//新信息居中
		this.getContentPane().add(workPane, BorderLayout.NORTH);
		//加入一个显示dataTableStore的滚动条到页面的左边
	    this.getContentPane().add(new JScrollPane(this.dataTablepj), BorderLayout.CENTER);
	  //添加鼠标监听器组件
	    this.dataTablepj.addMouseListener(new MouseAdapter (){
			@Override
	    	//在组件上单击鼠标按钮时调用函数
			public void mouseClicked(MouseEvent e) {
				//返回所选第一行的索引
				int i=FrmMyStorepj.this.dataTablepj.getSelectedRow();
				//若没有选择行则返回-1
				if(i<0) {
					return;
				}
				curpj=pj.get(i);
			}
	    });
	    this.reloadpjTable();
		this.setSize(700, 250);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.addpj.addActionListener(this);
		this.delpj.addActionListener(this);
		this.btnok.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnok) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.addpj) {
			if(this.curpj==null) {
				JOptionPane.showMessageDialog(null, "请选择订单", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			String pjnr=this.pjnr.getText();
			boolean photo=Boolean.parseBoolean(this.photo.getText());
			int pjstar=Integer.parseInt(this.pjstar.getText());
			try {
				takeawayUtil.sppjManager.addSppj(this.curpj,pjnr, pjstar, photo);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.delpj) {
			if(this.curpj==null) {
				JOptionPane.showMessageDialog(null, "请选择评价", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.sppjManager.deleteSppj(this.curpj);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		}
	}
}
