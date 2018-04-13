package com.agonzales.SistemaEscolar.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.agonzales.SistemaEscolar.util.Constantes;

@Repository
public class RolPrivilegioRespository {

	@PersistenceContext
	private EntityManager em;

	public boolean exists(Integer idPrivilegio, Integer idRol){
		String sql = "select exists (select * from rol_privilegio where privilegio_id =:idPrivilegio and rol_id =:idRol)";
		
		Query q = em.createNativeQuery(sql);
		
		q.setParameter("idPrivilegio", idPrivilegio);
		q.setParameter("idRol", idRol);
		
		Boolean existe = (Boolean) q.getSingleResult();
		
		return existe;
	}

	@Transactional
	public void setDefaultPrivilegioUsuario(Integer idRol) {
		String sql = "INSERT INTO rol_privilegio(rol_id, privilegio_id) VALUES(:idRol, :idPrivilegio)";
		
		Query q = em.createNativeQuery(sql);
		q.setParameter("idRol", idRol);
		q.setParameter("idPrivilegio", Constantes.PRIVILEGIO_USUARIO);
		
		q.executeUpdate();
	}

}
