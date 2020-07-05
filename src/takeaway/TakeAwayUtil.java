package takeaway;

import takeaway.control.example.ControlManager;
import takeaway.control.example.PlanManager;
import takeaway.control.example.StepManager;
import takeaway.control.example.UserManager;
import takeaway.itf.IControlManager;
import takeaway.itf.IPlanManager;
import takeaway.itf.IStepManager;
import takeaway.itf.IUserManager;

public class TakeAwayUtil {
	public static IPlanManager planManager=new PlanManager();//��Ҫ����������Ƶ�ʵ����
	public static IStepManager stepManager=new StepManager();//��Ҫ����������Ƶ�ʵ����
	public static IUserManager userManager=new UserManager();//��Ҫ����������Ƶ�ʵ����
	public static IControlManager controlManager=new ControlManager();//��Ҫ����������Ƶ�ʵ����
}
