package takeaway.itf;

import java.util.List;

import takeaway.model.BeanYh;
import takeaway.util.BaseException;

public interface IYhManager {
	/**
	 * 提取已经使用的优惠券
	 * @return
	 * @throws BaseException
	 */
	public List<BeanYh> loadused()throws BaseException;
	/**
	 * 提取还未使用的优惠券
	 * @return
	 * @throws BaseException
	 */
	public List<BeanYh> loadnotused()throws BaseException;
	/**
	 * 提取还未拥有的优惠券
	 * @return
	 * @throws BaseException
	 */
	public List<BeanYh> loadnothave()throws BaseException;
}
