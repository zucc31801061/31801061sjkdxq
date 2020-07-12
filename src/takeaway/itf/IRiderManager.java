package takeaway.itf;

import java.util.List;

import takeaway.model.BeanRider;
import takeaway.model.BeanUser;
import takeaway.util.BaseException;

public interface IRiderManager {
	/**
	 * 添加商家
	 * 注意：当前登陆用户可通过 BeanUser.currentLoginUser获取
	 * @param name  计划名称
	 * @throws BaseException
	 */
	public BeanRider addRider(String name) throws BaseException;
	/**
	 *商家登录
	 */
	public BeanRider login() throws BaseException;
	/**
	 * 提取所有商家
	 * @return
	 * @throws BaseException
	 */
	public List<BeanRider> loadAll()throws BaseException;
	/**
	 * 删除商家
	 * @param Rider
	 * @throws BaseException
	 */
	public void deleteRider(BeanRider Rider)throws BaseException;
	public void updateinfo(String name) throws BaseException;
	public void changeid(String rider,String id) throws BaseException;
	public List<BeanRider> loadbyrider()throws BaseException;
	public List<BeanRider> searchrider(String name)throws BaseException;
}
