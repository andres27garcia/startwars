package co.com.segurosalfa.siniestros.repo;

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

public class GenericSprecification<T> implements Specification<T> {

	private static final long serialVersionUID = -2103934259936900615L;

	private List<SearchCriteria<T>> list;
	private List<SearchCriteria<?>> listJoins;

	public GenericSprecification() {
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

	@SuppressWarnings("rawtypes")
	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		List<Predicate> predicates = new ArrayList<>();

		for (SearchCriteria criteria : list) {
			if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
				predicates.add(builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString()));
			} else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
				predicates.add(builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString()));
			} else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
				predicates
						.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
			} else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
				predicates.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
			} else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
				predicates.add(builder.notEqual(root.get(criteria.getKey()), criteria.getValue().toString()));
			} else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
				predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue().toString()));
			} else if (criteria.getOperation().equals(SearchOperation.BETWEEN)) {
				predicates.add(builder.between(root.get(criteria.getKey()), criteria.getValueDateI(),
						criteria.getValueDateF()));
			} else if (criteria.getOperation().equals(SearchOperation.BETWEEN_LONG)) {
				predicates.add(builder.between(root.get(criteria.getKey()),
						Long.valueOf(criteria.getValueI().toString()), Long.valueOf(criteria.getValueF().toString())));
			}
		}

		// Si la consulta realiza JOIN con otras tablas
		for (SearchCriteria<?> criteria : listJoins) {
			/*
			 * Si se encesita filtrar dentro de una entidad que a su vez tiene JoinColumn
			 * hacia otra entidad
			 */
			if (criteria.getManageSubJoin()) {
				predicates.add(builder.equal(
						root.get(criteria.getEntityJoin()).get(criteria.getFieldJoin()).get(criteria.getKey()),
						criteria.getValue()));
			} else {
				// En caso de que no, realiza el filtro con un JOIN normal hacia las dos tablas
				// que lleguen como parametro
				if (criteria.isManageJoin() && criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
					Join<T, ?> entity = root.join(criteria.getFieldJoin(), JoinType.LEFT);
					predicates.add(builder.greaterThan(entity.get(criteria.getKey()), criteria.getValue().toString()));
				} else if (criteria.isManageJoin() && criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
					Join<T, ?> entity = root.join(criteria.getFieldJoin(), JoinType.LEFT);
					predicates.add(builder.lessThan(entity.get(criteria.getKey()), criteria.getValue().toString()));
				} else if (criteria.isManageJoin()
						&& criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
					Join<T, ?> entity = root.join(criteria.getFieldJoin(), JoinType.LEFT);
					predicates.add(builder.greaterThanOrEqualTo(entity.get(criteria.getKey()),
							criteria.getValue().toString()));
				} else if (criteria.isManageJoin() && criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
					Join<T, ?> entity = root.join(criteria.getFieldJoin(), JoinType.LEFT);
					predicates.add(
							builder.lessThanOrEqualTo(entity.get(criteria.getKey()), criteria.getValue().toString()));
				} else if (criteria.isManageJoin() && criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
					Join<T, ?> entity = root.join(criteria.getFieldJoin(), JoinType.LEFT);
					predicates.add(builder.notEqual(entity.get(criteria.getKey()), criteria.getValue()));
				} else if (criteria.isManageJoin() && criteria.getOperation().equals(SearchOperation.EQUAL)) {
					Join<T, ?> entity = root.join(criteria.getFieldJoin(), JoinType.LEFT);
					predicates.add(builder.equal(entity.get(criteria.getKey()), criteria.getValue()));
				} else if (criteria.getOperation().equals(SearchOperation.BETWEEN)) {
					Join<T, ?> entity = root.join(criteria.getFieldJoin(), JoinType.LEFT);
					predicates.add(builder.between(entity.get(criteria.getKey()), criteria.getValueDateI(),
							criteria.getValueDateF()));
				} else if (criteria.getOperation().equals(SearchOperation.BETWEEN_LONG)) {
					Join<T, ?> entity = root.join(criteria.getFieldJoin(), JoinType.LEFT);
					predicates.add(builder.between(entity.get(criteria.getKey()),
							Long.valueOf(criteria.getValueI().toString()),
							Long.valueOf(criteria.getValueF().toString())));
				}
			}
		}

		return builder.or(predicates.toArray(new Predicate[0]));
	}

}
