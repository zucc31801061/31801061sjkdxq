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
import javax.swing.table.DefaultTableModel;

import takeaway.takeawayUtil;
import takeaway.model.BeanOrderInfo;
import takeaway.model.BeanSppj;
import takeaway.util.BaseException;

public class FrmRqtj extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnok = new Button("ȷ��");
	
	private JPanel titleBar = new JPanel();
	private JLabel title = new JLabel("                   ������Ʒ��                                                                                                                                                         ��Ʒ���ۣ�                             ");
	
	private Object tblrqproTitle[]=BeanOrderInfo.tblOrderInfoTitle1;
	private Object tblrqproData[][];
	DefaultTableModel tabrqproModel=new DefaultTableModel();
	private JTable dataTablerqpro=new JTable(tabrqproModel);
	
	private Object tblpropjTitle[]=BeanSppj.tableTitles2;
	private Object tblpropjData[][];
	DefaultTableModel tabpropjModel=new DefaultTableModel();
	private JTable dataTablepropj=new JTable(tabpropjModel);
	
	private BeanOrderInfo currqpro=null;
	List<BeanOrderInfo> rqpro=null;
	List<BeanSppj> propj=null;
	private void reloadrqpro(){
		try {
			rqpro=takeawayUtil.orderinfoManager.loadretj();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblrqproData = new Object[rqpro.size()][BeanOrderInfo.tblOrderInfoTitle1.length];
		for(int i=0;i<rqpro.size();i++){
			for(int j=0;j<BeanOrderInfo.tblOrderInfoTitle1.length;j++)
				tblrqproData[i][j]=rqpro.get(i).getCell1(j);
		}
		tabrqproModel.setDataVector(tblrqproData,tblrqproTitle);
		this.dataTablerqpro.validate();
		this.dataTablerqpro.repaint();
	}
	private void reloadpropjTabel(int rqproIdx){
		if(rqproIdx<0) return;
		currqpro=rqpro.get(rqproIdx);
		try {
			propj=takeawayUtil.sppjManager.loadpropj(currqpro);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		//����һ����ά�����д�СΪpropj.size()���д�СΪBeanSppj.tableTitles2.length
		tblpropjData =new Object[propj.size()][BeanSppj.tableTitles2.length];
		for(int i=0;i<propj.size();i++){
			for(int j=0;j<BeanSppj.tableTitles2.length;j++)
				//�������ÿ��
				tblpropjData[i][j]=propj.get(i).getCell2(j);
		}
		//��ʵ���е�ֵ�滻Ϊ�����е�ֵ��������ΪtblpropjData��������ΪtableTitles2
		tabpropjModel.setDataVector(tblpropjData,tblpropjTitle);
		this.dataTablepropj.validate();
		this.dataTablepropj.repaint();
	}
	public FrmRqtj(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("��ѯ��Ʒ");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		titleBar.add(title);
		this.getContentPane().add(titleBar, BorderLayout.NORTH);
		//����һ����ʾdataTablerqpro�Ĺ�������ҳ������
	    this.getContentPane().add(new JScrollPane(this.dataTablerqpro), BorderLayout.WEST);
	    this.dataTablerqpro.addMouseListener(new MouseAdapter (){
			@Override
			//������ϵ�����갴ťʱ���ú���
			public void mouseClicked(MouseEvent e) {
				//������ѡ��һ�е�����
				int i=FrmRqtj.this.dataTablerqpro.getSelectedRow();
				//��û��ѡ�����򷵻�-1
				if(i<0) {
					return;
				}
				//���ض�Ӧ��Ʒ�б�
				FrmRqtj.this.reloadpropjTabel(i);
			}
		});
	    this.reloadrqpro();
	    this.getContentPane().add(new JScrollPane(this.dataTablepropj), BorderLayout.CENTER);
		this.setSize(1100, 300);
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnok.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnok) {
			this.setVisible(false);
			return;
		}
	}
}
