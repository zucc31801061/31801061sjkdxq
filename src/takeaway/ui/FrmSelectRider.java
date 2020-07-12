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
import takeaway.model.BeanOrder;
import takeaway.model.BeanRider;
import takeaway.util.BaseException;

public class FrmSelectRider extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnok = new Button("ȷ��");
	private Button del = new Button("ע������");
	private Button updateriderid = new Button("�޸����ֳƺ�");
	private JPanel titleBar = new JPanel();
	private JLabel title = new JLabel("�����������˺ţ�");
	private JTextField name = new JTextField(20);
	private Button select = new Button("��ѯ");
	private JLabel title1 = new JLabel("                                                                                                               �������ˣ�                                            ");

	private Object tblselriderTitle[]=BeanRider.tableTitles1;
	private Object tblselriderData[][];
	DefaultTableModel tabselriderModel=new DefaultTableModel();
	private JTable dataTableselrider=new JTable(tabselriderModel);
	
	private Object tblqsrzTitle[]=BeanOrder.tableTitles9;
	private Object tblqsrzData[][];
	DefaultTableModel tabqsrzModel=new DefaultTableModel();
	private JTable dataTableqsrz=new JTable(tabqsrzModel);
	
	private BeanRider curselrider=null;
	List<BeanRider> selrider=null;
	List<BeanOrder> qsrz=null;
	private void reloadselrider(String name){
		try {
			selrider=takeawayUtil.riderManager.searchrider(name);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblselriderData = new Object[selrider.size()][BeanRider.tableTitles1.length];
		for(int i=0;i<selrider.size();i++){
			for(int j=0;j<BeanRider.tableTitles1.length;j++)
				tblselriderData[i][j]=selrider.get(i).getCell1(j);
		}
		tabselriderModel.setDataVector(tblselriderData,tblselriderTitle);
		this.dataTableselrider.validate();
		this.dataTableselrider.repaint();
	}
	private void reloadqsrzTabel(int selriderIdx){
		if(selriderIdx<0) return;
		curselrider=selrider.get(selriderIdx);
		try {
			qsrz=takeawayUtil.orderManager.selectridermoney(curselrider);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		//����һ����ά�����д�СΪqsrz.size()���д�СΪBeanOrder.tableTitles2.length
		tblqsrzData =new Object[qsrz.size()][BeanOrder.tableTitles9.length];
		for(int i=0;i<qsrz.size();i++){
			for(int j=0;j<BeanOrder.tableTitles9.length;j++)
				//�������ÿ��
				tblqsrzData[i][j]=qsrz.get(i).getCell9(j);
		}
		//��ʵ���е�ֵ�滻Ϊ�����е�ֵ��������ΪtblqsrzData��������ΪtableTitles2
		tabqsrzModel.setDataVector(tblqsrzData,tblqsrzTitle);
		this.dataTableqsrz.validate();
		this.dataTableqsrz.repaint();
	}
	public FrmSelectRider(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("��ѯ�û���");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(updateriderid);
		toolBar.add(del);
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		titleBar.add(title);
		titleBar.add(name);
		titleBar.add(select);
		titleBar.add(title1);
		this.getContentPane().add(titleBar, BorderLayout.NORTH);
		//����һ����ʾdataTableselrider�Ĺ�������ҳ������
	    this.getContentPane().add(new JScrollPane(this.dataTableselrider), BorderLayout.CENTER);
	    this.dataTableselrider.addMouseListener(new MouseAdapter (){
			@Override
			//������ϵ�����갴ťʱ���ú���
			public void mouseClicked(MouseEvent e) {
				//������ѡ��һ�е�����
				int i=FrmSelectRider.this.dataTableselrider.getSelectedRow();
				//��û��ѡ�����򷵻�-1
				if(i<0) {
					return;
				}
				//���ض�Ӧ��Ʒ�б�
				FrmSelectRider.this.reloadqsrzTabel(i);
			}
		});
	    this.getContentPane().add(new JScrollPane(this.dataTableqsrz), BorderLayout.EAST);
		this.setSize(1100, 300);
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.select.addActionListener(this);
		this.updateriderid.addActionListener(this);
		this.del.addActionListener(this);
		this.btnok.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnok) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.select) {
			String name=this.name.getText();
			this.reloadselrider(name);
		}
		else if(e.getSource()==this.updateriderid) {
			FrmUpdateRiderid ur=new FrmUpdateRiderid(this, "�޸����ֳƺ�", true);
			ur.setVisible(true);
		}
		else if(e.getSource()==this.del) {if(this.curselrider==null) {
				JOptionPane.showMessageDialog(null, "��ѡ������", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.riderManager.deleteRider(this.curselrider);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		}
	}
}
