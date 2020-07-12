package takeaway.itf;

import java.util.List;

import takeaway.model.BeanKind;
import takeaway.model.BeanKind;
import takeaway.util.BaseException;

public interface IKindManager {
	/**
	 * 提取所有分类
	 * @return
	 * @throws BaseException
	 */
	public List<BeanKind> loadAll()throws BaseException;
	/**
	 * 增加分类
	 * @return
	 * @throws BaseException
	 */
	public void Addfl(String name)throws BaseException;
	/**
	 * 删除分类
	 * @return
	 * @throws BaseException
	 */
	public void Delfl(BeanKind kind)throws BaseException;
	public void Updfl(BeanKind kind,String name)throws BaseException;
}
