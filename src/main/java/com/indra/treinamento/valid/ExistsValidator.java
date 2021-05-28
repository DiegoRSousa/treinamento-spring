package com.indra.treinamento.valid;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistsValidator implements ConstraintValidator<Exists, Long>{
	
	private String domainAttribute;
	private Class<?> klazz;
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public void initialize(Exists params) {
		domainAttribute = params.fieldName();
		klazz = params.domainClass();
	}

	@Override
	public boolean isValid(Long value, ConstraintValidatorContext context) {
		var query = manager.createQuery("select 1 from " + klazz.getName() + " where " + domainAttribute + " =:value");
		query.setParameter("value", value);
		return !query.getResultList().isEmpty();
	}

}
