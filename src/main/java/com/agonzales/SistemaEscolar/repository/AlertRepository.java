package com.agonzales.SistemaEscolar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.agonzales.SistemaEscolar.domain.Alert;

@Repository
public interface AlertRepository extends CrudRepository<Alert, Integer>{

	List<Alert> findFirst4ByToUsuarioIdAndReadOrderByDateDesc(Integer id, boolean read);

	int countByToUsuarioIdAndRead(Integer id, boolean read);

	List<Alert> findAllByToUsuarioIdOrderByDateDesc(Integer id);

	@Transactional
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE Alert SET read = true, readDate = now() WHERE id =:id")
	void markAlertAsRead(@Param("id") Integer id);

	@Transactional
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE Alert SET read = false, readDate = null WHERE id =:id")
	void markAlertAsUnread(@Param("id") Integer id);

}
