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
import takeaway.model.BeanAddress;
import takeaway.model.BeanOrder;
import takeaway.util.BaseException;

public class FrmGlyMain extends JFrame implements ActionListener {
	//����������л�ID
		private static final long serialVersionUID = 1L;
		//�����˵���
		private JMenuBar menubar=new JMenuBar(); 
		//�����˵�
		private JMenu menu_takeorder=new JMenu("��Ҫ�ӵ�");
		private JMenu menu_seeorder=new JMenu("�鿴����");
		private JMenu menu_updateinfo=new JMenu("�޸���Ϣ");
		//�����˵���
		private JMenuItem  menuItem_takeorder=new JMenuItem("��Ҫ�ӵ�");//�����
		
		private JMenuItem  menuItem_alrsend=new JMenuItem("���ʹ�");//�����
		private JMenuItem  menuItem_ingsend=new JMenuItem("��������");//�����
		private JMenuItem  menuItem_mytake=new JMenuItem("�ҵ�����");//�����
		
		private JMenuItem  menuItem_updatename=new JMenuItem("�޸���Ϣ");
		private JMenuItem  menuItem_myinfo=new JMenuItem("�ҵ���Ϣ");
		//�������
		private JPanel statusBar = new JPanel();
		//�����б�
		//�������
		private Object tblOrderTitle[]=BeanOrder.tableTitles3;
		//��ά��洢
		private Object tblOrderData[][];
		//�������ģ��
		DefaultTableModel tabOrderModel=new DefaultTableModel();
		//��tabOrderModelΪģ�͹�����
		private JTable dataTableOrder=new JTable(tabOrderModel);
		
		//�û���ַ�б�
		private Object tblUserAddressTitle[]=BeanAddress.tableTitles;
		private Object tblUserAddressData[][];
		DefaultTableModel tabUserAddressModel=new DefaultTableModel();
		private JTable dataTableUserAddress=new JTable(tabUserAddressModel);
		
		private BeanOrder curOrder=null;
		List<BeanOrder> allOrder=null;
		List<BeanAddress> UserAddress=null;
		/*private void reloadOrderTable(){//���ǲ������ݣ���Ҫ��ʵ�����滻
			try {
				//�������е�Order�б���
				allOrder=takeawayUtil.orderManager.loadByqs();
			} catch (BaseException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			tblOrderData =  new Object[allOrder.size()][BeanOrder.tableTitles3.length];
			for(int i=0;i<allOrder.size();i++){
				for(int j=0;j<BeanOrder.tableTitles3.length;j++)
					tblOrderData[i][j]=allOrder.get(i).getCell3(j);
			}
			tabOrderModel.setDataVector(tblOrderData,tblOrderTitle);
			this.dataTableOrder.validate();
			//��֤�������������
			this.dataTableOrder.repaint();
			//�ػ�����
		}
		private void reloadUserAddressTabel(int orderIdx){
			if(orderIdx<0) return;
			//����Order�б��и�����λ�õ�Order
			curOrder=allOrder.get(orderIdx);
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
		}*/
		public FrmGlyMain() {
			//���ô������
			this.setExtendedState(Frame.MAXIMIZED_BOTH);
			//���ô��ڱ���
			this.setTitle("��������");
			//���˵�����ӵ��˵�
			this.menu_takeorder.add(this.menuItem_takeorder); this.menuItem_takeorder.addActionListener(this);
			
			this.menu_seeorder.add(this.menuItem_alrsend); this.menuItem_alrsend.addActionListener(this);
			this.menu_seeorder.add(this.menuItem_ingsend); this.menuItem_ingsend.addActionListener(this);
			this.menu_seeorder.add(this.menuItem_mytake); this.menuItem_mytake.addActionListener(this);
			
			this.menu_updateinfo.add(this.menuItem_updatename); this.menuItem_updatename.addActionListener(this);
			this.menu_updateinfo.add(this.menuItem_myinfo); this.menuItem_myinfo.addActionListener(this);
			//���˵���ӵ��˵���
			menubar.add(menu_takeorder);
			menubar.add(menu_seeorder);
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
					int i=FrmGlyMain.this.dataTableOrder.getSelectedRow();
					//��û��ѡ�����򷵻�-1
					if(i<0) {
						return;
					}
					//���ض�Ӧ��Ʒ�б�
					/*FrmGlyMain.this.reloadUserAddressTabel(i);*/
				}
				
			});
			//����һ����ʾdataTableUserAddress�Ĺ�������ҳ����м�
			this.getContentPane().add(new JScrollPane(this.dataTableUserAddress), BorderLayout.CENTER);
			
			/*this.reloadOrderTable();*/
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
			if(e.getSource()==this.menuItem_alrsend) {
				FrmRiderHisOrder rho=new FrmRiderHisOrder(this,"���ʹ�",true);
				rho.setVisible(true);
			}
			else if(e.getSource()==this.menuItem_ingsend) {
				FrmRiderIngOrder rio=new FrmRiderIngOrder(this,"��������",true);
				rio.setVisible(true);
			}
			else if(e.getSource()==this.menuItem_takeorder) {
				FrmTakeOrder to=new FrmTakeOrder(this,"��Ҫ�ӵ�",true);
				to.setVisible(true);
			}
			else if(e.getSource()==this.menuItem_mytake) {
				FrmMyTake mt=new FrmMyTake(this,"�ҵ�����",true);
				mt.setVisible(true);
			}
			else if(e.getSource()==this.menuItem_updatename) {
				FrmUpdateRider ur=new FrmUpdateRider(this,"�޸���Ϣ",true);
				ur.setVisible(true);
			}
			else if(e.getSource()==this.menuItem_myinfo) {
				FrmRiderInfo ri=new FrmRiderInfo(this,"�ҵ���Ϣ",true);
				ri.setVisible(true);
			}
		}
}
