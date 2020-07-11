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

public class FrmHisOrder extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnok = new Button("ȷ��");
	private JPanel titleBar = new JPanel();
	private JLabel title = new JLabel("��Ʒ������                                                                                                                                                           �������飺");

	private Object tblHisOrderTitle[]=BeanOrder.tableTitles1;
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
			HisOrder=takeawayUtil.orderManager.loaduserHisOrder();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblHisOrderData = new Object[HisOrder.size()][BeanOrder.tableTitles1.length];
		for(int i=0;i<HisOrder.size();i++){
			for(int j=0;j<BeanOrder.tableTitles1.length;j++)
				tblHisOrderData[i][j]=HisOrder.get(i).getCell1(j);
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
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		//����һ����ά�����д�СΪorderinfo.size()���д�СΪBeanOrderInfo.tblOrderInfoTitle.length
		tblHisOrderInfoData =new Object[orderinfo.size()][BeanOrderInfo.tblOrderInfoTitle.length];
		for(int i=0;i<orderinfo.size();i++){
			for(int j=0;j<BeanOrderInfo.tblOrderInfoTitle.length;j++)
				//�������ÿ��
				tblHisOrderInfoData[i][j]=orderinfo.get(i).getCell(j);
		}
		//��ʵ���е�ֵ�滻Ϊ�����е�ֵ��������ΪtblOrderInfoData��������ΪtblOrderInfoTitle
		tabHisOrderInfoModel.setDataVector(tblHisOrderInfoData,tblHisOrderInfoTitle);
		this.dataTableHisOrderInfo.validate();
		this.dataTableHisOrderInfo.repaint();
	}
	public FrmHisOrder(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("��ʷ����");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		titleBar.add(title);
		this.getContentPane().add(titleBar, BorderLayout.NORTH);
		//����һ����ʾdataTableHisOrder�Ĺ�������ҳ������
	    this.getContentPane().add(new JScrollPane(this.dataTableHisOrder), BorderLayout.WEST);
	    this.dataTableHisOrder.addMouseListener(new MouseAdapter (){
			@Override
			//������ϵ�����갴ťʱ���ú���
			public void mouseClicked(MouseEvent e) {
				//������ѡ��һ�е�����
				int i=FrmHisOrder.this.dataTableHisOrder.getSelectedRow();
				//��û��ѡ�����򷵻�-1
				if(i<0) {
					return;
				}
				//���ض�Ӧ��Ʒ�б�
				FrmHisOrder.this.reloadHisOrderInfoTabel(i);
			}
		});
	    this.getContentPane().add(new JScrollPane(this.dataTableHisOrderInfo), BorderLayout.EAST);
	    this.reloadHisOrder();
		this.setSize(1000, 300);
		// ��Ļ������ʾ
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
