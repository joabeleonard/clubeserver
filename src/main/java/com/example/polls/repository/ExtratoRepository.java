package com.example.polls.repository;

import com.example.polls.model.Extrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by rajeevkumarsingh on 20/11/17.
 */
@Repository
public interface ExtratoRepository extends JpaRepository<Extrato, Long> {

}
