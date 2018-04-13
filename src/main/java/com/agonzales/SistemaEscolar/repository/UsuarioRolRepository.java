package com.agonzales.SistemaEscolar.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.agonzales.SistemaEscolar.domain.Rol;
import com.agonzales.SistemaEscolar.domain.UsuarioRol;

@Repository
public interface UsuarioRolRepository extends CrudRepository<UsuarioRol, Integer>{

	List<Rol> findActiveRoleByUserId(@Param("userId")Integer userId);
}
