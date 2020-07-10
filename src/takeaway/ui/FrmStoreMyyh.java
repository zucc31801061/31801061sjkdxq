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
import takeaway.model.BeanYhInfo;
import takeaway.util.BaseException;

public class FrmStoreMyyh extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button addyh = new Button("增加优惠券");
	private Button delyh = new Button("删除优惠券");
	private Button btnok = new Button("确定");
	private Object tblyhTitle[]=BeanYhInfo.tableTitles3;
	private Object tblyhData[][];
	DefaultTableModel tabyhModel=new DefaultTableModel();
	private JTable dataTableyh=new JTable(tabyhModel);
	
	BeanYhInfo curyh;
	List<BeanYhInfo> yh=null;
	private void reloadyhTable(){
		try {
			yh=takeawayUtil.yhManager.loadbystore();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblyhData = new Object[yh.size()][BeanYhInfo.tableTitles3.length];
		for(int i=0;i<yh.size();i++){
			for(int j=0;j<BeanYhInfo.tableTitles3.length;j++)
				tblyhData[i][j]=yh.get(i).getCell3(j);
		}
		tabyhModel.setDataVector(tblyhData,tblyhTitle);
		this.dataTableyh.validate();
		this.dataTableyh.repaint();
	}
	public FrmStoreMyyh(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("我的优惠券");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(addyh);
		toolBar.add(delyh);
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
	    this.getContentPane().add(new JScrollPane(this.dataTableyh), BorderLayout.WEST);
	    this.dataTableyh.addMouseListener(new MouseAdapter (){
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=FrmStoreMyyh.this.dataTableyh.getSelectedRow();
				if(i<0) {
					return;
				}
				curyh=yh.get(i);
			}
	    });
	    this.reloadyhTable();
		this.setSize(460, 250);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.addyh.addActionListener(this);
		this.delyh.addActionListener(this);
		this.btnok.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnok) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.addyh) {
			FrmAddYh ay=new FrmAddYh(this, "增加优惠券", true);
			ay.setVisible(true);
		}
		else if(e.getSource()==this.delyh) {
			if(this.curyh==null) {
				JOptionPane.showMessageDialog(null, "请选择优惠券", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.yhManager.Delyh(this.curyh);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		}
	}
}
