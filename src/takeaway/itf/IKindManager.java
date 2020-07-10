package takeaway.itf;

import java.util.List;

import takeaway.model.BeanKind;
import takeaway.util.BaseException;

public interface IKindManager {
	/**
	 * ��ȡ���з���
	 * @return
	 * @throws BaseException
	 */
	public List<BeanKind> loadAll()throws BaseException;
}
