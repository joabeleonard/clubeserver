package com.example.polls.repository;

import com.example.polls.model.CategoriaEmpresa;
import com.example.polls.model.Cliente;
import com.example.polls.model.Cupom;
import com.example.polls.model.DicasGames;
import com.example.polls.model.Empresa;
import com.example.polls.model.NivelGame;
import com.example.polls.model.Personagem;
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
public interface DicaRepository extends JpaRepository<DicasGames, Long> {

//	 @Query("SELECT c FROM Cupom c where c.cliente.id = :clientId")
//    Optional<DicasGames> findById(Long pollId);

	@Query("SELECT d FROM DicasGames d where d.nivelGame.ordemNivel = :ordemNivel and d.nivelGame.personagem = :personagem  and d.ordemDica = 1")
	DicasGames primeiraDicaProximoNivel(@Param("personagem") Personagem personagem, @Param("ordemNivel")int ordemNivel);

	@Query("SELECT d FROM DicasGames d where d.ordemDica = :ordemDica and  d.nivelGame = :nivel")
	DicasGames proximaDica( @Param("nivel") NivelGame nivelGame, @Param("ordemDica")int i);


}
