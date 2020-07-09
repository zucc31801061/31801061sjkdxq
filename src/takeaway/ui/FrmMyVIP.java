package takeaway.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import takeaway.takeawayUtil;
import takeaway.model.BeanUser;
import takeaway.util.BaseException;

public class FrmMyVIP  extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button pay = new Button("��Ҫ��ֵ");
	private Button btnok = new Button("ȷ��");
	//��Ϣ�б�
	//�������
	private Object tblVIPTitle[]=BeanUser.tableTitles1;
	//��ά��洢
	private Object tblVIPData[][];
	//�������ģ��
	DefaultTableModel tabVIPModel=new DefaultTableModel();
	//��tabMyVIPModelΪģ�͹�����
	private JTable dataTableVIP=new JTable(tabVIPModel);
	
	BeanUser VIP=null;
	private void reloadMyVIPTable(){//���ǲ������ݣ���Ҫ��ʵ�����滻
		try {
			//��ѯ��ǰMyVIP
			VIP=takeawayUtil.userManager.SearchVIP();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblVIPData = new Object[1][BeanUser.tableTitles1.length];
		for(int i=0;i<1;i++){
			for(int j=0;j<BeanUser.tableTitles1.length;j++)
				tblVIPData[i][j]=VIP.getCell1(j);
		}
		tabVIPModel.setDataVector(tblVIPData,tblVIPTitle);
		this.dataTableVIP.validate();
		//��֤�������������
		this.dataTableVIP.repaint();
		//�ػ�����
	}
	public FrmMyVIP(JFrame f, String s, boolean b) {
		super(f, s, b);
		//���ô��ڱ���
		this.setTitle("�ҵĻ�Ա");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(pay);
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		//����һ����ʾdataTableMyVIP�Ĺ�������ҳ������
	    this.getContentPane().add(new JScrollPane(this.dataTableVIP), BorderLayout.WEST);
		this.reloadMyVIPTable();
		this.setSize(460, 150);
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.pay.addActionListener(this);
		this.btnok.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnok) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.pay){
			try {
				BeanUser user=takeawayUtil.userManager.PayVIP();
				this.setVisible(false);
			}catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}
}
