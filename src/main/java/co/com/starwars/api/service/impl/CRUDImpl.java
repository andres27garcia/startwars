package co.com.starwars.api.service.impl;

import java.util.List;

import co.com.starwars.api.exception.ServiceException;
import co.com.starwars.api.repo.IGenericRepo;
import co.com.starwars.api.service.ICRUD;


public abstract class CRUDImpl<T, ID> implements ICRUD<T, ID>{
	
	protected abstract IGenericRepo<T, ID> getRepo();
	
	@Override
	public T registrar(T obj) throws ServiceException {
		return getRepo().save(obj);
	}

	@Override
	public T modificar(T obj) throws ServiceException {
		return getRepo().save(obj);
	}

	@Override
	public List<T> listar() throws ServiceException {
		return getRepo().findAll();
	}
	
	@Override
	public T listarPorId(ID id) throws ServiceException {
		return getRepo().findById(id).orElse(null);		
	}
	
	@Override
	public void eliminar(ID id) throws ServiceException {
		getRepo().deleteById(id);
	}

}
