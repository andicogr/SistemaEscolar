package com.agonzales.SistemaEscolar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.agonzales.SistemaEscolar.domain.Rol;

@Repository
public interface RolRepository extends CrudRepository<Rol, Integer>, QuerydslPredicateExecutor<Rol>{

	List<Rol> findByNombre(String nombre);
	
	List<Rol> findByActivo(boolean activo);
	
	@Query("SELECT COUNT(e) FROM Rol e WHERE e.nombre =:nombre AND e.id <> :id")
	int countByNombreAndId(@Param("nombre") String nombre, @Param("id") Integer id);

	List<Rol> findUnassignedRoleByUserId(@Param("userId")Integer userId);

}
