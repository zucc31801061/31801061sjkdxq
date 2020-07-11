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
import takeaway.model.BeanSppj;
import takeaway.util.BaseException;

public class FrmMyStorepj extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button addpj = new Button("�޸�����");
	private Button delpj = new Button("ɾ������");
	private Button btnok = new Button("ȷ��");
	
	private JPanel workPane = new JPanel();
	private JLabel Newpjnr = new JLabel("�������ݣ�");
	private JLabel Newphoto = new JLabel("ͼƬ(true/false)��");
	private JLabel Newpjstar = new JLabel("�Ǽ���");
	private JTextField pjnr = new JTextField(13);
	private JTextField photo= new JTextField(13);
	private JTextField pjstar = new JTextField(13);
	
	//��ַ�б�
	//�������
	private Object tblpjTitle[]=BeanSppj.tableTitles;
	//��ά��洢
	private Object tblpjData[][];
	//�������ģ��
	DefaultTableModel tabpjModel=new DefaultTableModel();
	//��tabpjModelΪģ�͹�����
	private JTable dataTablepj=new JTable(tabpjModel);
	
	BeanSppj curpj;
	List<BeanSppj> pj=null;
	private void reloadpjTable(){//���ǲ������ݣ���Ҫ��ʵ�����滻
		try {
			pj=takeawayUtil.sppjManager.loadbyuser();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblpjData = new Object[pj.size()][BeanSppj.tableTitles.length];
		for(int i=0;i<pj.size();i++){
			for(int j=0;j<BeanSppj.tableTitles.length;j++)
				tblpjData[i][j]=pj.get(i).getCell(j);
		}
		tabpjModel.setDataVector(tblpjData,tblpjTitle);
		this.dataTablepj.validate();
		//��֤�������������
		this.dataTablepj.repaint();
		//�ػ�����
	}
	public FrmMyStorepj(JFrame f, String s, boolean b) {
		super(f, s, b);
		//���ô��ڱ���
		this.setTitle("�̼�����");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(addpj);
		toolBar.add(delpj);
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(Newpjnr);
		workPane.add(pjnr);
		workPane.add(Newphoto);
		workPane.add(photo);
		workPane.add(Newpjstar);
		workPane.add(pjstar);
		//����Ϣ����
		this.getContentPane().add(workPane, BorderLayout.NORTH);
		//����һ����ʾdataTableStore�Ĺ�������ҳ������
	    this.getContentPane().add(new JScrollPane(this.dataTablepj), BorderLayout.CENTER);
	  //��������������
	    this.dataTablepj.addMouseListener(new MouseAdapter (){
			@Override
	    	//������ϵ�����갴ťʱ���ú���
			public void mouseClicked(MouseEvent e) {
				//������ѡ��һ�е�����
				int i=FrmMyStorepj.this.dataTablepj.getSelectedRow();
				//��û��ѡ�����򷵻�-1
				if(i<0) {
					return;
				}
				curpj=pj.get(i);
			}
	    });
	    this.reloadpjTable();
		this.setSize(700, 250);
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.addpj.addActionListener(this);
		this.delpj.addActionListener(this);
		this.btnok.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnok) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.addpj) {
			if(this.curpj==null) {
				JOptionPane.showMessageDialog(null, "��ѡ�񶩵�", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			String pjnr=this.pjnr.getText();
			boolean photo=Boolean.parseBoolean(this.photo.getText());
			int pjstar=Integer.parseInt(this.pjstar.getText());
			try {
				takeawayUtil.sppjManager.addSppj(this.curpj,pjnr, pjstar, photo);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.delpj) {
			if(this.curpj==null) {
				JOptionPane.showMessageDialog(null, "��ѡ������", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.sppjManager.deleteSppj(this.curpj);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		}
	}
}
