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

public class FrmUserInfo extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button updateinfo = new Button("�޸���Ϣ");
	private Button btnok = new Button("ȷ��");
	//��Ϣ�б�
	//�������
	private Object tblUserInfoTitle[]=BeanUser.tableTitles;
	//��ά��洢
	private Object tblUserInfoData[][];
	//�������ģ��
	DefaultTableModel tabUserInfoModel=new DefaultTableModel();
	//��tabUserInfoModelΪģ�͹�����
	private JTable dataTableUserInfo=new JTable(tabUserInfoModel);
	
	BeanUser UserInfo=null;
	private void reloadUserInfoTable(){//���ǲ������ݣ���Ҫ��ʵ�����滻
		try {
			//��ѯ��ǰUserInfo
			UserInfo=takeawayUtil.userManager.SearchInfo();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblUserInfoData = new Object[1][BeanUser.tableTitles.length];
		for(int i=0;i<1;i++){
			for(int j=0;j<BeanUser.tableTitles.length;j++)
				tblUserInfoData[i][j]=UserInfo.getCell(j);
		}
		tabUserInfoModel.setDataVector(tblUserInfoData,tblUserInfoTitle);
		this.dataTableUserInfo.validate();
		//��֤�������������
		this.dataTableUserInfo.repaint();
		//�ػ�����
	}
	public FrmUserInfo(JFrame f, String s, boolean b) {
		super(f, s, b);
		//���ô��ڱ���
		this.setTitle("�ҵ���Ϣ");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(updateinfo);
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		//����һ����ʾdataTableUserInfo�Ĺ�������ҳ������
	    this.getContentPane().add(new JScrollPane(this.dataTableUserInfo), BorderLayout.WEST);
		this.reloadUserInfoTable();
		this.setSize(460, 150);
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
			FrmUpdateInfo uf=new FrmUpdateInfo(this,"�޸���Ϣ",true);
			uf.setVisible(true);
			this.reloadUserInfoTable();
		}
	}
}
