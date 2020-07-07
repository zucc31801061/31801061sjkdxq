package takeaway;

import takeaway.control.ControlManager;
import takeaway.control.StoreManager;
import takeaway.control.ProductManager;
import takeaway.control.UserManager;
import takeaway.itf.IControlManager;
import takeaway.itf.IStoreManager;
import takeaway.itf.IProductManager;
import takeaway.itf.IUserManager;

public class takeawayUtil {
	public static IStoreManager planManager=new StoreManager();//需要换成自行设计的实现类
	public static IProductManager stepManager=new ProductManager();//需要换成自行设计的实现类
	public static IUserManager userManager=new UserManager();//需要换成自行设计的实现类
	public static IControlManager controlManager=new ControlManager();//需要换成自行设计的实现类
	
}
