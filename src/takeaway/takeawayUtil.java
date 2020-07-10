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
	public static IStoreManager storeManager=new StoreManager();//��Ҫ����������Ƶ�ʵ����
	public static IProductManager productManager=new ProductManager();//��Ҫ����������Ƶ�ʵ����
	public static IUserManager userManager=new UserManager();//��Ҫ����������Ƶ�ʵ����
	public static IControlManager controlManager=new ControlManager();//��Ҫ����������Ƶ�ʵ����
	public static IOrderManager orderManager=new OrderManager();//��Ҫ����������Ƶ�ʵ����
	public static IOrderInfoManager orderinfoManager=new OrderInfoManager();//��Ҫ����������Ƶ�ʵ����
	public static IAddressManager addressManager=new AddressManager();//��Ҫ����������Ƶ�ʵ����
	public static IRiderManager riderManager=new RiderManager();//��Ҫ����������Ƶ�ʵ����
	public static IYhInfoManager yhManager=new YhInfoManager();//��Ҫ����������Ƶ�ʵ����
}
