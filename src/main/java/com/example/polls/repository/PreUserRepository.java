package com.example.polls.repository;

import com.example.polls.model.PreUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by rajeevkumarsingh on 02/08/17.
 */
@Repository
public interface PreUserRepository extends JpaRepository<PreUser, Long> {
	
	
    Optional<PreUser> findByEmail(String email);
    
    List<PreUser> findByIdIn(List<Long> preuserIds);

    Boolean existsByEmail(String email);
    
}
