package com.example.polls.repository;

import com.example.polls.model.Poll;
import com.example.polls.model.Questionario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by rajeevkumarsingh on 20/11/17.
 */
@Repository
public interface QuestionarioRepository extends JpaRepository<Questionario, Long> {

}
