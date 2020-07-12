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
import takeaway.model.BeanControl;
import takeaway.util.BaseException;

public class FrmGlyInfo extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button updateinfo = new Button("�޸���Ϣ");
	private Button btnok = new Button("ȷ��");
	//��Ϣ�б�
	//�������
	private Object tblyginfoTitle[]=BeanControl.tableTitles;
	//��ά��洢
	private Object tblyginfoData[][];
	//�������ģ��
	DefaultTableModel tabyginfoModel=new DefaultTableModel();
	//��tabyginfoModelΪģ�͹�����
	private JTable dataTableyginfo=new JTable(tabyginfoModel);
	
	BeanControl yginfo=null;
	private void reloadyginfoTable(){//���ǲ������ݣ���Ҫ��ʵ�����滻
		try {
			//��ѯ��ǰyginfo
			yginfo=takeawayUtil.controlManager.SearchInfo();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblyginfoData = new Object[1][BeanControl.tableTitles.length];
		for(int i=0;i<1;i++){
			for(int j=0;j<BeanControl.tableTitles.length;j++)
				tblyginfoData[i][j]=yginfo.getCell(j);
		}
		tabyginfoModel.setDataVector(tblyginfoData,tblyginfoTitle);
		this.dataTableyginfo.validate();
		//��֤�������������
		this.dataTableyginfo.repaint();
		//�ػ�����
	}
	public FrmGlyInfo(JFrame f, String s, boolean b) {
		super(f, s, b);
		//���ô��ڱ���
		this.setTitle("�ҵ���Ϣ");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(updateinfo);
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		//����һ����ʾdataTableyginfo�Ĺ�������ҳ������
	    this.getContentPane().add(new JScrollPane(this.dataTableyginfo), BorderLayout.CENTER);
		this.reloadyginfoTable();
		this.setSize(250, 150);
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.updateinfo.addActionListener(this);
		this.btnok.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnok) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.updateinfo){
			FrmUpdateGly ug=new FrmUpdateGly(this,"�޸���Ϣ",true);
			ug.setVisible(true);
		}
	}
}
