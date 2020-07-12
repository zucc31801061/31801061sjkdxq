package takeaway.ui;

import java.awt.BorderLayout;
import java.awt.Button;
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
import takeaway.model.BeanRider;
import takeaway.model.BeanStore;
import takeaway.model.BeanUser;
import takeaway.util.BaseException;

public class FrmGlyMain extends JFrame implements ActionListener {
	//����������л�ID
		private static final long serialVersionUID = 1L;
		//�����˵���
		private JMenuBar menubar=new JMenuBar(); 
		//�����˵�
		private JMenu menu_user=new JMenu("�û�����");
		private JMenu menu_store=new JMenu("�̼Ҳ���");
		private JMenu menu_rider=new JMenu("���ֲ���");
		private JMenu menu_more=new JMenu("����");
		//�����˵���
		private JMenuItem  menuItem_userxfinfo=new JMenuItem("�û��������");//�����
		private JMenuItem  menuItem_userinfo=new JMenuItem("��ѯ�û�");//�����
		
		private JMenuItem  menuItem_seefl=new JMenuItem("�鿴����");//�����
		private JMenuItem  menuItem_storeinfo=new JMenuItem("��ѯ�̼�");//�����
		
		private JMenuItem  menuItem_riderrz=new JMenuItem("��������");//�����
		private JMenuItem  menuItem_riderinfo=new JMenuItem("��ѯ����");//�����
		
		private JMenuItem  menuItem_updateinfo=new JMenuItem("�ҵ���Ϣ");
		private JMenuItem  menuItem_updatemm=new JMenuItem("�޸���Ϣ");
		
		private JPanel menuBar = new JPanel();
		private JLabel userl = new JLabel("���֣�                                                                                                                        ");
		private JLabel riderl = new JLabel("                                       �û���                                                                                 ");
		private JLabel storel = new JLabel("                                                                                     �̼ң�");
		private JPanel statusBar = new JPanel();
		
