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
public class PreUserRepositoryImpl {

	@PersistenceContext
	private EntityManager entityManager;

}
