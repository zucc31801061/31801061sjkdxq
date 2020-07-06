package takeaway;

import takeaway.control.ProductManager;
import takeaway.control.UserManager;
import takeaway.control.ControlManager;
import takeaway.control.StoreManager;
import takeaway.itf.IControlManager;
import takeaway.itf.IStoreManager;
import takeaway.itf.IProductManager;
import takeaway.itf.IUserManager;

public class takeawayUtil {
	public static IStoreManager storeManager=new StoreManager();//��Ҫ����������Ƶ�ʵ����
	public static IProductManager productManager=new ProductManager();//��Ҫ����������Ƶ�ʵ����
	public static IUserManager userManager=new UserManager();//��Ҫ����������Ƶ�ʵ����
	public static IControlManager controlManager=new ControlManager();//��Ҫ����������Ƶ�ʵ����
}
