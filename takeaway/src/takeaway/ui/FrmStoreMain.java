package takeaway.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import takeaway.takeawayUtil;
import takeaway.model.BeanOrder;
import takeaway.model.BeanOrderInfo;
import takeaway.model.BeanProduct;
import takeaway.model.BeanStore;
import takeaway.util.BaseException;

public class FrmStoreMain extends JFrame implements ActionListener {
	//����������л�ID
	private static final long serialVersionUID = 1L;
	//�����˵���
	private JMenuBar menubar=new JMenuBar(); 
	//�����˵�
	private JMenu menu_myproduct=new JMenu("�ҵ���Ʒ");
    private JMenu menu_provideyh=new JMenu("�����Ż�");
    private JMenu menu_userorder=new JMenu("�û�����");
	private JMenu menu_userpj=new JMenu("�û�����");
	private JMenu menu_updateinfo=new JMenu("�޸���Ϣ");
    //�����˵���
	private JMenuItem  menuItem_addproduct=new JMenuItem("������Ʒ");
	private JMenuItem  menuItem_delproduct=new JMenuItem("ɾ����Ʒ");
	private JMenuItem  menuItem_addkind=new JMenuItem("�������");
	private JMenuItem  menuItem_delkind=new JMenuItem("ɾ�����");
	
	private JMenuItem  menuItem_mymj=new JMenuItem("�ҵ�����");
	private JMenuItem  menuItem_myyh=new JMenuItem("�ҵ��Ż�ȯ");
	
	private JMenuItem  menuItem_notsend=new JMenuItem("δ����");//�����
	private JMenuItem  menuItem_ingsend=new JMenuItem("��������");
	
	private JMenuItem  menuItem_seepj=new JMenuItem("�鿴����");
	
	private JMenuItem  menuItem_updatename=new JMenuItem("�޸��̼���");
	//�������
	private JPanel statusBar = new JPanel();
	//�����б�
	//�������
	private Object tblOrderTitle[]=BeanOrder.tableTitles;
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
		tblOrderData =  new Object[allOrder.size()][BeanOrder.tableTitles.length];
		for(int i=0;i<allOrder.size();i++){
			for(int j=0;j<BeanOrder.tableTitles.length;j++)
				tblOrderData[i][j]=allOrder.get(i).getCell(j);
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
	public FrmStoreMain() {
		//���ô������
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		//���ô��ڱ���
		this.setTitle("�����̼�");
		//���˵�����ӵ��˵�
		this.menu_myproduct.add(this.menuItem_addproduct); this.menuItem_addproduct.addActionListener(this);
		this.menu_myproduct.add(this.menuItem_delproduct); this.menuItem_delproduct.addActionListener(this);
		this.menu_myproduct.add(this.menuItem_addkind); this.menuItem_addkind.addActionListener(this);
		this.menu_myproduct.add(this.menuItem_delkind); this.menuItem_delkind.addActionListener(this);
		
		this.menu_provideyh.add(this.menuItem_mymj); this.menuItem_mymj.addActionListener(this);
		this.menu_provideyh.add(this.menuItem_myyh); this.menuItem_myyh.addActionListener(this);
		
		this.menu_userorder.add(this.menuItem_notsend); this.menuItem_notsend.addActionListener(this);
		this.menu_userorder.add(this.menuItem_ingsend); this.menuItem_ingsend.addActionListener(this);
		
		this.menu_userpj.add(this.menuItem_seepj); this.menuItem_seepj.addActionListener(this);
		
		this.menu_updateinfo.add(this.menuItem_updatename); this.menuItem_updatename.addActionListener(this);
		//���˵���ӵ��˵���
		menubar.add(menu_myproduct);
		menubar.add(menu_provideyh);
		menubar.add(menu_userorder);
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
				int i=FrmStoreMain.this.dataTableOrder.getSelectedRow();
				//��û��ѡ�����򷵻�-1
				if(i<0) {
					return;
				}
				//���ض�Ӧ��Ʒ�б�
				FrmStoreMain.this.reloadOrderInfoTabel(i);
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
				takeawayUtil.planManager.addStore(name);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}*/
	}
}
