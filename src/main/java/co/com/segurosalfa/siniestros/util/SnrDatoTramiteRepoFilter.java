package co.com.segurosalfa.siniestros.util;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import co.com.sipren.common.util.SearchCriteria;
import co.com.sipren.common.util.SearchOperation;

public class SnrDatoTramiteRepoFilter<T> implements Specification<T> {

	private static final long serialVersionUID = -2103934259936900615L;

	private List<SearchCriteria<T>> list;
	private List<SearchCriteria<?>> listJoins;
	private Boolean identificacion = false;
	private Boolean persona = false;

	public SnrDatoTramiteRepoFilter() {
		super();
		this.list = new ArrayList<>();
		this.listJoins = new ArrayList<>();
	}

	public void add(SearchCriteria<T> criteria) {
		list.add(criteria);
	}

	public void addJoins(SearchCriteria<?> criteria) {
		listJoins.add(criteria);
	}

	public Boolean getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(Boolean identificacion) {
		this.identificacion = identificacion;
	}

	public Boolean getPersona() {
		return persona;
	}

	public void setPersona(Boolean persona) {
		this.persona = persona;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		List<Predicate> predicates = new ArrayList<>();

		for (SearchCriteria criteria : list) {
			if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
				predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue().toString()));
			} else if (criteria.getOperation().equals(SearchOperation.BETWEEN)) {
				predicates.add(builder.between(root.get(criteria.getKey()), criteria.getValueDateI(),
						criteria.getValueDateF()));
			}
		}

		for (SearchCriteria<?> criteria : listJoins) {
			
			if(criteria.getIsPersona()) {
				predicates.add(builder.equal(root
						.get("siniestro")
						.get(criteria.getFieldJoin())
						.get(criteria.getKey()), criteria.getValue()));	
			}else {
				Join<T, ?> entity = root.join(criteria.getFieldJoin(), JoinType.LEFT);
				predicates.add(builder.equal(entity.get(criteria.getKey()), criteria.getValue()));
			}					
		}

		return builder.and(predicates.toArray(new Predicate[0]));
	}

}
