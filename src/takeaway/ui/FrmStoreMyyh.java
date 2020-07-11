package takeaway.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import takeaway.model.BeanYhInfo;
import takeaway.util.BaseException;

public class FrmStoreMyyh extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button addyh = new Button("增加优惠券");
	private Button updyh = new Button("修改优惠券");
	private Button delyh = new Button("删除优惠券");
	private Button btnok = new Button("确定");
	
	private JPanel workPane = new JPanel();
	private JLabel newmoney = new JLabel("优惠金额：");
	private JLabel newnum = new JLabel("集单要求：");
	private JLabel startdate = new JLabel("起始日期（yyyy-MM-dd）：");
	private JLabel enddate = new JLabel("结束日期（yyyy-MM-dd）：");
	
	private JTextField money = new JTextField(10);
	private JTextField num = new JTextField(10);
	private JTextField start= new JTextField(10);
	private JTextField end= new JTextField(10);
	
	private Object tblyhTitle[]=BeanYhInfo.tableTitles3;
	private Object tblyhData[][];
	DefaultTableModel tabyhModel=new DefaultTableModel();
	private JTable dataTableyh=new JTable(tabyhModel);
	
	BeanYhInfo curyh;
	List<BeanYhInfo> yh=null;
	private void reloadyhTable(){
		try {
			yh=takeawayUtil.yhManager.loadbystore();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblyhData = new Object[yh.size()][BeanYhInfo.tableTitles3.length];
		for(int i=0;i<yh.size();i++){
			for(int j=0;j<BeanYhInfo.tableTitles3.length;j++)
				tblyhData[i][j]=yh.get(i).getCell3(j);
		}
		tabyhModel.setDataVector(tblyhData,tblyhTitle);
		this.dataTableyh.validate();
		this.dataTableyh.repaint();
	}
	public FrmStoreMyyh(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("我的优惠券");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(addyh);
		toolBar.add(updyh);
		toolBar.add(delyh);
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(newmoney);
		workPane.add(money);
		workPane.add(newnum);
		workPane.add(num);
		workPane.add(startdate);
		workPane.add(start);
		workPane.add(enddate);
		workPane.add(end);
		this.getContentPane().add(workPane, BorderLayout.NORTH);
	    this.getContentPane().add(new JScrollPane(this.dataTableyh), BorderLayout.CENTER);
	    this.dataTableyh.addMouseListener(new MouseAdapter (){
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=FrmStoreMyyh.this.dataTableyh.getSelectedRow();
				if(i<0) {
					return;
				}
				curyh=yh.get(i);
			}
	    });
	    this.reloadyhTable();
		this.setSize(1000, 250);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.addyh.addActionListener(this);
		this.updyh.addActionListener(this);
		this.delyh.addActionListener(this);
		this.btnok.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnok) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.addyh) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			int money=Integer.valueOf(this.money.getText());
			int num=Integer.valueOf(this.num.getText());
			Date start=new Date();
			try {
				start = simpleDateFormat.parse(this.start.getText());
			} catch (ParseException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			Date end=new Date();
			try {
				end = simpleDateFormat.parse(this.end.getText());
			} catch (ParseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				takeawayUtil.yhManager.Addyh(money, num, start, end);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		}
		else if(e.getSource()==this.updyh) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			int money=Integer.valueOf(this.money.getText());
			int num=Integer.valueOf(this.num.getText());
			Date start=new Date();
			try {
				start = simpleDateFormat.parse(this.start.getText());
			} catch (ParseException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			Date end=new Date();
			try {
				end = simpleDateFormat.parse(this.end.getText());
			} catch (ParseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				takeawayUtil.yhManager.Updyh(this.curyh,money, num, start, end);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		}
		else if(e.getSource()==this.delyh) {
			if(this.curyh==null) {
				JOptionPane.showMessageDialog(null, "请选择优惠券", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.yhManager.Delyh(this.curyh);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		}
	}
}
