package co.com.segurosalfa.siniestros.service.impl;

import java.util.List;

import co.com.segurosalfa.siniestros.exception.SiprenException;
import co.com.segurosalfa.siniestros.repo.IGenericRepo;
import co.com.segurosalfa.siniestros.service.ICRUD;

public abstract class CRUDImpl<T, ID> implements ICRUD<T, ID>{
	
	protected abstract IGenericRepo<T, ID> getRepo();
	
	@Override
	public T registrar(T obj) throws SiprenException {
		return getRepo().save(obj);
	}

	@Override
	public T modificar(T obj) throws SiprenException {
		return getRepo().save(obj);
	}

	@Override
	public List<T> listar() throws SiprenException {
		return getRepo().findAll();
	}
	
	@Override
	public T listarPorId(ID id) throws SiprenException {
		return getRepo().findById(id).orElse(null);		
	}
	
	@Override
	public void eliminar(ID id) throws SiprenException {
		getRepo().deleteById(id);
	}

}
