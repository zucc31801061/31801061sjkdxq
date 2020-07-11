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
import takeaway.model.BeanOrder;
import takeaway.util.BaseException;

public class FrmMyRiderpj extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button goodpj = new Button("����");
	private Button badpj = new Button("����");
	private Button delpj = new Button("ɾ������");
	private Button btnok = new Button("ȷ��");
	//��ַ�б�
	//�������
	private Object tblpjTitle[]=BeanOrder.tableTitles5;
	//��ά��洢
	private Object tblpjData[][];
	//�������ģ��
	DefaultTableModel tabpjModel=new DefaultTableModel();
	//��tabpjModelΪģ�͹�����
	private JTable dataTablepj=new JTable(tabpjModel);
	
	BeanOrder curpj;
	List<BeanOrder> pj=null;
	private void reloadpjTable(){//���ǲ������ݣ���Ҫ��ʵ�����滻
		try {
			pj=takeawayUtil.orderManager.loaduserpj();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblpjData = new Object[pj.size()][BeanOrder.tableTitles5.length];
		for(int i=0;i<pj.size();i++){
			for(int j=0;j<BeanOrder.tableTitles5.length;j++)
				tblpjData[i][j]=pj.get(i).getCell5(j);
		}
		tabpjModel.setDataVector(tblpjData,tblpjTitle);
		this.dataTablepj.validate();
		//��֤�������������
		this.dataTablepj.repaint();
		//�ػ�����
	}
	public FrmMyRiderpj(JFrame f, String s, boolean b) {
		super(f, s, b);
		//���ô��ڱ���
		this.setTitle("��������");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(goodpj);
		toolBar.add(badpj);
		toolBar.add(delpj);
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		//����һ����ʾdataTableStore�Ĺ�������ҳ������
	    this.getContentPane().add(new JScrollPane(this.dataTablepj), BorderLayout.WEST);
	  //��������������
	    this.dataTablepj.addMouseListener(new MouseAdapter (){
			@Override
	    	//������ϵ�����갴ťʱ���ú���
			public void mouseClicked(MouseEvent e) {
				//������ѡ��һ�е�����
				int i=FrmMyRiderpj.this.dataTablepj.getSelectedRow();
				//��û��ѡ�����򷵻�-1
				if(i<0) {
					return;
				}
				curpj=pj.get(i);
			}
	    });
	    this.reloadpjTable();
		this.setSize(460, 300);
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.goodpj.addActionListener(this);
		this.badpj.addActionListener(this);
		this.delpj.addActionListener(this);
		this.btnok.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnok) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.goodpj) {
			if(this.curpj==null) {
				JOptionPane.showMessageDialog(null, "��ѡ�񶩵�", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.orderManager.updhppj(this.curpj);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		}
		else if(e.getSource()==this.badpj) {
			if(this.curpj==null) {
				JOptionPane.showMessageDialog(null, "��ѡ�񶩵�", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.orderManager.updcppj(this.curpj);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		}
		else if(e.getSource()==this.delpj) {
			if(this.curpj==null) {
				JOptionPane.showMessageDialog(null, "��ѡ�񶩵�", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.orderManager.delpj(this.curpj);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		}
	}
}
