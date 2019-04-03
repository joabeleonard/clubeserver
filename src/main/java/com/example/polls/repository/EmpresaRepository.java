package com.example.polls.repository;

import com.example.polls.model.CategoriaEmpresa;
import com.example.polls.model.Cliente;
import com.example.polls.model.Cupom;
import com.example.polls.model.Empresa;
import com.example.polls.model.Poll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by rajeevkumarsingh on 20/11/17.
 */
@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    Optional<Empresa> findById(Long pollId);

    @Query("SELECT e FROM Empresa e where e.razaoSocial like :nome and e.categoriaEmpresa = :categoriaEmpresa")
    Page<Empresa> getEmpresasByFilters(@Param("nome") String nome,@Param("categoriaEmpresa") CategoriaEmpresa categoria,Pageable pageable);
    
    Page<Poll> findByCreatedBy(Long userId, Pageable pageable);

}
