package com.example.polls.repository;

import com.example.polls.model.Personagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by rajeevkumarsingh on 20/11/17.
 */
@Repository
public interface PersonagemRepository extends JpaRepository<Personagem, Long> {

    Optional<Personagem> findById(Long pollId);

}
