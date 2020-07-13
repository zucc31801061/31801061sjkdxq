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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import takeaway.takeawayUtil;
import takeaway.model.BeanMjMethod;
import takeaway.model.BeanProduct;
import takeaway.model.BeanYhInfo;
import takeaway.util.BaseException;

public class FrmChosemjyh extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button addmj = new Button("添加满减");
	private Button addyh = new Button("添加优惠券");
	private Button next = new Button("下一步");
	
	private JPanel workPane = new JPanel();
	private JLabel tips = new JLabel("已为您筛选最佳满减方案：");
	
	private Object tblbestmjTitle[]=BeanMjMethod.tblMjTitle;
	private Object tblbestmjData[][];
	DefaultTableModel tabbestmjModel=new DefaultTableModel();
	private JTable dataTablebestmj=new JTable(tabbestmjModel);
	
	private BeanMjMethod curbestmj=null;
	List<BeanMjMethod> bestmj=null;
	private void reloadbestmj(){
		try {
			bestmj=takeawayUtil.mjManager.selectbestmj();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblbestmjData = new Object[bestmj.size()][BeanMjMethod.tblMjTitle.length];
		for(int i=0;i<bestmj.size();i++){
			for(int j=0;j<BeanMjMethod.tblMjTitle.length;j++)
				tblbestmjData[i][j]=bestmj.get(i).getCell(j);
		}
		tabbestmjModel.setDataVector(tblbestmjData,tblbestmjTitle);
		this.dataTablebestmj.validate();
		this.dataTablebestmj.repaint();
	}
	
	private Object tblnotuseyhTitle[]=BeanYhInfo.tableTitles1;
	private Object tblnotuseyhData[][];
	DefaultTableModel tabnotuseyhModel=new DefaultTableModel();
	private JTable dataTablenotuseyh=new JTable(tabnotuseyhModel);
	
	private BeanYhInfo curnotuseyh=null;
	List<BeanYhInfo> notuseyh=null;
	private void reloadnotuseyh(){
		try {
			notuseyh=takeawayUtil.yhManager.loadnotusebystore();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblnotuseyhData = new Object[notuseyh.size()][BeanYhInfo.tableTitles1.length];
		for(int i=0;i<notuseyh.size();i++){
			for(int j=0;j<BeanYhInfo.tableTitles1.length;j++)
				tblnotuseyhData[i][j]=notuseyh.get(i).getCell1(j);
		}
		tabnotuseyhModel.setDataVector(tblnotuseyhData,tblnotuseyhTitle);
		this.dataTablenotuseyh.validate();
		this.dataTablenotuseyh.repaint();
	}
	public FrmChosemjyh(JDialog f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("选择优惠方式");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(addmj);
		toolBar.add(addyh);
		toolBar.add(next);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		workPane.add(tips);
		this.getContentPane().add(workPane, BorderLayout.NORTH);
		this.getContentPane().add(new JScrollPane(this.dataTablebestmj), BorderLayout.WEST);
		this.dataTablebestmj.addMouseListener(new MouseAdapter (){
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=FrmChosemjyh.this.dataTablebestmj.getSelectedRow();
				if(i<0) {
					return;
				}
				FrmChosemjyh.this.curbestmj=bestmj.get(i);
			}
	    });
		this.reloadbestmj();
		this.getContentPane().add(new JScrollPane(this.dataTablenotuseyh), BorderLayout.CENTER);
		this.dataTablenotuseyh.addMouseListener(new MouseAdapter (){
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=FrmChosemjyh.this.dataTablenotuseyh.getSelectedRow();
				if(i<0) {
					return;
				}
				FrmChosemjyh.this.curnotuseyh=notuseyh.get(i);
			}
	    });
		this.reloadnotuseyh();
		this.setSize(950, 300);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);
		this.validate();
		this.addmj.addActionListener(this);
		this.addyh.addActionListener(this);
		this.next.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.addmj) {
			if(this.curbestmj==null) {
				JOptionPane.showMessageDialog(null, "请选择满减方案", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.orderManager.addmj(curbestmj);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		if(e.getSource()==this.addyh) {
			if(this.curnotuseyh==null) {
				JOptionPane.showMessageDialog(null, "请选择优惠券", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.orderManager.addyh(curnotuseyh);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		if(e.getSource()==this.next) {
			Frmchoseaddress ca=new Frmchoseaddress(this, "下一步", true);
			ca.setVisible(true);
		}
	}
}
