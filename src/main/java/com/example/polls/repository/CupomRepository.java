package com.example.polls.repository;

import com.example.polls.model.Cupom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by rajeevkumarsingh on 20/11/17.
 */
@Repository
public interface CupomRepository extends JpaRepository<Cupom, Long> {

	 @Query("SELECT c FROM Cupom c where c.cliente.id = :clientId")
	 Page<Cupom> findCuponsIdsByUserId(@Param("clientId") Long clientId,Pageable pageable);
    
	 @Query("SELECT c FROM Cupom c where c.codigo = :codigo")
	 Cupom findCupomByCodigo(@Param("codigo") String codigo);
	 
}
