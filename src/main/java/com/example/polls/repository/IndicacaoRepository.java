package com.example.polls.repository;

import com.example.polls.model.Cupom;
import com.example.polls.model.Indicacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Created by Joabe Leonard on 17/02/19.
 */
@Repository
public interface IndicacaoRepository extends JpaRepository<Indicacao, Long> {

}
