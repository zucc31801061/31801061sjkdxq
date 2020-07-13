package takeaway.itf;

import java.util.List;

import takeaway.model.BeanMjMethod;
import takeaway.util.BaseException;

public interface IMjMethodManager {
	/**
	 * 提取当前商家的满减方案
	 * @return
	 * @throws BaseException
	 */
	public List<BeanMjMethod> loadAll() throws BaseException;
	public List<BeanMjMethod> selectbestmj() throws BaseException;
	/**
	 * 增加满减方案
	 * @return
	 * @throws BaseException
	 */
	public BeanMjMethod Addmj(Float money,Float yh,Boolean yhdj)throws BaseException;
	/**
	 * 删除满减方案
	 * @return
	 * @throws BaseException
	 */
	public void Delmj(BeanMjMethod method)throws BaseException;
	public void Updmj(BeanMjMethod method,Float money,Float yh,Boolean yhdj)throws BaseException;
}
