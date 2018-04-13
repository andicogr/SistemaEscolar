package com.agonzales.SistemaEscolar.config.audit;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agonzales.SistemaEscolar.util.Constantes;
import com.agonzales.SistemaEscolar.util.EntidadAuditoria;
import com.agonzales.SistemaEscolar.util.VariablesSession;

public class AuditListener {
	
	private static final Logger AUDIT_LOG = LoggerFactory.getLogger(Constantes.LOG_AUDITORIA);
	private static final Logger LOG = LoggerFactory.getLogger(AuditListener.class);

	@PostPersist
	public void onPostPersist(EntidadAuditoria object) {
		String userSession = (String) VariablesSession.getAttribute(Constantes.USUARIO_SESION);
		try {
			AUDIT_LOG.info("[User] " + userSession + " - [save] " + object.toString());
		} catch (Exception | StackOverflowError e) {
			AUDIT_LOG.info("[User] " + userSession + " - [save] " + object.getId());
			LOG.error("Error al registrar log de auditoria para la clase " 
					+ object.getClass().getSimpleName() + ", Error: " + e);
			e.printStackTrace();
		}

	}

	@PostUpdate
	public void onPostUpdate(EntidadAuditoria object) {
		String userSession = (String) VariablesSession.getAttribute(Constantes.USUARIO_SESION);
		try {
			AUDIT_LOG.info("[User] " + userSession + " - [update] " + object.toString());
		} catch (Exception | StackOverflowError e) {
			AUDIT_LOG.info("[User] " + userSession + " - [update] " + object.getId());
			LOG.error("Error al registrar log de auditoria para la clase " 
					+ object.getClass().getSimpleName() + ", Error: " + e);
			e.printStackTrace();
		}
	}

	@PostRemove
	public void onPostRemove(EntidadAuditoria object) {
		String userSession = (String) VariablesSession.getAttribute(Constantes.USUARIO_SESION);
		try {
			AUDIT_LOG.info("[User] " + userSession + " - [delete] " + object.toString());
		} catch (Exception | StackOverflowError e) {
			AUDIT_LOG.info("[User] " + userSession + " - [delete] " + object.getId());
			LOG.error("Error al registrar log de auditoria para la clase " 
					+ object.getClass().getSimpleName() + ", Error: " + e);
			e.printStackTrace();
		}
	}

}
