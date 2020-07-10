package takeaway.itf;

import java.util.List;

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
}
