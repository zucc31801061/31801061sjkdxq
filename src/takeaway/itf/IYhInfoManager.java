package takeaway.itf;

import java.util.List;

import takeaway.model.BeanYhInfo;
import takeaway.util.BaseException;

public interface IYhInfoManager {
	/**
	 * ��ȡ�Ѿ�ʹ�õ��Ż�ȯ
	 * @return
	 * @throws BaseException
	 */
	public List<BeanYhInfo> loadused()throws BaseException;
	/**
	 * ��ȡ��δʹ�õ��Ż�ȯ
	 * @return
	 * @throws BaseException
	 */
	public List<BeanYhInfo> loadnotused()throws BaseException;
	/**
	 * ��ȡ��δӵ�е��Ż�ȯ
	 * @return
	 * @throws BaseException
	 */
	public List<BeanYhInfo> loadnothave()throws BaseException;
}
