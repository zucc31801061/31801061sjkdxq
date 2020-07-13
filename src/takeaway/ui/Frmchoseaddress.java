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
import takeaway.model.BeanAddress;
import takeaway.util.BaseException;

public class Frmchoseaddress extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnok = new Button("确定");
	
	private Object tblAddTitle[]=BeanAddress.tableTitles;
	private Object tblAddData[][];
	DefaultTableModel tabAddModel=new DefaultTableModel();
	private JTable dataTableAdd=new JTable(tabAddModel);
	
	BeanAddress curAdd;
	List<BeanAddress> Add=null;
	private void reloadAddTable(){
		try {
			Add=takeawayUtil.addressManager.loadAll();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblAddData = new Object[Add.size()][BeanAddress.tableTitles.length];
		for(int i=0;i<Add.size();i++){
			for(int j=0;j<BeanAddress.tableTitles.length;j++)
				tblAddData[i][j]=Add.get(i).getCell(j);
		}
		tabAddModel.setDataVector(tblAddData,tblAddTitle);
		this.dataTableAdd.validate();
		this.dataTableAdd.repaint();
	}
	public Frmchoseaddress(JDialog f, String s, boolean b) {
		super(f, s, b);
		//设置窗口标题
		this.setTitle("我的地址");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		//加入一个显示dataTableStore的滚动条到页面的左边
	    this.getContentPane().add(new JScrollPane(this.dataTableAdd), BorderLayout.CENTER);
	  //添加鼠标监听器组件
	    this.dataTableAdd.addMouseListener(new MouseAdapter (){
			@Override
	    	//在组件上单击鼠标按钮时调用函数
			public void mouseClicked(MouseEvent e) {
				//返回所选第一行的索引
				int i=Frmchoseaddress.this.dataTableAdd.getSelectedRow();
				//若没有选择行则返回-1
				if(i<0) {
					return;
				}
				Frmchoseaddress.this.curAdd=Add.get(i);
			}
	    });
	    this.reloadAddTable();
		this.setSize(950, 300);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnok.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnok) {
			try {
				takeawayUtil.orderManager.addadd(curAdd);
				takeawayUtil.orderManager.order();
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		}
	}
}
