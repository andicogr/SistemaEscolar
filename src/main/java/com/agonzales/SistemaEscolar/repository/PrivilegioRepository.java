package com.agonzales.SistemaEscolar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agonzales.SistemaEscolar.domain.Privilegio;

@Repository
public interface PrivilegioRepository extends CrudRepository<Privilegio, Integer>{

	List<Privilegio> findByNombre(String nombre);
	
	@Query("FROM Privilegio "
			+ "WHERE privilegioPadre IS null "
			+ "AND activo = true "
			+ "AND nombre != 'PRIV_USER'"
			+ "ORDER BY orden ASC")
	List<Privilegio> findPrivilegiosPadreActivos();
}
