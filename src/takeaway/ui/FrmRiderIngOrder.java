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

public class FrmRiderIngOrder extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button send = new Button("ȷ���ʹ�");
	private Button btnok = new Button("ȷ��");
	
	private Object tblIngOrderTitle[]=BeanOrder.tableTitles2;
	private Object tblIngOrderData[][];
	DefaultTableModel tabIngOrderModel=new DefaultTableModel();
	private JTable dataTableIngOrder=new JTable(tabIngOrderModel);
	
	private Object tblUserAddressTitle[]=BeanAddress.tableTitles;
	private Object tblUserAddressData[][];
	DefaultTableModel tabUserAddressModel=new DefaultTableModel();
	private JTable dataTableUserAddress=new JTable(tabUserAddressModel);
	
	BeanOrder curOrder;
	List<BeanOrder> IngOrder=null;
	List<BeanAddress> UserAddress=null;
	private void reloadIngOrder(){//���ǲ������ݣ���Ҫ��ʵ�����滻
		try {
			//��ѯ��ǰIngOrder
			IngOrder=takeawayUtil.orderManager.loadrideringOrder();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblIngOrderData = new Object[IngOrder.size()][BeanOrder.tableTitles2.length];
		for(int i=0;i<IngOrder.size();i++){
			for(int j=0;j<BeanOrder.tableTitles2.length;j++)
				tblIngOrderData[i][j]=IngOrder.get(i).getCell2(j);
		}
		tabIngOrderModel.setDataVector(tblIngOrderData,tblIngOrderTitle);
		this.dataTableIngOrder.validate();
		//��֤�������������
		this.dataTableIngOrder.repaint();
		//�ػ�����
	}
	private void reloadUserAddressTabel(int orderIdx){
		if(orderIdx<0) return;
		//����Order�б��и�����λ�õ�Order
		curOrder=IngOrder.get(orderIdx);
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
	public FrmRiderIngOrder(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("��������");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(send);
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		//����һ����ʾdataTableIngOrder�Ĺ�������ҳ������
	    this.getContentPane().add(new JScrollPane(this.dataTableIngOrder), BorderLayout.CENTER);
	    this.dataTableIngOrder.addMouseListener(new MouseAdapter (){
			@Override
	    	//������ϵ�����갴ťʱ���ú���
			public void mouseClicked(MouseEvent e) {
				//������ѡ��һ�е�����
				int i=FrmRiderIngOrder.this.dataTableIngOrder.getSelectedRow();
				//��û��ѡ�����򷵻�-1
				if(i<0) {
					return;
				}
				curOrder=IngOrder.get(i);
				FrmRiderIngOrder.this.reloadUserAddressTabel(i);
			}
	    });
	    this.getContentPane().add(new JScrollPane(this.dataTableUserAddress), BorderLayout.EAST);
	    this.reloadIngOrder();
	    this.setSize(1100, 300);
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.send.addActionListener(this);
		this.btnok.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnok) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.send) {
			if(this.curOrder==null) {
				JOptionPane.showMessageDialog(null, "��ѡ�񶩵�", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.orderManager.sendOrder(this.curOrder);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.reloadIngOrder();
		}
	}
}
