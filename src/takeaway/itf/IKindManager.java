package takeaway.itf;

import java.util.List;

import takeaway.model.BeanKind;
import takeaway.model.BeanKind;
import takeaway.util.BaseException;

public interface IKindManager {
	/**
	 * ��ȡ���з���
	 * @return
	 * @throws BaseException
	 */
	public List<BeanKind> loadAll()throws BaseException;
	/**
	 * ���ӷ���
	 * @return
	 * @throws BaseException
	 */
	public void Addfl(String name)throws BaseException;
	/**
	 * ɾ������
	 * @return
	 * @throws BaseException
	 */
	public void Delfl(BeanKind kind)throws BaseException;
	public void Updfl(BeanKind kind,String name)throws BaseException;
}
