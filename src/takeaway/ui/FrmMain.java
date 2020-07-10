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
import takeaway.model.BeanStore;
import takeaway.model.BeanUser;
import takeaway.model.BeanProduct;
import takeaway.util.BaseException;


/*
 * public class FrmMain��������Զ�����࣬���ֽ�FrmMain
 * extends JFrame�� �̳�Java��JFrame�࣬JFrame��Java�Ĵ����࣬�̳���������д����һЩ�����ﵽ�������̵�����
 * implements ActionListener����ʵ�� ActionListener�ӿڣ�Ϊ���������ӿڣ���Java swing�������嶯����һ���ӿ�
 */
public class FrmMain extends JFrame implements ActionListener {
	//����������л�ID
	private static final long serialVersionUID = 1L;
	//�����˵���
	private JMenuBar menubar=new JMenuBar(); 
	//�����˵�
	private JMenu menu_order=new JMenu("��Ҫ�µ�");
    private JMenu menu_myorder=new JMenu("�ҵĶ���");
    private JMenu menu_function=new JMenu("�ҵĹ���");
    private JMenu menu_more=new JMenu("����");
    //�����˵���
    private JMenuItem  menuItem_history=new JMenuItem("��ʷ����");//�����
    private JMenuItem  menuItem_ing=new JMenuItem("��������");//�����
    
    private JMenuItem  menuItem_discount=new JMenuItem("�ҵ��Ż�ȯ");
    private JMenuItem  menuItem_reception=new JMenuItem("�ҵ�����");
    private JMenuItem  menuItem_info=new JMenuItem("�ҵ���Ϣ");//�����
    private JMenuItem  menuItem_address=new JMenuItem("�ҵĵ�ַ");//�����
    
    private JMenuItem  menuItem_vip=new JMenuItem("�ҵĻ�Ա");//�����
    private JMenuItem  menuItem_store=new JMenuItem("��Ϊ�̼�");//�����
    private JMenuItem  menuItem_rider=new JMenuItem("��Ϊ����");//������
    private JMenuItem  menuItem_modifyPwd=new JMenuItem("�޸�����");//�����
    
    private FrmLogin dlgLogin=null;
	//����һ�����
	private JPanel statusBar = new JPanel();
	//�̼��б�
	//�������
	private Object tblStoreTitle[]=BeanStore.tableTitles;
	//��ά��洢
	private Object tblStoreData[][];
	//�������ģ��
	DefaultTableModel tabStoreModel=new DefaultTableModel();
	//��tabStoreModelΪģ�͹�����
	private JTable dataTableStore=new JTable(tabStoreModel);
	
	//��Ʒ�б�
	private Object tblProductTitle[]=BeanProduct.tblProductTitle;
	private Object tblProductData[][];
	DefaultTableModel tabProductModel=new DefaultTableModel();
	private JTable dataTableProduct=new JTable(tabProductModel);
	
