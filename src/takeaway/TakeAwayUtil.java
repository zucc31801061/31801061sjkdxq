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
	public static IPlanManager planManager=new PlanManager();//需要换成自行设计的实现类
	public static IStepManager stepManager=new StepManager();//需要换成自行设计的实现类
	public static IUserManager userManager=new UserManager();//需要换成自行设计的实现类
	public static IControlManager controlManager=new ControlManager();//需要换成自行设计的实现类
}
