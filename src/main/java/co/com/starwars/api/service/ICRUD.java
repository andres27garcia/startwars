package  co.com.starwars.api.service;

import java.util.List;

import co.com.starwars.api.exception.ServiceException;

public interface ICRUD<T, ID> {

	T registrar(T obj) throws ServiceException;

	T modificar(T obj) throws ServiceException;

	List<T> listar() throws ServiceException;

	T listarPorId(ID id) throws ServiceException;

	void eliminar(ID id) throws ServiceException;

}
