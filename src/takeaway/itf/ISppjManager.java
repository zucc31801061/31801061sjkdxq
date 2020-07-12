package takeaway.itf;

import java.util.List;

import takeaway.model.BeanOrderInfo;
import takeaway.model.BeanProduct;
import takeaway.model.BeanSppj;
import takeaway.util.BaseException;

public interface ISppjManager {
	/**
	 * 添加评价
	 * 注意：当前登陆用户可通过 BeanUser.currentLoginUser获取
	 * @throws BaseException
	 */
	public void addSppj(BeanSppj sppj,String pjnr,int pjstar,boolean photo) throws BaseException;
	/**
	 * 提取当前用户所有评价
	 * @return
	 * @throws BaseException
	 */
	public List<BeanSppj> loadbyuser()throws BaseException;
	/**
	 * 提取当前商家所有评价
	 * @return
	 * @throws BaseException
	 */
	public List<BeanSppj> loadbystore()throws BaseException;
	public List<BeanSppj> loadpropj(BeanProduct product)throws BaseException;
	public List<BeanSppj> loadpropj(BeanOrderInfo orderinfo)throws BaseException;
	/**
	 * 删除商家
	 * @param address
	 * @throws BaseException
	 */
	public void deleteSppj(BeanSppj sppj)throws BaseException;
}
