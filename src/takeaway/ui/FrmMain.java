package takeaway.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import takeaway.TakeAwayUtil;
import takeaway.model.BeanPlan;
import takeaway.model.BeanStep;
import takeaway.util.BaseException;



public class FrmMain extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_order=new JMenu("��Ҫ�µ�");
    private JMenu menu_myorder=new JMenu("�ҵĶ���");
    private JMenu menu_function=new JMenu("�ҵĹ���");
    private JMenu menu_more=new JMenu("����");
    
    private JMenuItem  menuItem_searchstore=new JMenuItem("�鿴�̼�");
    private JMenuItem  menuItem_searchproduct=new JMenuItem("�鿴��Ʒ");
    
    private JMenuItem  menuItem_car=new JMenuItem("���ﳵ");
    private JMenuItem  menuItem_history=new JMenuItem("��ʷ����");
    private JMenuItem  menuItem_ing=new JMenuItem("��������");
    private JMenuItem  menuItem_unpaid=new JMenuItem("��δ֧��");
    
    private JMenuItem  menuItem_discount=new JMenuItem("�ҵ��Ż�ȯ");
    private JMenuItem  menuItem_reception=new JMenuItem("�ҵ�����");
    private JMenuItem  menuItem_info=new JMenuItem("�ҵ���Ϣ");
    private JMenuItem  menuItem_address=new JMenuItem("�ҵĵ�ַ");
    
    private JMenuItem  menuItem_vip=new JMenuItem("�ҵĻ�Ա");
    private JMenuItem  menuItem_store=new JMenuItem("��Ϊ�̼�");
    private JMenuItem  menuItem_rider=new JMenuItem("��Ϊ����");
    private JMenuItem  menuItem_modifyPwd=new JMenuItem("�޸�����");
    private JMenuItem  menuItem_logout=new JMenuItem("ע���û�");
    
	private FrmLogin dlgLogin=null;
	private JPanel statusBar = new JPanel();
	
	private Object tblPlanTitle[]=BeanPlan.tableTitles;
	private Object tblPlanData[][];
	DefaultTableModel tabPlanModel=new DefaultTableModel();
	private JTable dataTablePlan=new JTable(tabPlanModel);
	
	private Object tblStepTitle[]=BeanStep.tblStepTitle;
	private Object tblStepData[][];
	DefaultTableModel tabStepModel=new DefaultTableModel();
	private JTable dataTableStep=new JTable(tabStepModel);
	
	private BeanPlan curPlan=null;
	List<BeanPlan> allPlan=null;
	List<BeanStep> planSteps=null;
	private void reloadPlanTable(){//���ǲ������ݣ���Ҫ��ʵ�����滻
		try {
			allPlan=TakeAwayUtil.planManager.loadAll();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblPlanData =  new Object[allPlan.size()][BeanPlan.tableTitles.length];
		for(int i=0;i<allPlan.size();i++){
			for(int j=0;j<BeanPlan.tableTitles.length;j++)
				tblPlanData[i][j]=allPlan.get(i).getCell(j);
		}
		tabPlanModel.setDataVector(tblPlanData,tblPlanTitle);
		this.dataTablePlan.validate();
		this.dataTablePlan.repaint();
	}
	private void reloadPlanStepTabel(int planIdx){
		if(planIdx<0) return;
		curPlan=allPlan.get(planIdx);
		try {
			planSteps=TakeAwayUtil.stepManager.loadSteps(curPlan);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblStepData =new Object[planSteps.size()][BeanStep.tblStepTitle.length];
		for(int i=0;i<planSteps.size();i++){
			for(int j=0;j<BeanStep.tblStepTitle.length;j++)
				tblStepData[i][j]=planSteps.get(i).getCell(j);
		}
		
		tabStepModel.setDataVector(tblStepData,tblStepTitle);
		this.dataTableStep.validate();
		this.dataTableStep.repaint();
	}
	public FrmMain(){
		
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setTitle("��������");
		dlgLogin=new FrmLogin(this,"��½",true);
		dlgLogin.setVisible(true);
	    //�˵�
		this.menu_order.add(this.menuItem_searchstore); this.menuItem_searchstore.addActionListener(this);
	    this.menu_order.add(this.menuItem_searchproduct); this.menuItem_searchproduct.addActionListener(this);
		
	    this.menu_myorder.add(this.menuItem_car); this.menuItem_car.addActionListener(this);
	    this.menu_myorder.add(this.menuItem_history); this.menuItem_history.addActionListener(this);
	    this.menu_myorder.add(this.menuItem_ing); this.menuItem_ing.addActionListener(this);
	    this.menu_myorder.add(this.menuItem_unpaid); this.menuItem_unpaid.addActionListener(this);
	    
	    this.menu_function.add(this.menuItem_discount); this.menuItem_discount.addActionListener(this);
	    this.menu_function.add(this.menuItem_reception); this.menuItem_reception.addActionListener(this);
	    this.menu_function.add(this.menuItem_info); this.menuItem_info.addActionListener(this);
	    this.menu_function.add(this.menuItem_address); this.menuItem_address.addActionListener(this);
	    
	    this.menu_more.add(this.menuItem_vip); this.menuItem_vip.addActionListener(this);
	    this.menu_more.add(this.menuItem_store); this.menuItem_store.addActionListener(this);
	    this.menu_more.add(this.menuItem_rider); this.menuItem_rider.addActionListener(this);
	    this.menu_more.add(this.menuItem_modifyPwd); this.menuItem_modifyPwd.addActionListener(this);
	    this.menu_more.add(this.menuItem_logout); this.menuItem_logout.addActionListener(this);
	    
	    menubar.add(menu_order);
	    menubar.add(menu_myorder);
	    menubar.add(menu_function);
	    menubar.add(menu_more);
	    this.setJMenuBar(menubar);
	    
	    this.getContentPane().add(new JScrollPane(this.dataTablePlan), BorderLayout.WEST);
	    this.dataTablePlan.addMouseListener(new MouseAdapter (){

			@Override
			public void mouseClicked(MouseEvent e) {
				int i=FrmMain.this.dataTablePlan.getSelectedRow();
				if(i<0) {
					return;
				}
				FrmMain.this.reloadPlanStepTabel(i);
			}
	    	
	    });
	    this.getContentPane().add(new JScrollPane(this.dataTableStep), BorderLayout.CENTER);
	    
	    this.reloadPlanTable();
	    //״̬��
	    statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
	    JLabel label=new JLabel("����!");//�޸ĳ�   ���ã�+��½�û���
	    statusBar.add(label);
	    this.getContentPane().add(statusBar,BorderLayout.SOUTH);
	    this.addWindowListener(new WindowAdapter(){   
	    	public void windowClosing(WindowEvent e){ 
	    		System.exit(0);
             }
        });
	    this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.menuItem_searchstore){
			FrmAddPlan dlg=new FrmAddPlan(this,"�鿴�̼�",true);
			dlg.setVisible(true);
		}
		/*else if(e.getSource()==this.menuItem_DeletePlan){
			if(this.curPlan==null) {
				JOptionPane.showMessageDialog(null, "��ѡ��ƻ�", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				TakeAwayUtil.planManager.deletePlan(this.curPlan);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.menuItem_AddStep){
			FrmAddStep dlg=new FrmAddStep(this,"��Ӳ���",true);
			dlg.plan=curPlan;
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_DeleteStep){
			int i=FrmMain.this.dataTableStep.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "��ѡ����", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				TakeAwayUtil.stepManager.deleteStep(this.planSteps.get(i));
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.menuItem_startStep){
			int i=FrmMain.this.dataTableStep.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "��ѡ����", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				TakeAwayUtil.stepManager.startStep(this.planSteps.get(i));
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.menuItem_finishStep){
			int i=FrmMain.this.dataTableStep.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "��ѡ����", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				TakeAwayUtil.stepManager.finishStep(this.planSteps.get(i));
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.menuItem_moveUpStep){
			int i=FrmMain.this.dataTableStep.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "��ѡ����", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				TakeAwayUtil.stepManager.moveUp(this.planSteps.get(i));
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.menuItem_moveDownStep){
			int i=FrmMain.this.dataTableStep.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "��ѡ����", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				TakeAwayUtil.stepManager.moveDown(this.planSteps.get(i));
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.menuItem_static1){
			
		}*/
		else if(e.getSource()==this.menuItem_modifyPwd){
			FrmModifyPwd dlg=new FrmModifyPwd(this,"�޸�����",true);
			dlg.setVisible(true);
		}
	}
}
