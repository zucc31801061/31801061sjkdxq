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
import takeaway.model.BeanUser;
import takeaway.model.BeanOrder;
import takeaway.util.BaseException;

public class FrmSelectUser extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnok = new Button("确定");
	private Button del = new Button("注销用户");
	private Button updatevip = new Button("修改会员信息");
	private JPanel titleBar = new JPanel();
	private JLabel title = new JLabel("请输入用户账号：");
	private JTextField name = new JTextField(20);
	private Button select = new Button("查询");
	private JLabel title1 = new JLabel("                                                                                                               消费情况：                                            ");

	private Object tblseluserTitle[]=BeanUser.tableTitles2;
	private Object tblseluserData[][];
	DefaultTableModel tabseluserModel=new DefaultTableModel();
	private JTable dataTableseluser=new JTable(tabseluserModel);
	
	private Object tbluserxfTitle[]=BeanOrder.tableTitles6;
	private Object tbluserxfData[][];
	DefaultTableModel tabuserxfModel=new DefaultTableModel();
	private JTable dataTableuserxf=new JTable(tabuserxfModel);
	
	private BeanUser curseluser=null;
	List<BeanUser> seluser=null;
	List<BeanOrder> userxf=null;
	private void reloadseluser(){
		try {
			seluser=takeawayUtil.userManager.loadAll();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblseluserData = new Object[seluser.size()][BeanUser.tableTitles2.length];
		for(int i=0;i<seluser.size();i++){
			for(int j=0;j<BeanUser.tableTitles2.length;j++)
				tblseluserData[i][j]=seluser.get(i).getCell2(j);
		}
		tabseluserModel.setDataVector(tblseluserData,tblseluserTitle);
		this.dataTableseluser.validate();
		this.dataTableseluser.repaint();
	}
	private void reloadseluser(String name){
		try {
			seluser=takeawayUtil.userManager.searchuser(name);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblseluserData = new Object[seluser.size()][BeanUser.tableTitles2.length];
		for(int i=0;i<seluser.size();i++){
			for(int j=0;j<BeanUser.tableTitles2.length;j++)
				tblseluserData[i][j]=seluser.get(i).getCell2(j);
		}
		tabseluserModel.setDataVector(tblseluserData,tblseluserTitle);
		this.dataTableseluser.validate();
		this.dataTableseluser.repaint();
	}
	private void reloaduserxfTabel(int seluserIdx){
		if(seluserIdx<0) return;
		curseluser=seluser.get(seluserIdx);
		try {
			userxf=takeawayUtil.orderManager.selectxfinfo(curseluser);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		//定义一个二维对象，行大小为userxf.size()，列大小为BeanOrder.tableTitles2.length
		tbluserxfData =new Object[userxf.size()][BeanOrder.tableTitles6.length];
		for(int i=0;i<userxf.size();i++){
			for(int j=0;j<BeanOrder.tableTitles6.length;j++)
				//遍历输出每项
				tbluserxfData[i][j]=userxf.get(i).getCell6(j);
		}
		//将实例中的值替换为数组中的值，行索引为tbluserxfData，列索引为tableTitles2
		tabuserxfModel.setDataVector(tbluserxfData,tbluserxfTitle);
		this.dataTableuserxf.validate();
		this.dataTableuserxf.repaint();
	}
	public FrmSelectUser(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("查询用户：");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(updatevip);
		toolBar.add(del);
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		titleBar.add(title);
		titleBar.add(name);
		titleBar.add(select);
		titleBar.add(title1);
		this.getContentPane().add(titleBar, BorderLayout.NORTH);
	    this.getContentPane().add(new JScrollPane(this.dataTableseluser), BorderLayout.CENTER);
	    this.reloadseluser();
	    this.dataTableseluser.addMouseListener(new MouseAdapter (){
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=FrmSelectUser.this.dataTableseluser.getSelectedRow();
				if(i<0) {
					return;
				}
				FrmSelectUser.this.reloaduserxfTabel(i);
			}
		});
	    this.getContentPane().add(new JScrollPane(this.dataTableuserxf), BorderLayout.EAST);
		this.setSize(1100, 300);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.select.addActionListener(this);
		this.updatevip.addActionListener(this);
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
			this.reloadseluser(name);
		}
		else if(e.getSource()==this.updatevip) {
			FrmUpdateVip uv=new FrmUpdateVip(this, "修改会员信息", true);
			uv.setVisible(true);
		}
		else if(e.getSource()==this.del) {
			if(this.curseluser==null) {
				JOptionPane.showMessageDialog(null, "请选择用户", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.userManager.deluser(this.curseluser);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		}
	}
}
