package takeaway.itf;

import java.util.List;

import takeaway.model.BeanMjMethod;
import takeaway.util.BaseException;

public interface IMjMethodManager {
	/**
	 * ��ȡ��ǰ�̼ҵ���������
	 * @return
	 * @throws BaseException
	 */
	public List<BeanMjMethod> loadAll() throws BaseException;
	public List<BeanMjMethod> selectbestmj() throws BaseException;
	/**
	 * ������������
	 * @return
	 * @throws BaseException
	 */
	public BeanMjMethod Addmj(Float money,Float yh,Boolean yhdj)throws BaseException;
	/**
	 * ɾ����������
	 * @return
	 * @throws BaseException
	 */
	public void Delmj(BeanMjMethod method)throws BaseException;
	public void Updmj(BeanMjMethod method,Float money,Float yh,Boolean yhdj)throws BaseException;
}
