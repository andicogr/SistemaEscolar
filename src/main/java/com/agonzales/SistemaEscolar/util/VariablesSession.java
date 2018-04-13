package com.agonzales.SistemaEscolar.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

//TODO en un futuro debe estar en el scope de aplicacion
public class VariablesSession {

	public static Object getAttribute(String atributo){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		return session.getAttribute(atributo);
	}

	public static void setAttribute(String atributo, Object objeto){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		session.setAttribute(atributo, objeto);
	}

	public static Integer getUserSessionId() {
		return (Integer) getAttribute(Constantes.USUARIO_SESSION_ID);
	}

}