		//�����û�
		private Object tbluserTitle[]=BeanUser.tableTitles2;
		private Object tbluserData[][];
		DefaultTableModel tabuserModel=new DefaultTableModel();
		private JTable dataTableuser=new JTable(tabuserModel);
		List<BeanUser> user=null;
		private void reloaduser(){
			try {
				user=takeawayUtil.userManager.loadAll();
			} catch (BaseException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			tbluserData = new Object[user.size()][BeanUser.tableTitles2.length];
			for(int i=0;i<user.size();i++){
				for(int j=0;j<BeanUser.tableTitles2.length;j++)
					tbluserData[i][j]=user.get(i).getCell2(j);
			}
			tabuserModel.setDataVector(tbluserData,tbluserTitle);
			this.dataTableuser.validate();
			this.dataTableuser.repaint();
		}
		//�����̼�
		private Object tblstoreTitle[]=BeanStore.tableTitles1;
		private Object tblstoreData[][];
		DefaultTableModel tabstoreModel=new DefaultTableModel();
		private JTable dataTablestore=new JTable(tabstoreModel);
		List<BeanStore> store=null;
		private void reloadstore(){
			try {
				store=takeawayUtil.storeManager.loadAll();
			} catch (BaseException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			tblstoreData = new Object[store.size()][BeanStore.tableTitles1.length];
			for(int i=0;i<store.size();i++){
				for(int j=0;j<BeanStore.tableTitles1.length;j++)
					tblstoreData[i][j]=store.get(i).getCell1(j);
			}
			tabstoreModel.setDataVector(tblstoreData,tblstoreTitle);
			this.dataTablestore.validate();
			this.dataTablestore.repaint();
		}
		//��������
		private Object tblriderTitle[]=BeanRider.tableTitles1;
		private Object tblriderData[][];
		DefaultTableModel tabriderModel=new DefaultTableModel();
		private JTable dataTablerider=new JTable(tabriderModel);
		List<BeanRider> rider=null;
		private void reloadrider(){
			try {
				rider=takeawayUtil.riderManager.loadAll();
			} catch (BaseException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			tblriderData = new Object[rider.size()][BeanRider.tableTitles1.length];
			for(int i=0;i<rider.size();i++){
				for(int j=0;j<BeanRider.tableTitles1.length;j++)
					tblriderData[i][j]=rider.get(i).getCell1(j);
			}
			tabriderModel.setDataVector(tblriderData,tblriderTitle);
			this.dataTablerider.validate();
			this.dataTablerider.repaint();
		}
		
		public FrmGlyMain() {
			//���ô������
			this.setExtendedState(Frame.MAXIMIZED_BOTH);
			//���ô��ڱ���
			this.setTitle("���ǹ���Ա");
			//���˵�����ӵ��˵�
			this.menu_user.add(this.menuItem_userxfinfo); this.menuItem_userxfinfo.addActionListener(this);
			this.menu_user.add(this.menuItem_userinfo); this.menuItem_userinfo.addActionListener(this);
			
			this.menu_store.add(this.menuItem_seefl); this.menuItem_seefl.addActionListener(this);
			this.menu_store.add(this.menuItem_storeinfo); this.menuItem_storeinfo.addActionListener(this);
			
			this.menu_rider.add(this.menuItem_riderrz); this.menuItem_riderrz.addActionListener(this);
			this.menu_rider.add(this.menuItem_riderinfo); this.menuItem_riderinfo.addActionListener(this);
			
			this.menu_more.add(this.menuItem_updateinfo); this.menuItem_updateinfo.addActionListener(this);
			this.menu_more.add(this.menuItem_updatemm); this.menuItem_updatemm.addActionListener(this);
			//���˵���ӵ��˵���
			menubar.add(menu_user);
			menubar.add(menu_store);
			menubar.add(menu_rider);
			menubar.add(menu_more);
			//�������Ĳ˵�������������
			this.setJMenuBar(menubar);
			
			menuBar.add(userl);
			menuBar.add(riderl);
			menuBar.add(storel);
			this.getContentPane().add(menuBar, BorderLayout.NORTH);
			
			//����һ����ʾdataTableOrder�Ĺ�������ҳ������
			this.getContentPane().add(new JScrollPane(this.dataTableuser), BorderLayout.CENTER);
			this.getContentPane().add(new JScrollPane(this.dataTablerider), BorderLayout.WEST);
			this.getContentPane().add(new JScrollPane(this.dataTablestore), BorderLayout.EAST);
			
			this.reloaduser();
			this.reloadrider();
			this.reloadstore();
			/*this.reloadOrderTable();*/
			//����һ��״̬����ҳ�����
			statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
			JLabel label=new JLabel("����!");//�޸ĳ�   ���ã�+��½�û���
			statusBar.add(label);
			this.getContentPane().add(statusBar,BorderLayout.SOUTH);
			this.addWindowListener(new WindowAdapter(){   
				public void windowClosing(WindowEvent e){ 
					System.exit(0);
				}
			});
			this.setVisible(true);
		}	
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==this.menuItem_userxfinfo) {
				FrmUserXfInfo uxi=new FrmUserXfInfo(this,"�û��������",true);
				uxi.setVisible(true);
			}
			else if(e.getSource()==this.menuItem_userinfo) {
				FrmSelectUser su=new FrmSelectUser(this,"��ѯ�û�",true);
				su.setVisible(true);
			}
			else if(e.getSource()==this.menuItem_seefl) {
				FrmSearchFl sf=new FrmSearchFl(this,"�鿴����",true);
				sf.setVisible(true);
			}
			else if(e.getSource()==this.menuItem_riderrz) {
				FrmAllQsrz aq=new FrmAllQsrz(this,"��������",true);
				aq.setVisible(true);
			}
			else if(e.getSource()==this.menuItem_riderinfo) {
				FrmSelectRider sr=new FrmSelectRider(this,"��ѯ����",true);
				sr.setVisible(true);
			}
			else if(e.getSource()==this.menuItem_updateinfo) {
				FrmGlyInfo gi=new FrmGlyInfo(this,"�ҵ���Ϣ",true);
				gi.setVisible(true);
			}
			else if(e.getSource()==this.menuItem_updatemm) {
				FrmChangeYgmm cy=new FrmChangeYgmm(this,"�޸�����",true);
				cy.setVisible(true);
			}
		}
}
