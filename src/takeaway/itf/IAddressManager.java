package takeaway.itf;

import java.util.List;

import takeaway.model.BeanAddress;
import takeaway.model.BeanOrder;
import takeaway.util.BaseException;

public interface IAddressManager {
	/**
	 * ��ӵ�ַ
	 * ע�⣺��ǰ��½�û���ͨ�� BeanUser.currentLoginUser��ȡ
	 * @throws BaseException
	 */
	public BeanAddress addAddress(String sheng,String shi,String qu,String address,String name,String phnum) throws BaseException;
	/**
	 * ��ȡ���е�ַ
	 * @return
	 * @throws BaseException
	 */
	public List<BeanAddress> loadAll()throws BaseException;
	/**
	 * ɾ����ַ
	 * @param address
	 * @throws BaseException
	 */
	public void deleteAddress(BeanAddress address)throws BaseException;
	/**
	 * ���ض�Ӧ�����ĵ�ַ��Ϣ
	 * @param address
	 * @throws BaseException
	 */
	public List<BeanAddress> loadselect(BeanOrder order)throws BaseException;
}
