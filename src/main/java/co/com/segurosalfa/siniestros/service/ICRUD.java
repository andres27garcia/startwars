package co.com.segurosalfa.siniestros.service;

import java.util.List;

import co.com.segurosalfa.siniestros.exception.SiprenException;

public interface ICRUD<T, ID> {

	T registrar(T pac) throws SiprenException;

	T modificar(T pac) throws SiprenException;

	List<T> listar() throws SiprenException;

	T listarPorId(ID id) throws SiprenException;

	void eliminar(ID id) throws SiprenException;

}
