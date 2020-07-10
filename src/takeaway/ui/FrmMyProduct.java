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
import takeaway.model.BeanProduct;
import takeaway.util.BaseException;

public class FrmMyProduct extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button addpro = new Button("������Ʒ");
	private Button delpro = new Button("ɾ����Ʒ");
	private Button btnok = new Button("ȷ��");
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
		toolBar.add(delpro);
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		//����һ����ʾdataTableStore�Ĺ�������ҳ������
	    this.getContentPane().add(new JScrollPane(this.dataTablePro), BorderLayout.WEST);
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
		this.setSize(460, 250);
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.addpro.addActionListener(this);
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
			FrmAddProduct ap=new FrmAddProduct(this, "������Ʒ", true);
			ap.setVisible(true);
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
			this.setVisible(false);
		}
	}
}
