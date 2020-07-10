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
import takeaway.model.BeanMjMethod;
import takeaway.util.BaseException;

public class FrmMymj extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button addmethod = new Button("���ӷ���");
	private Button delmethod = new Button("ɾ������");
	private Button btnok = new Button("ȷ��");
	//��ַ�б�
	//�������
	private Object tblMethodTitle[]=BeanMjMethod.tblMjTitle;
	//��ά���洢
	private Object tblMethodData[][];
	//��������ģ��
	DefaultTableModel tabMethodModel=new DefaultTableModel();
	//��tabMethodModelΪģ�͹������
	private JTable dataTableMethod=new JTable(tabMethodModel);
	
	BeanMjMethod curMethod;
	List<BeanMjMethod> Method=null;
	private void reloadMethodTable(){//���ǲ������ݣ���Ҫ��ʵ�����滻
		try {
			//��ѯ��ǰMethod
			Method=takeawayUtil.mjManager.loadAll();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblMethodData = new Object[Method.size()][BeanMjMethod.tblMjTitle.length];
		for(int i=0;i<Method.size();i++){
			for(int j=0;j<BeanMjMethod.tblMjTitle.length;j++)
				tblMethodData[i][j]=Method.get(i).getCell(j);
		}
		tabMethodModel.setDataVector(tblMethodData,tblMethodTitle);
		this.dataTableMethod.validate();
		//��֤�������������
		this.dataTableMethod.repaint();
		//�ػ�����
	}
	public FrmMymj(JFrame f, String s, boolean b) {
		super(f, s, b);
		//���ô��ڱ���
		this.setTitle("�ҵ���Ʒ");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(addmethod);
		toolBar.add(delmethod);
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		//����һ����ʾdataTableStore�Ĺ�������ҳ������
	    this.getContentPane().add(new JScrollPane(this.dataTableMethod), BorderLayout.WEST);
	  //���������������
	    this.dataTableMethod.addMouseListener(new MouseAdapter (){
			@Override
	    	//������ϵ�����갴ťʱ���ú���
			public void mouseClicked(MouseEvent e) {
				//������ѡ��һ�е�����
				int i=FrmMymj.this.dataTableMethod.getSelectedRow();
				//��û��ѡ�����򷵻�-1
				if(i<0) {
					return;
				}
				curMethod=Method.get(i);
			}
	    });
	    this.reloadMethodTable();
		this.setSize(460, 250);
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.addmethod.addActionListener(this);
		this.delmethod.addActionListener(this);
		this.btnok.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnok) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.addmethod) {
			FrmAddMj am=new FrmAddMj(this, "���ӷ���", true);
			am.setVisible(true);
		}
		else if(e.getSource()==this.delmethod) {
			if(this.curMethod==null) {
				JOptionPane.showMessageDialog(null, "��ѡ�񷽰�", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.mjManager.Delmj(this.curMethod);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		}
	}
}