package takeaway.itf;

import java.util.List;

import takeaway.model.BeanStore;
import takeaway.model.BeanUser;
import takeaway.util.BaseException;

public interface IStoreManager {
	/**
	 * 添加商家
	 * 注意：当前登陆用户可通过 BeanUser.currentLoginUser获取
	 * @param name  计划名称
	 * @throws BaseException
	 */
	public BeanStore addStore(String name) throws BaseException;
	/**
	 *商家登录
	 */
	public BeanStore login() throws BaseException;
	/**
	 * 提取所有商家
	 * @return
	 * @throws BaseException
	 */
	public List<BeanStore> loadAll()throws BaseException;
	/**
	 * 删除商家
	 * @param plan
	 * @throws BaseException
	 */
	public void deleteStore(BeanStore plan)throws BaseException;

}
