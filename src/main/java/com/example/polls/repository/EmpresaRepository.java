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
    
	@Query("SELECT e FROM Empresa e where e.id = :userId")
	Empresa findByEmpresaId(@Param("userId") Long userId);
    
	@Query("SELECT e FROM Empresa e where e.user.id = :userId")
	Empresa findByUser(@Param("userId") Long userId);

    Page<Poll> findByCreatedBy(Long userId, Pageable pageable);

    @Query("select e from Empresa e where ( :nome is null or lower(e.user.name) like lower(concat('%', :nome,'%')) )"
    		+ " and ( :categoriaEmpresa is null or e.categoriaEmpresa = :categoriaEmpresa)  "
    		+ " and ( :bairro is null or lower(e.endereco.bairro) like lower(concat('%', :bairro,'%'))) "
    		+ "  and ( :cidade is null or lower(e.endereco.cidade) like lower(concat('%', :cidade,'%')))  ")
	Page<Empresa> getEmpresasByFilters(@Param("nome")String nome, @Param("categoriaEmpresa")CategoriaEmpresa categoriaEmpresa, String bairro, String cidade, Pageable pageable);

}
