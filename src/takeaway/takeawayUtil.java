package takeaway;

import takeaway.control.AddressManager;
import takeaway.control.ControlManager;
import takeaway.control.OrderInfoManager;
import takeaway.control.OrderManager;
import takeaway.control.StoreManager;
import takeaway.control.ProductManager;
import takeaway.control.RiderManager;
import takeaway.control.UserManager;
import takeaway.control.YhInfoManager;
import takeaway.itf.IAddressManager;
import takeaway.itf.IControlManager;
import takeaway.itf.IOrderInfoManager;
import takeaway.itf.IOrderManager;
import takeaway.itf.IStoreManager;
import takeaway.itf.IProductManager;
import takeaway.itf.IRiderManager;
import takeaway.itf.IUserManager;
import takeaway.itf.IYhInfoManager;

public class takeawayUtil {
	public static IStoreManager storeManager=new StoreManager();//需要换成自行设计的实现类
	public static IProductManager productManager=new ProductManager();//需要换成自行设计的实现类
	public static IUserManager userManager=new UserManager();//需要换成自行设计的实现类
	public static IControlManager controlManager=new ControlManager();//需要换成自行设计的实现类
	public static IOrderManager orderManager=new OrderManager();//需要换成自行设计的实现类
	public static IOrderInfoManager orderinfoManager=new OrderInfoManager();//需要换成自行设计的实现类
	public static IAddressManager addressManager=new AddressManager();//需要换成自行设计的实现类
	public static IRiderManager riderManager=new RiderManager();//需要换成自行设计的实现类
	public static IYhInfoManager yhManager=new YhInfoManager();//需要换成自行设计的实现类
}
