package takeaway.itf;

import java.util.List;

import takeaway.model.BeanAddress;
import takeaway.model.BeanMjMethod;
import takeaway.model.BeanOrder;
import takeaway.model.BeanRider;
import takeaway.model.BeanStore;
import takeaway.model.BeanUser;
import takeaway.model.BeanYhInfo;
import takeaway.util.BaseException;

public interface IOrderManager {
	/**
	 * 添加订单
	 * 注意：当前登陆用户可通过 BeanUser.currentLoginUser获取
	 * @param name  计划名称
	 * @throws BaseException
	 */
	public BeanOrder addOrder() throws BaseException;
	/**
	 * 提取当前商家所有订单
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrder> loadBysj()throws BaseException;
	/**
	 * 提取当前骑手所有订单
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrder> loadByqs()throws BaseException;
	/**
	 * 提取当前用户所有已送达订单
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrder> loaduserHisOrder() throws BaseException;
	/**
	 * 提取当前商家所有已送达订单
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrder> loadstoreHisOrder() throws BaseException;
	/**
	 * 提取当前骑手所有已送达订单
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrder> loadriderHisOrder() throws BaseException;
	/**
	 * 提取当前用户所有配送中订单
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrder> loaduserIngOrder() throws BaseException;
	/**
	 * 提取当前商家所有配送中订单
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrder> loadstoreingOrder() throws BaseException;
	/**
	 * 提取当前商家所有配送中订单
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrder> loadrideringOrder() throws BaseException;
	/**
	 * 提取所有可接单的订单
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrder> loadriderfreeOrder() throws BaseException;
	/**
	 * 接单
	 * @param plan
	 * @throws BaseException
	 */
	public void takeOrder(BeanOrder order)throws BaseException;
	/**
	 * 确认送达
	 * @param plan
	 * @throws BaseException
	 */
	public void sendOrder(BeanOrder order)throws BaseException;
	/**
	 * 提取当前用户所有评价(对骑手)
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrder> loaduserpj() throws BaseException;
	/**
	 * 提取当前骑手入账信息
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrder> loadridermoney() throws BaseException;
	/**
	 * 提取查询骑手入账信息
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrder> selectridermoney(BeanRider rider) throws BaseException;
	/**
	 * 提取所有骑手入账信息
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrder> loadallridermoney() throws BaseException;
	/**
	 * 删除商家
	 * @param plan
	 * @throws BaseException
	 */
	public List<BeanOrder> loaduserxfinfo() throws BaseException;
	public void deleteOrder()throws BaseException;
	public void updhppj(BeanOrder order)throws BaseException;
	public void updcppj(BeanOrder order)throws BaseException;
	public void delpj(BeanOrder order)throws BaseException;
	public void addstore(BeanStore store)throws BaseException;
	public List<BeanOrder> loadallxfinfo() throws BaseException;
	public List<BeanOrder> selectxfinfo(BeanUser user) throws BaseException;
	public float pdxd()throws BaseException;
	public void csh()throws BaseException;
	public void addmj(BeanMjMethod mj)throws BaseException;
	public void addyh(BeanYhInfo yh)throws BaseException;
	public void addadd(BeanAddress add)throws BaseException;
	public void order()throws BaseException;
}
