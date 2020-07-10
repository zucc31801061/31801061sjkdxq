package takeaway.itf;

import java.util.List;

import takeaway.model.BeanKind;
import takeaway.util.BaseException;

public interface IKindManager {
	/**
	 * 提取所有分类
	 * @return
	 * @throws BaseException
	 */
	public List<BeanKind> loadAll()throws BaseException;
}
