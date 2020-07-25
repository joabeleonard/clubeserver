package com.example.polls.repository;

import com.example.polls.model.Cliente;
import com.example.polls.model.Poll;
import com.example.polls.security.UserPrincipal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created by rajeevkumarsingh on 20/11/17.
 */
@Repository
public interface ClientRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findById(Long pollId);

	@Query("SELECT c FROM Cliente c where c.user.id = :userId")
	Cliente findByUser(@Param("userId") Long userId);

	@Query("SELECT c FROM Cliente c where c.user.username = :username")
	Cliente findByUsername(@Param("username") String username);

//	Cliente findByCodigoIndicacao(String codigoIndicacao);


//	@Query("SELECT c FROM Cliente c where c.pontosExperiencia > :pontos pontosExperiencia order by pontos limit 5")
//	ArrayList<Cliente> findCincoMelhoresExperiencia(@Param("pontos")int pontosExperiencia);
//
//	@Query("SELECT c FROM Cliente c where c.pontosExperiencia > :pontos pontosExperiencia order by pontos limit 5")
//	Collection<? extends Cliente> findCincoPioresExperiencia(@Param("pontos")int pontosExperiencia);
//
//	@Query("SELECT c FROM Cliente c where c.pontos > :pontos order by pontos limit 5")
//	ArrayList<Cliente> findCincoMelhoresRankingDot(@Param("pontos")int pontos);
//
//	@Query("SELECT c FROM Cliente c where c.pontos > :pontos order by pontos limit 5")
//	Collection<? extends Cliente> findCincoPioresRankingDot(@Param("pontos")int pontos);

    
}
