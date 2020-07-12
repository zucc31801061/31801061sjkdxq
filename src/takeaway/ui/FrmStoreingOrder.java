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

public class FrmStoreIngOrder extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnok = new Button("ȷ��");
	private JPanel titleBar = new JPanel();
	private JLabel title = new JLabel("��Ʒ������                                                                                                                                                           �������飺");
	
	private Object tblIngOrderTitle[]=BeanOrder.tableTitles;
	private Object tblIngOrderData[][];
	DefaultTableModel tabIngOrderModel=new DefaultTableModel();
	private JTable dataTableIngOrder=new JTable(tabIngOrderModel);
	
	private Object tblIngOrderInfoTitle[]=BeanOrderInfo.tblOrderInfoTitle;
	private Object tblIngOrderInfoData[][];
	DefaultTableModel tabIngOrderInfoModel=new DefaultTableModel();
	private JTable dataTableIngOrderInfo=new JTable(tabIngOrderInfoModel);
	
	private BeanOrder curIngOrder=null;
	List<BeanOrder> IngOrder=null;
	List<BeanOrderInfo> orderinfo=null;
	private void reloadIngOrder(){
		try {
			IngOrder=takeawayUtil.orderManager.loadstoreingOrder();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblIngOrderData = new Object[IngOrder.size()][BeanOrder.tableTitles.length];
		for(int i=0;i<IngOrder.size();i++){
			for(int j=0;j<BeanOrder.tableTitles.length;j++)
				tblIngOrderData[i][j]=IngOrder.get(i).getCell(j);
		}
		tabIngOrderModel.setDataVector(tblIngOrderData,tblIngOrderTitle);
		this.dataTableIngOrder.validate();
		this.dataTableIngOrder.repaint();
	}
	private void reloadIngOrderInfoTabel(int IngOrderIdx){
		if(IngOrderIdx<0) return;
		curIngOrder=IngOrder.get(IngOrderIdx);
		try {
			orderinfo=takeawayUtil.orderinfoManager.loadOrderInfo(curIngOrder);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		//����һ����ά�����д�СΪorderinfo.size()���д�СΪBeanOrderInfo.tblOrderInfoTitle.length
		tblIngOrderInfoData =new Object[orderinfo.size()][BeanOrderInfo.tblOrderInfoTitle.length];
		for(int i=0;i<orderinfo.size();i++){
			for(int j=0;j<BeanOrderInfo.tblOrderInfoTitle.length;j++)
				//�������ÿ��
				tblIngOrderInfoData[i][j]=orderinfo.get(i).getCell(j);
		}
		//��ʵ���е�ֵ�滻Ϊ�����е�ֵ��������ΪtblOrderInfoData��������ΪtblOrderInfoTitle
		tabIngOrderInfoModel.setDataVector(tblIngOrderInfoData,tblIngOrderInfoTitle);
		this.dataTableIngOrderInfo.validate();
		this.dataTableIngOrderInfo.repaint();
	}
	public FrmStoreIngOrder(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("��������");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		titleBar.add(title);
		this.getContentPane().add(titleBar, BorderLayout.NORTH);
		//����һ����ʾdataTableIngOrder�Ĺ�������ҳ������
	    this.getContentPane().add(new JScrollPane(this.dataTableIngOrder), BorderLayout.CENTER);
	    this.dataTableIngOrder.addMouseListener(new MouseAdapter (){
			@Override
			//������ϵ�����갴ťʱ���ú���
			public void mouseClicked(MouseEvent e) {
				//������ѡ��һ�е�����
				int i=FrmStoreIngOrder.this.dataTableIngOrder.getSelectedRow();
				//��û��ѡ�����򷵻�-1
				if(i<0) {
					return;
				}
				//���ض�Ӧ��Ʒ�б�
				FrmStoreIngOrder.this.reloadIngOrderInfoTabel(i);
			}
		});
	    this.getContentPane().add(new JScrollPane(this.dataTableIngOrderInfo), BorderLayout.EAST);
	    this.reloadIngOrder();
		this.setSize(1100, 250);
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
