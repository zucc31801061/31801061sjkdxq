package takeaway.itf;

import java.util.List;

import takeaway.model.BeanAddress;
import takeaway.util.BaseException;

public interface IAddressManager {
	/**
	 * 添加地址
	 * 注意：当前登陆用户可通过 BeanUser.currentLoginUser获取
	 * @throws BaseException
	 */
	public BeanAddress addAddress(String sheng,String shi,String qu,String address,String name,String phnum,String addno) throws BaseException;
	/**
	 * 提取所有商家
	 * @return
	 * @throws BaseException
	 */
	public List<BeanAddress> loadAll()throws BaseException;
	/**
	 * 删除商家
	 * @param address
	 * @throws BaseException
	 */
	public void deleteAddress(BeanAddress address)throws BaseException;
}
