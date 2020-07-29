package com.example.polls.repository;

import com.example.polls.model.Cupom;
import com.example.polls.model.Extrato;
import com.example.polls.model.Indicacao;
import com.example.polls.payload.ClientResponse;
import com.example.polls.payload.PagedResponse;
import com.example.polls.security.UserPrincipal;

import java.util.List;

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

	@Query("SELECT i FROM Indicacao i where i.userIndicou.id = :userId")
	Page<Indicacao> findByUserIndicou(@Param("userId") Long userId , Pageable pageable);

}
