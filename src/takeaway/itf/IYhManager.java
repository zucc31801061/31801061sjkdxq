package takeaway.itf;

import java.util.List;

import takeaway.model.BeanYh;
import takeaway.util.BaseException;

public interface IYhManager {
	/**
	 * ��ȡ�Ѿ�ʹ�õ��Ż�ȯ
	 * @return
	 * @throws BaseException
	 */
	public List<BeanYh> loadused()throws BaseException;
	/**
	 * ��ȡ��δʹ�õ��Ż�ȯ
	 * @return
	 * @throws BaseException
	 */
	public List<BeanYh> loadnotused()throws BaseException;
	/**
	 * ��ȡ��δӵ�е��Ż�ȯ
	 * @return
	 * @throws BaseException
	 */
	public List<BeanYh> loadnothave()throws BaseException;
}
