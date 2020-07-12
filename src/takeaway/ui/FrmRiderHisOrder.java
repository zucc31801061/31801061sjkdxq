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
import takeaway.model.BeanOrder;
import takeaway.util.BaseException;

public class FrmRiderHisOrder extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnok = new Button("ȷ��");
	
	private Object tblHisOrderTitle[]=BeanOrder.tableTitles2;
	private Object tblHisOrderData[][];
	DefaultTableModel tabHisOrderModel=new DefaultTableModel();
	private JTable dataTableHisOrder=new JTable(tabHisOrderModel);
	
	private Object tblUserAddressTitle[]=BeanAddress.tableTitles;
	private Object tblUserAddressData[][];
	DefaultTableModel tabUserAddressModel=new DefaultTableModel();
	private JTable dataTableUserAddress=new JTable(tabUserAddressModel);
	
	BeanOrder curOrder;
	List<BeanOrder> HisOrder=null;
	List<BeanAddress> UserAddress=null;
	private void reloadHisOrder(){
		try {
			//��ѯ��ǰHisOrder
			HisOrder=takeawayUtil.orderManager.loadriderHisOrder();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblHisOrderData = new Object[HisOrder.size()][BeanOrder.tableTitles2.length];
		for(int i=0;i<HisOrder.size();i++){
			for(int j=0;j<BeanOrder.tableTitles2.length;j++)
				tblHisOrderData[i][j]=HisOrder.get(i).getCell2(j);
		}
		tabHisOrderModel.setDataVector(tblHisOrderData,tblHisOrderTitle);
		this.dataTableHisOrder.validate();
		//��֤�������������
		this.dataTableHisOrder.repaint();
		//�ػ�����
	}
	private void reloadUserAddressTabel(int orderIdx){
		if(orderIdx<0) return;
		//����Order�б��и�����λ�õ�Order
		curOrder=HisOrder.get(orderIdx);
		try {
			//���ض�Ӧ��UserAddress�б�
			UserAddress=takeawayUtil.addressManager.loadselect(curOrder);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		//����һ����ά�����д�СΪUserAddress.size()���д�СΪBeanAddressAddress.tblUserAddressTitle.length
		tblUserAddressData =new Object[UserAddress.size()][BeanAddress.tableTitles.length];
		for(int i=0;i<UserAddress.size();i++){
			for(int j=0;j<BeanAddress.tableTitles.length;j++)
				//�������ÿ��
				tblUserAddressData[i][j]=UserAddress.get(i).getCell(j);
		}
		//��ʵ���е�ֵ�滻Ϊ�����е�ֵ��������ΪtblUserAddressData��������ΪtblUserAddressTitle
		tabUserAddressModel.setDataVector(tblUserAddressData,tblUserAddressTitle);
		this.dataTableUserAddress.validate();
		this.dataTableUserAddress.repaint();
	}
	public FrmRiderHisOrder(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("���ʹ�");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		//����һ����ʾdataTableHisOrder�Ĺ�������ҳ������
	    this.getContentPane().add(new JScrollPane(this.dataTableHisOrder), BorderLayout.CENTER);
	    this.dataTableHisOrder.addMouseListener(new MouseAdapter (){
			@Override
			//������ϵ�����갴ťʱ���ú���
			public void mouseClicked(MouseEvent e) {
				//������ѡ��һ�е�����
				int i=FrmRiderHisOrder.this.dataTableHisOrder.getSelectedRow();
				//��û��ѡ�����򷵻�-1
				if(i<0) {
					return;
				}
				FrmRiderHisOrder.this.reloadUserAddressTabel(i);
			}
		});
		//����һ����ʾdataTableUserAddress�Ĺ�������ҳ����м�
		this.getContentPane().add(new JScrollPane(this.dataTableUserAddress), BorderLayout.EAST);
	    this.reloadHisOrder();
		this.setSize(1100, 300);
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
