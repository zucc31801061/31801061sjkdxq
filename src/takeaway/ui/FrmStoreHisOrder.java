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
import takeaway.model.BeanOrder;
import takeaway.model.BeanOrderInfo;
import takeaway.util.BaseException;

public class FrmStoreHisOrder extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnok = new Button("确定");
	private JPanel titleBar = new JPanel();
	private JLabel title = new JLabel("商品订单：                                                                                                                                                           订单详情：");
	
	private Object tblHisOrderTitle[]=BeanOrder.tableTitles;
	private Object tblHisOrderData[][];
	DefaultTableModel tabHisOrderModel=new DefaultTableModel();
	private JTable dataTableHisOrder=new JTable(tabHisOrderModel);
	
	private Object tblHisOrderInfoTitle[]=BeanOrderInfo.tblOrderInfoTitle;
	private Object tblHisOrderInfoData[][];
	DefaultTableModel tabHisOrderInfoModel=new DefaultTableModel();
	private JTable dataTableHisOrderInfo=new JTable(tabHisOrderInfoModel);
	
	private BeanOrder curHisOrder=null;
	List<BeanOrder> HisOrder=null;
	List<BeanOrderInfo> orderinfo=null;
	private void reloadHisOrder(){
		try {
			HisOrder=takeawayUtil.orderManager.loadstoreHisOrder();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblHisOrderData = new Object[HisOrder.size()][BeanOrder.tableTitles.length];
		for(int i=0;i<HisOrder.size();i++){
			for(int j=0;j<BeanOrder.tableTitles.length;j++)
				tblHisOrderData[i][j]=HisOrder.get(i).getCell(j);
		}
		tabHisOrderModel.setDataVector(tblHisOrderData,tblHisOrderTitle);
		this.dataTableHisOrder.validate();
		this.dataTableHisOrder.repaint();
	}
	private void reloadHisOrderInfoTabel(int HisOrderIdx){
		if(HisOrderIdx<0) return;
		curHisOrder=HisOrder.get(HisOrderIdx);
		try {
			orderinfo=takeawayUtil.orderinfoManager.loadOrderInfo(curHisOrder);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblHisOrderInfoData =new Object[orderinfo.size()][BeanOrderInfo.tblOrderInfoTitle.length];
		for(int i=0;i<orderinfo.size();i++){
			for(int j=0;j<BeanOrderInfo.tblOrderInfoTitle.length;j++)
				tblHisOrderInfoData[i][j]=orderinfo.get(i).getCell(j);
		}
		tabHisOrderInfoModel.setDataVector(tblHisOrderInfoData,tblHisOrderInfoTitle);
		this.dataTableHisOrderInfo.validate();
		this.dataTableHisOrderInfo.repaint();
	}
	public FrmStoreHisOrder(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("已送达");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		titleBar.add(title);
		this.getContentPane().add(titleBar, BorderLayout.NORTH);
	    this.getContentPane().add(new JScrollPane(this.dataTableHisOrder), BorderLayout.CENTER);
	    this.dataTableHisOrder.addMouseListener(new MouseAdapter (){
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=FrmStoreHisOrder.this.dataTableHisOrder.getSelectedRow();
				if(i<0) {
					return;
				}
				FrmStoreHisOrder.this.reloadHisOrderInfoTabel(i);
			}
		});
	    this.getContentPane().add(new JScrollPane(this.dataTableHisOrderInfo), BorderLayout.EAST);
	    this.reloadHisOrder();
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
