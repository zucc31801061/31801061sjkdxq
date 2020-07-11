package takeaway.itf;

import java.util.Date;
import java.util.List;

import takeaway.model.BeanMjMethod;
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
	/**
	 * ��ȡ��ǰ�̼ҵ��Ż�ȯ
	 * @return
	 * @throws BaseException
	 */
	public List<BeanYhInfo> loadbystore()throws BaseException;
	/**
	 * �����Ż�ȯ
	 * @return
	 * @throws BaseException
	 */
	public BeanYhInfo Addyh(int yhmoney,int jdyq,Date start,Date end)throws BaseException;
	/**
	 * ɾ���Ż�ȯ
	 * @return
	 * @throws BaseException
	 */
	public void Delyh(BeanYhInfo yh)throws BaseException;
	public void Updyh(BeanYhInfo yh,int yhmoney,int jdyq,Date start,Date end)throws BaseException;
}
