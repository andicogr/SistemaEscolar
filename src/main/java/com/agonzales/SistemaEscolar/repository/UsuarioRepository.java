package com.agonzales.SistemaEscolar.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.agonzales.SistemaEscolar.domain.Usuario;

@Repository
public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Integer>, QuerydslPredicateExecutor<Usuario>{

	Usuario findByUsername(String username);
	
	@Query("SELECT id FROM Usuario WHERE username =:username")
	Integer getUID(@Param("username") String username);
	
	@Query("SELECT COUNT(e) FROM Usuario e WHERE e.username =:username AND e.id <> :id")
	int countByUsernameAndId(@Param("username") String username, @Param("id") Integer id);

}
