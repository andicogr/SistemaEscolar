package com.agonzales.SistemaEscolar.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agonzales.SistemaEscolar.domain.UserAttempt;

@Repository
public interface UserAttemptRepository extends CrudRepository<UserAttempt, Integer>{
	
	UserAttempt findByUsername(String username);

}
