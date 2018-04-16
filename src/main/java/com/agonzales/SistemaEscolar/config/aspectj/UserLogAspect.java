package com.agonzales.SistemaEscolar.config.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.agonzales.SistemaEscolar.util.Constantes;
import com.agonzales.SistemaEscolar.util.VariablesSession;


@Aspect
@Configuration
public class UserLogAspect {
	
	private Logger LOG = LoggerFactory.getLogger(UserLogAspect.class);
	private Logger TIMES_LOG = LoggerFactory.getLogger("times-log");
	private Logger USER_TRACE_LOG = LoggerFactory.getLogger("user-trace-log");
	
	@After("execution(* com.agonzales.SistemaEscolar.controller..*(..))")
	public void before(JoinPoint joinPoint){
		String username = (String) VariablesSession.getAttribute(Constantes.USUARIO_SESION);
		if(username != null) {
			USER_TRACE_LOG.info("Username: " + username + " > " + joinPoint.getSignature());
		}

		LOG.info(joinPoint.getSignature().toString());

	}

	@Around("execution(* *(..)) && @annotation(Loggable)")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long start = System.currentTimeMillis();

		String msg = point.getSignature() + " in " + (System.currentTimeMillis() - start) + "ms";

		TIMES_LOG.info(msg);
		return point.proceed();
	}


}