	private BeanStore curStore=null;
	List<BeanStore> allStore=null;
	List<BeanProduct> Product=null;
	private void reloadStoreTable(){//���ǲ������ݣ���Ҫ��ʵ�����滻
		try {
			//�������е�Store�б���
			allStore=takeawayUtil.storeManager.loadAll();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblStoreData = new Object[allStore.size()][BeanStore.tableTitles.length];
		for(int i=0;i<allStore.size();i++){
			for(int j=0;j<BeanStore.tableTitles.length;j++)
				tblStoreData[i][j]=allStore.get(i).getCell(j);
		}
		tabStoreModel.setDataVector(tblStoreData,tblStoreTitle);
		this.dataTableStore.validate();
		//��֤�������������
		this.dataTableStore.repaint();
		//�ػ�����
	}
	private void reloadProductTabel(int StoreIdx){
		if(StoreIdx<0) return;
		//����Store�б��и�����λ�õ�Store
		curStore=allStore.get(StoreIdx);
		try {
			//���ظ��̼ҵ���Ʒ�б�
			Product=takeawayUtil.productManager.loadProducts(curStore);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		//����һ����ά�����д�СΪProducts.size()���д�СΪBeanProduct.tblProductTitle.length
		tblProductData =new Object[Product.size()][BeanProduct.tblProductTitle.length];
		for(int i=0;i<Product.size();i++){
			for(int j=0;j<BeanProduct.tblProductTitle.length;j++)
				//�������ÿ��
				tblProductData[i][j]=Product.get(i).getCell(j);
		}
		//��ʵ���е�ֵ�滻Ϊ�����е�ֵ��������ΪtblProductData��������ΪtblProductTitle
		tabProductModel.setDataVector(tblProductData,tblProductTitle);
		this.dataTableProduct.validate();
		this.dataTableProduct.repaint();
	}
	public FrmMain(){
		//���ô������
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		//���ô��ڱ���
		this.setTitle("��������");
		dlgLogin=new FrmLogin(this,"��½",true);
		dlgLogin.setVisible(true);
		//���˵�����ӵ��˵�
	    this.menu_myorder.add(this.menuItem_history); this.menuItem_history.addActionListener(this);
	    this.menu_myorder.add(this.menuItem_ing); this.menuItem_ing.addActionListener(this);
	    
	    this.menu_function.add(this.menuItem_discount); this.menuItem_discount.addActionListener(this);
	    this.menu_function.add(this.menuItem_reception); this.menuItem_reception.addActionListener(this);
	    this.menu_function.add(this.menuItem_info); this.menuItem_info.addActionListener(this);
	    this.menu_function.add(this.menuItem_address); this.menuItem_address.addActionListener(this);
	    
	    this.menu_more.add(this.menuItem_vip); this.menuItem_vip.addActionListener(this);
	    this.menu_more.add(this.menuItem_store); this.menuItem_store.addActionListener(this);
	    this.menu_more.add(this.menuItem_rider); this.menuItem_rider.addActionListener(this);
	    this.menu_more.add(this.menuItem_modifyPwd); this.menuItem_modifyPwd.addActionListener(this);
	    //���˵���ӵ��˵���
	    menubar.add(menu_order);
	    menubar.add(menu_myorder);
	    menubar.add(menu_function);
	    menubar.add(menu_more);
	    //�������Ĳ˵�������������
	    this.setJMenuBar(menubar);
	    //����һ����ʾdataTableStore�Ĺ�������ҳ������
	    this.getContentPane().add(new JScrollPane(this.dataTableStore), BorderLayout.WEST);
	    //��������������
	    this.dataTableStore.addMouseListener(new MouseAdapter (){
			@Override
	    	//������ϵ�����갴ťʱ���ú���
			public void mouseClicked(MouseEvent e) {
				//������ѡ��һ�е�����
				int i=FrmMain.this.dataTableStore.getSelectedRow();
				//��û��ѡ�����򷵻�-1
				if(i<0) {
					return;
				}
				//���ض�Ӧ��Ʒ�б�
				FrmMain.this.reloadProductTabel(i);
			}
	    	
	    });
	    //����һ����ʾdataTableProduct�Ĺ�������ҳ����м�
	    this.getContentPane().add(new JScrollPane(this.dataTableProduct), BorderLayout.CENTER);
	    
	    this.reloadStoreTable();
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
		if(e.getSource()==this.menuItem_info){
			FrmUserInfo ui=new FrmUserInfo(this,"�ҵ���Ϣ",true);
			ui.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_address){
			FrmMyAddress ma=new FrmMyAddress(this,"�ҵĵ�ַ",true);
			ma.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_history){
			FrmHisOrder ho=new FrmHisOrder(this,"��ʷ����",true);
			ho.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_ing){
			FrmIngOrder io=new FrmIngOrder(this,"��������",true);
			io.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_discount){
			FrmMyyh my=new FrmMyyh(this,"�ҵ��Ż�",true);
			my.setVisible(true);
		}
		/*else if(e.getSource()==this.menuItem_DeleteStore){
			if(this.curStore==null) {
				JOptionPane.showMessageDialog(null, "��ѡ��ƻ�", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.StoreManager.deleteStore(this.curStore);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}*/
		else if(e.getSource()==this.menuItem_store){
			FrmStore st=new FrmStore(this,"��Ϊ�̼�",true);
			st.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_rider){
			FrmRider ri=new FrmRider(this,"��Ϊ����",true);
			ri.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_vip){
			FrmMyVIP mv=new FrmMyVIP(this,"�ҵĻ�Ա",true);
			mv.setVisible(true);
		}/*
		else if(e.getSource()==this.menuItem_DeleteProduct){
			int i=FrmMain.this.dataTableProduct.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "��ѡ����", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.ProductManager.deleteProduct(this.Product.get(i));
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.menuItem_startProduct){
			int i=FrmMain.this.dataTableProduct.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "��ѡ����", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.ProductManager.startProduct(this.Product.get(i));
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.menuItem_finishProduct){
			int i=FrmMain.this.dataTableProduct.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "��ѡ����", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.ProductManager.finishProduct(this.Product.get(i));
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.menuItem_moveUpProduct){
			int i=FrmMain.this.dataTableProduct.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "��ѡ����", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.ProductManager.moveUp(this.Product.get(i));
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.menuItem_moveDownProduct){
			int i=FrmMain.this.dataTableProduct.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "��ѡ����", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.ProductManager.moveDown(this.Product.get(i));
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.menuItem_static1){
			
		}*/
		else if(e.getSource()==this.menuItem_modifyPwd){
			FrmModifyPwd mp=new FrmModifyPwd(this,"�޸�����",true);
			mp.setVisible(true);
		}
	}
}
