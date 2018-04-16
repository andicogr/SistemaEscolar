package com.agonzales.SistemaEscolar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agonzales.SistemaEscolar.domain.UserAttempt;
import com.agonzales.SistemaEscolar.repository.UserAttemptRepository;

@Service
public class UserAttemptService {

	@Autowired
	private UserAttemptRepository userAttemptRepository;
	
	public UserAttempt findByUsername(String username) {
		return userAttemptRepository.findByUsername(username);
	}
	
	@Transactional
	public void resetUserAttempt(String username) {
		UserAttempt userAttemp = userAttemptRepository.findByUsername(username);
		
		if(userAttemp != null) {
			userAttemp.setAttempts(0);
			userAttemptRepository.save(userAttemp);
		}
	}
	
	@Transactional
	public UserAttempt saveNewUserAttemp(String username) {
		UserAttempt userAttemp = new UserAttempt(username);
		return userAttemptRepository.save(userAttemp);
	}
	
	@Transactional
	public UserAttempt updateAttemp(String username) {
		UserAttempt userAttemp = userAttemptRepository.findByUsername(username);
		if(userAttemp != null) {
			userAttemp.setAttempts(userAttemp.getAttempts() + 1);
			userAttemp = userAttemptRepository.save(userAttemp);			
		}
		return userAttemp;
	}
	
	public UserAttempt registrarUserAttemp(String username) {
		UserAttempt userAttemp = userAttemptRepository.findByUsername(username);
		if(userAttemp == null) {
			userAttemp = saveNewUserAttemp(username);
		}else {
			userAttemp = updateAttemp(username);
		}
		return userAttemp;
	}
}
