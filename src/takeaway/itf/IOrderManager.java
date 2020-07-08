package takeaway.itf;

import java.util.List;

import takeaway.model.BeanOrder;
import takeaway.util.BaseException;

public interface IOrderManager {
	/**
	 * 添加订单
	 * 注意：当前登陆用户可通过 BeanUser.currentLoginUser获取
	 * @param name  计划名称
	 * @throws BaseException
	 */
	public BeanOrder addOrder(String name) throws BaseException;
	/**
	 * 提取当前商家所有订单
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrder> loadBysj()throws BaseException;
	public List<BeanOrder> loaduserHisOrder() throws BaseException;
	public List<BeanOrder> loaduserIngOrder() throws BaseException;
	/**
	 * 删除商家
	 * @param plan
	 * @throws BaseException
	 */
	public void deleteOrder(BeanOrder plan)throws BaseException;
}
