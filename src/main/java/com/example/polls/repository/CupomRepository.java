package com.example.polls.repository;

import com.example.polls.model.Cliente;
import com.example.polls.model.Cupom;
import com.example.polls.model.ReportAvaliacoes;
import com.example.polls.model.ReportVendas;
import com.example.polls.security.UserPrincipal;

import java.util.ArrayList;

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

     @Query("SELECT c FROM Cupom c where c.empresa.id = :empresaId")
	 ArrayList<Cupom> findCuponsByEmpresa(@Param("empresaId") Long id);
     
     @Query("SELECT  c.notaAvaliacao as name, count(*) as value FROM Cupom c where c.empresa.id = :empresaId GROUP BY c.notaAvaliacao")
     ArrayList<ReportAvaliacoes> findReportAvaliacao(@Param("empresaId") Long id);
     
     @Query("SELECT  DATE_FORMAT(c.dataGeracao,'%Y-%m') as name, sum(c.valorCupom) as valor, count(*) as quant FROM Cupom c where c.empresa.user.id = :empresaId GROUP BY DATE_FORMAT(c.dataGeracao,'%Y-%m')")
	 ArrayList<ReportVendas> findDataReportVendas(@Param("empresaId") Long id);

	@Query("SELECT c FROM Cupom c where c.empresa.user.id = :userId")
	Page<Cupom> findVendas(@Param("userId") Long userId, Pageable pageable);
     
     
	 
}
