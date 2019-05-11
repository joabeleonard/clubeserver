package com.example.polls.repository.impl;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.polls.model.Cliente;
import com.example.polls.model.Cupom;
import com.example.polls.model.StatusCupom;

/**
 * Created by joabeleonard on 09/03/19.
 */
@Repository
public class ClientRepositoryImpl {

	@PersistenceContext
	private EntityManager entityManager;


	@SuppressWarnings("unchecked")
	public ArrayList<Cliente> findCincoMelhoresExperiencia(int pontosExperiencia){
		return (ArrayList<Cliente>) entityManager
				.createQuery("SELECT c FROM Cliente c where c.pontosExperiencia => "+pontosExperiencia+" pontosExperiencia by pontos")
				.setMaxResults(5).getResultList();
	};

	@SuppressWarnings("unchecked")
	public ArrayList<Cliente>  findCincoPioresExperiencia(int pontosExperiencia){
		return (ArrayList<Cliente>) entityManager
				.createQuery("SELECT c FROM Cliente c where c.pontosExperiencia <= "+pontosExperiencia+" pontosExperiencia by desc pontos ")
				.setMaxResults(5).getResultList();
	};

	@SuppressWarnings("unchecked")
	public ArrayList<Cliente> findCincoMelhoresRankingDot(int pontos){
		return (ArrayList<Cliente>) entityManager
				.createQuery("SELECT c FROM Cliente c where c.pontos >= "+pontos+" order by pontos ")
				.setMaxResults(5).getResultList();
	};

	@SuppressWarnings("unchecked")
	public ArrayList<Cliente>  findCincoPioresRankingDot( int pontos){
		return (ArrayList<Cliente>) entityManager
				.createQuery("SELECT c FROM Cliente c where c.pontos <= "+pontos+" order by  pontos desc")
				.setMaxResults(5).getResultList();
	};

}
