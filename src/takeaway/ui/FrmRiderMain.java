package takeaway.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import takeaway.takeawayUtil;
import takeaway.model.BeanOrder;
import takeaway.model.BeanOrderInfo;
import takeaway.util.BaseException;

public class FrmRiderMain extends JFrame implements ActionListener {
	//����������л�ID
	private static final long serialVersionUID = 1L;
	//�����˵���
	private JMenuBar menubar=new JMenuBar(); 
	//�����˵�
	private JMenu menu_takeorder=new JMenu("��Ҫ�ӵ�");
	private JMenu menu_seeorder=new JMenu("�鿴����");
	private JMenu menu_userpj=new JMenu("�û�����");
	private JMenu menu_updateinfo=new JMenu("�޸���Ϣ");
	//�����˵���
	private JMenuItem  menuItem_notsend=new JMenuItem("�ȴ�����");//�����
	private JMenuItem  menuItem_ingsend=new JMenuItem("��������");
	
	private JMenuItem  menuItem_seepj=new JMenuItem("�鿴����");
	
	private JMenuItem  menuItem_updatename=new JMenuItem("�޸�����");
	//�������
	private JPanel statusBar = new JPanel();
	//�����б�
	//�������
	private Object tblOrderTitle[]=BeanOrder.tableTitles2;
	//��ά��洢
	private Object tblOrderData[][];
	//�������ģ��
	DefaultTableModel tabOrderModel=new DefaultTableModel();
	//��tabOrderModelΪģ�͹�����
	private JTable dataTableOrder=new JTable(tabOrderModel);
	
	//���������б�
	private Object tblOrderInfoTitle[]=BeanOrderInfo.tblOrderInfoTitle;
	private Object tblOrderInfoData[][];
	DefaultTableModel tabOrderInfoModel=new DefaultTableModel();
	private JTable dataTableOrderInfo=new JTable(tabOrderInfoModel);
	
	private BeanOrder curOrder=null;
	List<BeanOrder> allOrder=null;
	List<BeanOrderInfo> orderinfo=null;
	private void reloadOrderTable(){//���ǲ������ݣ���Ҫ��ʵ�����滻
		try {
			//�������е�Order�б���
			allOrder=takeawayUtil.orderManager.loadBysj();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblOrderData =  new Object[allOrder.size()][BeanOrder.tableTitles2.length];
		for(int i=0;i<allOrder.size();i++){
			for(int j=0;j<BeanOrder.tableTitles2.length;j++)
				tblOrderData[i][j]=allOrder.get(i).getCell2(j);
		}
		tabOrderModel.setDataVector(tblOrderData,tblOrderTitle);
		this.dataTableOrder.validate();
		//��֤�������������
		this.dataTableOrder.repaint();
		//�ػ�����
	}
	private void reloadOrderInfoTabel(int orderIdx){
		if(orderIdx<0) return;
		//����Order�б��и�����λ�õ�Order
		curOrder=allOrder.get(orderIdx);
		try {
			//���ض�Ӧ��OrderInfo�б�
			orderinfo=takeawayUtil.orderinfoManager.loadOrderInfo(curOrder);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		//����һ����ά�����д�СΪorderinfo.size()���д�СΪBeanOrderInfo.tblOrderInfoTitle.length
		tblOrderInfoData =new Object[orderinfo.size()][BeanOrderInfo.tblOrderInfoTitle.length];
		for(int i=0;i<orderinfo.size();i++){
			for(int j=0;j<BeanOrderInfo.tblOrderInfoTitle.length;j++)
				//�������ÿ��
				tblOrderInfoData[i][j]=orderinfo.get(i).getCell(j);
		}
		//��ʵ���е�ֵ�滻Ϊ�����е�ֵ��������ΪtblOrderInfoData��������ΪtblOrderInfoTitle
		tabOrderInfoModel.setDataVector(tblOrderInfoData,tblOrderInfoTitle);
		this.dataTableOrderInfo.validate();
		this.dataTableOrderInfo.repaint();
	}
	public FrmRiderMain() {
		//���ô������
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		//���ô��ڱ���
		this.setTitle("��������");
		//���˵�����ӵ��˵�
		this.menu_seeorder.add(this.menuItem_notsend); this.menuItem_notsend.addActionListener(this);
		this.menu_seeorder.add(this.menuItem_ingsend); this.menuItem_ingsend.addActionListener(this);
		
		this.menu_userpj.add(this.menuItem_seepj); this.menuItem_seepj.addActionListener(this);
		
		this.menu_updateinfo.add(this.menuItem_updatename); this.menuItem_updatename.addActionListener(this);
		//���˵���ӵ��˵���
		menubar.add(menu_takeorder);
		menubar.add(menu_seeorder);
		menubar.add(menu_userpj);
		menubar.add(menu_updateinfo);
		//�������Ĳ˵�������������
		this.setJMenuBar(menubar);
		//����һ����ʾdataTableOrder�Ĺ�������ҳ������
		this.getContentPane().add(new JScrollPane(this.dataTableOrder), BorderLayout.WEST);
		//��������������
		this.dataTableOrder.addMouseListener(new MouseAdapter (){
			@Override
			//������ϵ�����갴ťʱ���ú���
			public void mouseClicked(MouseEvent e) {
				//������ѡ��һ�е�����
				int i=FrmRiderMain.this.dataTableOrder.getSelectedRow();
				//��û��ѡ�����򷵻�-1
				if(i<0) {
					return;
				}
				//���ض�Ӧ��Ʒ�б�
				FrmRiderMain.this.reloadOrderInfoTabel(i);
			}
			
		});
		//����һ����ʾdataTableOrderInfo�Ĺ�������ҳ����м�
		this.getContentPane().add(new JScrollPane(this.dataTableOrderInfo), BorderLayout.CENTER);
		
		this.reloadOrderTable();
		//����һ��״̬����ҳ�����
		statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel label=new JLabel("����!");//�޸ĳ�   ���ã�+��½�û���
		statusBar.add(label);
		this.getContentPane().add(statusBar,BorderLayout.SOUTH);
		this.addWindowListener(new WindowAdapter(){   
			public void windowClosing(WindowEvent e){ 
				this.windowClosed(e);
			}
		});
		this.setVisible(true);
	}	
	@Override
	public void actionPerformed(ActionEvent e) {
		/*if(e.getSource()==this.btnBack) {
			this.setVisible(false);
		}
		else if(e.getSource()==this.becomesj){
			String name=this.edtsjname.getText();
			try {
				takeawayUtil.planManager.addRider(name);
			} catch (BaseException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
			}
		}*/
	}
}
