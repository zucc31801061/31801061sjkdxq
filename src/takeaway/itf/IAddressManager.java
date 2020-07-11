package takeaway.itf;

import java.util.List;

import takeaway.model.BeanAddress;
import takeaway.model.BeanOrder;
import takeaway.util.BaseException;

public interface IAddressManager {
	/**
	 * 添加地址
	 * 注意：当前登陆用户可通过 BeanUser.currentLoginUser获取
	 * @throws BaseException
	 */
	public BeanAddress addAddress(String sheng,String shi,String qu,String address,String name,String phnum) throws BaseException;
	/**
	 * 提取所有地址
	 * @return
	 * @throws BaseException
	 */
	public List<BeanAddress> loadAll()throws BaseException;
	/**
	 * 删除地址
	 * @param address
	 * @throws BaseException
	 */
	public void deleteAddress(BeanAddress address)throws BaseException;
	/**
	 * 加载对应订单的地址信息
	 * @param address
	 * @throws BaseException
	 */
	public List<BeanAddress> loadselect(BeanOrder order)throws BaseException;
}
