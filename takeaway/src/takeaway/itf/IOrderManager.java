package takeaway.itf;

import java.util.List;

import takeaway.model.BeanOrder;
import takeaway.util.BaseException;

public interface IOrderManager {
	/**
	 * 添加商家
	 * 注意：当前登陆用户可通过 BeanUser.currentLoginUser获取
	 * @param name  计划名称
	 * @throws BaseException
	 */
	public BeanOrder addOrder(String name) throws BaseException;
	/**
	 * 提取所有商家
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrder> loadAll()throws BaseException;
	/**
	 * 删除商家
	 * @param plan
	 * @throws BaseException
	 */
	public void deleteOrder(BeanOrder plan)throws BaseException;
}
