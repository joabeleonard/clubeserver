package com.example.polls.repository;

import com.example.polls.model.Extrato;
import com.example.polls.payload.PagedResponse;

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
public interface ExtratoRepository extends JpaRepository<Extrato, Long> {

	 @Query("SELECT e FROM Extrato e where e.user.id = :userId")
	 Page<Extrato> getAllExtrato(@Param("userId") Long userId,Pageable pageable);
	 
	 @Query("SELECT e FROM Extrato e where e.user.id = :userId and valorComissao is not null")
	 Page<Extrato> getAllExtratoFinanceiro(@Param("userId") Long userId,Pageable pageable);

}
