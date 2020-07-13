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

import com.sun.org.apache.bcel.internal.generic.AALOAD;

import takeaway.takeawayUtil;
import takeaway.model.BeanAddress;
import takeaway.model.BeanStore;
import takeaway.util.BaseException;

public class FrmMyAddress extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button addadd = new Button("���ӵ�ַ");
	private Button updadd = new Button("�޸ĵ�ַ");
	private Button deladd = new Button("ɾ����ַ");
	private Button btnok = new Button("ȷ��");
	
	private JPanel workPane = new JPanel();
	private JLabel NewSheng = new JLabel("ʡ��");
	private JLabel NewShi = new JLabel("�У�");
	private JLabel NewQu = new JLabel("����");
	private JLabel NewAddress = new JLabel("��ַ��");
	private JLabel NewName = new JLabel("�û�����");
	private JLabel NewPhnum = new JLabel("�ֻ��ţ�");
	
	private JTextField Sheng = new JTextField(10);
	private JTextField Shi = new JTextField(10);
	private JTextField Qu= new JTextField(10);
	private JTextField Address = new JTextField(10);
	private JTextField Name = new JTextField(10);
	private JTextField Phnum = new JTextField(10);
	
	//��ַ�б�
	//�������
	private Object tblAddTitle[]=BeanAddress.tableTitles;
	//��ά��洢
	private Object tblAddData[][];
	//�������ģ��
	DefaultTableModel tabAddModel=new DefaultTableModel();
	//��tabAddModelΪģ�͹�����
	private JTable dataTableAdd=new JTable(tabAddModel);
	
	BeanAddress curAdd;
	List<BeanAddress> Add=null;
	private void reloadAddTable(){//���ǲ������ݣ���Ҫ��ʵ�����滻
		try {
			//��ѯ��ǰAdd
			Add=takeawayUtil.addressManager.loadAll();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblAddData = new Object[Add.size()][BeanAddress.tableTitles.length];
		for(int i=0;i<Add.size();i++){
			for(int j=0;j<BeanAddress.tableTitles.length;j++)
				tblAddData[i][j]=Add.get(i).getCell(j);
		}
		tabAddModel.setDataVector(tblAddData,tblAddTitle);
		this.dataTableAdd.validate();
		//��֤�������������
		this.dataTableAdd.repaint();
		//�ػ�����
	}
	public FrmMyAddress(JFrame f, String s, boolean b) {
		super(f, s, b);
		//���ô��ڱ���
		this.setTitle("�ҵĵ�ַ");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(addadd);
		toolBar.add(updadd);
		toolBar.add(deladd);
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(NewSheng);
		workPane.add(Sheng);
		workPane.add(NewShi);
		workPane.add(Shi);
		workPane.add(NewQu);
		workPane.add(Qu);
		workPane.add(NewAddress);
		workPane.add(Address);
		workPane.add(NewName);
		workPane.add(Name);
		workPane.add(NewPhnum);
		workPane.add(Phnum);
		//����Ϣ����
		this.getContentPane().add(workPane, BorderLayout.NORTH);
		//����һ����ʾdataTableStore�Ĺ�������ҳ������
	    this.getContentPane().add(new JScrollPane(this.dataTableAdd), BorderLayout.CENTER);
	  //��������������
	    this.dataTableAdd.addMouseListener(new MouseAdapter (){
			@Override
	    	//������ϵ�����갴ťʱ���ú���
			public void mouseClicked(MouseEvent e) {
				//������ѡ��һ�е�����
				int i=FrmMyAddress.this.dataTableAdd.getSelectedRow();
				//��û��ѡ�����򷵻�-1
				if(i<0) {
					return;
				}
				FrmMyAddress.this.curAdd=Add.get(i);
			}
	    });
	    this.reloadAddTable();
		this.setSize(950, 300);
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.addadd.addActionListener(this);
		this.updadd.addActionListener(this);
		this.deladd.addActionListener(this);
		this.btnok.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnok) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.addadd) {
			String sheng=this.Sheng.getText();
			String shi=this.Shi.getText();
			String qu=this.Qu.getText();
			String address=this.Address.getText();
			String name=this.Name.getText();
			String phnum=this.Phnum.getText();
			try {
				takeawayUtil.addressManager.addAddress(sheng, shi, qu, address, name, phnum);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.reloadAddTable();
		}
		else if(e.getSource()==this.updadd) {
			if(this.curAdd==null) {
				JOptionPane.showMessageDialog(null, "��ѡ���ַ", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			String sheng=this.Sheng.getText();
			String shi=this.Shi.getText();
			String qu=this.Qu.getText();
			String address=this.Address.getText();
			String name=this.Name.getText();
			String phnum=this.Phnum.getText();
			try {
				takeawayUtil.addressManager.updateAddress(this.curAdd, sheng, shi, qu, address, name, phnum);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.reloadAddTable();
		}
		else if(e.getSource()==this.deladd) {
			if(this.curAdd==null) {
				JOptionPane.showMessageDialog(null, "��ѡ���ַ", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.addressManager.deleteAddress(this.curAdd);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.reloadAddTable();
		}
	}
}
