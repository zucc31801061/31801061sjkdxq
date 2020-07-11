package takeaway.itf;

import java.util.Date;
import java.util.List;

import takeaway.model.BeanMjMethod;
import takeaway.model.BeanYhInfo;
import takeaway.util.BaseException;

public interface IYhInfoManager {
	/**
	 * 提取已经使用的优惠券
	 * @return
	 * @throws BaseException
	 */
	public List<BeanYhInfo> loadused()throws BaseException;
	/**
	 * 提取还未使用的优惠券
	 * @return
	 * @throws BaseException
	 */
	public List<BeanYhInfo> loadnotused()throws BaseException;
	/**
	 * 提取还未拥有的优惠券
	 * @return
	 * @throws BaseException
	 */
	public List<BeanYhInfo> loadnothave()throws BaseException;
	/**
	 * 提取当前商家的优惠券
	 * @return
	 * @throws BaseException
	 */
	public List<BeanYhInfo> loadbystore()throws BaseException;
	/**
	 * 增加优惠券
	 * @return
	 * @throws BaseException
	 */
	public BeanYhInfo Addyh(int yhmoney,int jdyq,Date start,Date end)throws BaseException;
	/**
	 * 删除优惠券
	 * @return
	 * @throws BaseException
	 */
	public void Delyh(BeanYhInfo yh)throws BaseException;
	public void Updyh(BeanYhInfo yh,int yhmoney,int jdyq,Date start,Date end)throws BaseException;
}
