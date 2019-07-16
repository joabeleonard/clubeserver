package com.example.polls.repository;

import com.example.polls.model.Game;
import com.example.polls.security.UserPrincipal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by rajeevkumarsingh on 20/11/17.
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Optional<Game> findById(Long pollId);

	@Query("SELECT g FROM Game g where g.cliente.user.username = :userName")
	Game findByUser(@Param("userName") String userName);

}
