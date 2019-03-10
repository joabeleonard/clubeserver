package com.example.polls.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.example.polls.model.Cupom;

/**
 * Created by joabeleonard on 09/03/19.
 */
@Repository
public class CupomRepositoryImpl {

	@PersistenceContext
    private EntityManager entityManager;
	
	 public Cupom findCupomByUser(Long userId){
		 return entityManager.createQuery("SELECT c FROM Cupom c where c.cliente.user.id = "+userId+" order by c.dataConsumo",
		          Cupom.class).setMaxResults(1).getSingleResult();
	 };
    
}
