package com.example.polls.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.example.polls.model.DicasGames;

/**
 * Created by joabeleonard on 09/03/19.
 */
@Repository
public class DicaRepositoryImpl {

	@PersistenceContext
    private EntityManager entityManager;
	
	 public DicasGames findDicaByPersonagemId(Long idPersonagem){
		 return entityManager.createQuery("SELECT d FROM DicasGames d where d.nivelGame.personagem.id = "+idPersonagem +"ORDER BY d.ordemDica",
		          DicasGames.class).setMaxResults(1).getSingleResult();
	 };
 
	 

}
