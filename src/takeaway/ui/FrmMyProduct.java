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
import takeaway.model.BeanAddress;
import takeaway.model.BeanProduct;
import takeaway.util.BaseException;

public class FrmMyProduct extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button addpro = new Button("������Ʒ");
	private Button updpro = new Button("�޸���Ʒ");
	private Button delpro = new Button("ɾ����Ʒ");
	private Button btnok = new Button("ȷ��");
	
	private JPanel workPane = new JPanel();
	private JLabel newname = new JLabel("��Ʒ��");
	private JLabel newkind = new JLabel("���ࣺ");
	private JLabel startmoney = new JLabel("ԭ�ۣ�");
	private JLabel endmoney = new JLabel("�Żݼۣ�");
	
	private JTextField name = new JTextField(10);
	private JTextField kind = new JTextField(10);
	private JTextField start= new JTextField(10);
	private JTextField end = new JTextField(10);
	
	//��ַ�б�
	//�������
	private Object tblProTitle[]=BeanProduct.tblProductTitle;
	//��ά��洢
	private Object tblProData[][];
	//�������ģ��
	DefaultTableModel tabProModel=new DefaultTableModel();
	//��tabProModelΪģ�͹�����
	private JTable dataTablePro=new JTable(tabProModel);
	
	BeanProduct curPro;
	List<BeanProduct> Pro=null;
	private void reloadProTable(){//���ǲ������ݣ���Ҫ��ʵ�����滻
		try {
			//��ѯ��ǰPro
			Pro=takeawayUtil.productManager.loadAll();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblProData = new Object[Pro.size()][BeanProduct.tblProductTitle.length];
		for(int i=0;i<Pro.size();i++){
			for(int j=0;j<BeanProduct.tblProductTitle.length;j++)
				tblProData[i][j]=Pro.get(i).getCell(j);
		}
		tabProModel.setDataVector(tblProData,tblProTitle);
		this.dataTablePro.validate();
		//��֤�������������
		this.dataTablePro.repaint();
		//�ػ�����
	}
	public FrmMyProduct(JFrame f, String s, boolean b) {
		super(f, s, b);
		//���ô��ڱ���
		this.setTitle("�ҵ���Ʒ");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(addpro);
		toolBar.add(updpro);
		toolBar.add(delpro);
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		workPane.add(newname);
		workPane.add(name);
		workPane.add(newkind);
		workPane.add(kind);
		workPane.add(startmoney);
		workPane.add(start);
		workPane.add(endmoney);
		workPane.add(end);
		//����Ϣ����
		this.getContentPane().add(workPane, BorderLayout.NORTH);
		
		//����һ����ʾdataTableStore�Ĺ�������ҳ������
	    this.getContentPane().add(new JScrollPane(this.dataTablePro), BorderLayout.CENTER);
	  //��������������
	    this.dataTablePro.addMouseListener(new MouseAdapter (){
			@Override
	    	//������ϵ�����갴ťʱ���ú���
			public void mouseClicked(MouseEvent e) {
				//������ѡ��һ�е�����
				int i=FrmMyProduct.this.dataTablePro.getSelectedRow();
				//��û��ѡ�����򷵻�-1
				if(i<0) {
					return;
				}
				curPro=Pro.get(i);
			}
	    });
	    this.reloadProTable();
		this.setSize(650, 250);
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.addpro.addActionListener(this);
		this.updpro.addActionListener(this);
		this.delpro.addActionListener(this);
		this.btnok.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnok) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.addpro) {
			String name=this.name.getText();
			String kind=this.kind.getText();
			Float start=Float.parseFloat(this.start.getText());
			Float end=Float.parseFloat(this.end.getText());
			try {
				takeawayUtil.productManager.addproduct(name,kind,start,end);
				this.reloadProTable();
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		}
		else if(e.getSource()==this.updpro) {
			String name=this.name.getText();
			String kind=this.kind.getText();
			Float start=Float.parseFloat(this.start.getText());
			Float end=Float.parseFloat(this.end.getText());
			try {
				takeawayUtil.productManager.updateProduct(this.curPro,name,kind,start,end);
				this.reloadProTable();
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		}
		else if(e.getSource()==this.delpro) {
			if(this.curPro==null) {
				JOptionPane.showMessageDialog(null, "��ѡ����Ʒ", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.productManager.deleteProduct(this.curPro);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.reloadProTable();
		}
	}
}
