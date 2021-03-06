package takeaway.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import takeaway.model.BeanYhInfo;
import takeaway.util.BaseException;

public class FrmMyyh extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel menuBar = new JPanel();
	private JPanel usedp = new JPanel();
	private JPanel notusedp = new JPanel();
	private JPanel nothavep = new JPanel();
	private JLabel usedl = new JLabel("已使用：                                                                                 ");
	private JLabel notusedl = new JLabel("                                       未使用：                                                                                 ");
	private JLabel nothavel = new JLabel("                                              正在集单：");
	private Button btnok = new Button("确定");
	//已使用的优惠券
	private Object tblusedyhTitle[]=BeanYhInfo.tableTitles;
	private Object tblusedyhData[][];
	DefaultTableModel tabusedyhModel=new DefaultTableModel();
	private JTable dataTableusedyh=new JTable(tabusedyhModel);
	List<BeanYhInfo> usedyh=null;
	private void reloadusedyh(){
		try {
			usedyh=takeawayUtil.yhManager.loadused();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblusedyhData = new Object[usedyh.size()][BeanYhInfo.tableTitles.length];
		for(int i=0;i<usedyh.size();i++){
			for(int j=0;j<BeanYhInfo.tableTitles.length;j++)
				tblusedyhData[i][j]=usedyh.get(i).getCell(j);
		}
		tabusedyhModel.setDataVector(tblusedyhData,tblusedyhTitle);
		this.dataTableusedyh.validate();
		this.dataTableusedyh.repaint();
	}
	//未使用的优惠券
	private Object tblnotusedTitle[]=BeanYhInfo.tableTitles1;
	private Object tblnotusedData[][];
	DefaultTableModel tabnotusedModel=new DefaultTableModel();
	private JTable dataTablenotused=new JTable(tabnotusedModel);
	List<BeanYhInfo> notused=null;
	private void reloadnotused(){
		try {
			notused=takeawayUtil.yhManager.loadnotused();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblnotusedData = new Object[notused.size()][BeanYhInfo.tableTitles1.length];
		for(int i=0;i<notused.size();i++){
			for(int j=0;j<BeanYhInfo.tableTitles1.length;j++)
				tblnotusedData[i][j]=notused.get(i).getCell1(j);
		}
		tabnotusedModel.setDataVector(tblnotusedData,tblnotusedTitle);
		this.dataTablenotused.validate();
		this.dataTablenotused.repaint();
	}
	//正在集单的优惠券
	private Object tblnothaveTitle[]=BeanYhInfo.tableTitles2;
	private Object tblnothaveData[][];
	DefaultTableModel tabnothaveModel=new DefaultTableModel();
	private JTable dataTablenothave=new JTable(tabnothaveModel);
	List<BeanYhInfo> nothave=null;
	private void reloadnothave(){
		try {
			nothave=takeawayUtil.yhManager.loadnothave();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblnothaveData = new Object[nothave.size()][BeanYhInfo.tableTitles2.length];
		for(int i=0;i<nothave.size();i++){
			for(int j=0;j<BeanYhInfo.tableTitles2.length;j++)
				tblnothaveData[i][j]=nothave.get(i).getCell2(j);
		}
		tabnothaveModel.setDataVector(tblnothaveData,tblnothaveTitle);
		this.dataTablenothave.validate();
		this.dataTablenothave.repaint();
	}
	public FrmMyyh(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("我的优惠券");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		//加入一个显示dataTableusedyh的滚动条到页面的左边
		menuBar.add(usedl);
		menuBar.add(notusedl);
		menuBar.add(nothavel);
		this.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		usedp.add(new JScrollPane(dataTableusedyh));
		this.getContentPane().add(usedp, BorderLayout.WEST);
		this.reloadusedyh();
		
		notusedp.add(new JScrollPane(dataTablenotused));
		this.getContentPane().add(notusedp, BorderLayout.CENTER);
		this.reloadnotused();
		
		nothavep.add(new JScrollPane(dataTablenothave));
		this.getContentPane().add(nothavep, BorderLayout.EAST);
		this.reloadnothave();
		
		this.setSize(1400, 550);
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
