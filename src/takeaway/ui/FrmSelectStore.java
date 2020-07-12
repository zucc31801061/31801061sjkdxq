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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import takeaway.takeawayUtil;
import takeaway.model.BeanProduct;
import takeaway.model.BeanStore;
import takeaway.util.BaseException;

public class FrmSelectStore extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button del = new Button("ע���̼�");
	private Button btnok = new Button("ȷ��");
	private JPanel titleBar = new JPanel();
	private JLabel title = new JLabel("�������̼��˺ţ�");
	private JTextField name = new JTextField(20);
	private Button select = new Button("��ѯ");
	private JLabel title1 = new JLabel("                                                                                                               ӵ����Ʒ��                                            ");

	private Object tblselstoreTitle[]=BeanStore.tableTitles1;
	private Object tblselstoreData[][];
	DefaultTableModel tabselstoreModel=new DefaultTableModel();
	private JTable dataTableselstore=new JTable(tabselstoreModel);
	
	private Object tblproductTitle[]=BeanProduct.tblProductTitle;
	private Object tblproductData[][];
	DefaultTableModel tabproductModel=new DefaultTableModel();
	private JTable dataTableproduct=new JTable(tabproductModel);
	
	private BeanStore curselstore=null;
	List<BeanStore> selstore=null;
	List<BeanProduct> product=null;
	private void reloadselstore(String name){
		try {
			selstore=takeawayUtil.storeManager.selectstore(name);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblselstoreData = new Object[selstore.size()][BeanStore.tableTitles1.length];
		for(int i=0;i<selstore.size();i++){
			for(int j=0;j<BeanStore.tableTitles1.length;j++)
				tblselstoreData[i][j]=selstore.get(i).getCell1(j);
		}
		tabselstoreModel.setDataVector(tblselstoreData,tblselstoreTitle);
		this.dataTableselstore.validate();
		this.dataTableselstore.repaint();
	}
	private void reloadproductTabel(int selstoreIdx){
		if(selstoreIdx<0) return;
		curselstore=selstore.get(selstoreIdx);
		try {
			product=takeawayUtil.productManager.loadProducts(curselstore);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		//����һ����ά�����д�СΪproduct.size()���д�СΪBeanProduct.tableTitles1.length
		tblproductData =new Object[product.size()][BeanProduct.tblProductTitle.length];
		for(int i=0;i<product.size();i++){
			for(int j=0;j<BeanProduct.tblProductTitle.length;j++)
				//�������ÿ��
				tblproductData[i][j]=product.get(i).getCell(j);
		}
		//��ʵ���е�ֵ�滻Ϊ�����е�ֵ��������ΪtblproductData��������ΪtableTitles1
		tabproductModel.setDataVector(tblproductData,tblproductTitle);
		this.dataTableproduct.validate();
		this.dataTableproduct.repaint();
	}
	public FrmSelectStore(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("��ѯ�̼ң�");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(del);
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		titleBar.add(title);
		titleBar.add(name);
		titleBar.add(select);
		titleBar.add(title1);
		this.getContentPane().add(titleBar, BorderLayout.NORTH);
		//����һ����ʾdataTableselstore�Ĺ�������ҳ������
	    this.getContentPane().add(new JScrollPane(this.dataTableselstore), BorderLayout.CENTER);
	    this.dataTableselstore.addMouseListener(new MouseAdapter (){
			@Override
			//������ϵ�����갴ťʱ���ú���
			public void mouseClicked(MouseEvent e) {
				//������ѡ��һ�е�����
				int i=FrmSelectStore.this.dataTableselstore.getSelectedRow();
				//��û��ѡ�����򷵻�-1
				if(i<0) {
					return;
				}
				//���ض�Ӧ��Ʒ�б�
				FrmSelectStore.this.reloadproductTabel(i);
			}
		});
	    this.getContentPane().add(new JScrollPane(this.dataTableproduct), BorderLayout.EAST);
		this.setSize(1100, 300);
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.select.addActionListener(this);
		this.btnok.addActionListener(this);
		this.del.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnok) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.select) {
			String name=this.name.getText();
			this.reloadselstore(name);
		}
		else if(e.getSource()==this.del) {
			if(this.curselstore==null) {
				JOptionPane.showMessageDialog(null, "��ѡ���̼�", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.storeManager.deleteStore(this.curselstore);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		}
	}
}