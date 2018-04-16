package com.agonzales.SistemaEscolar.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agonzales.SistemaEscolar.domain.Alert;
import com.agonzales.SistemaEscolar.repository.AlertRepository;
import com.agonzales.SistemaEscolar.util.Util;

@Service
public class AlertService {

	@Autowired
	private AlertRepository alertRepository;

	public List<Map<String, Object>> findFirstFourAlertsNotRead(Integer id){
		List<Alert> alerts = alertRepository.findFirst4ByToUsuarioIdAndReadOrderByDateDesc(id, false);
		return getListMapAlerts(alerts, false);
	}
	
	private List<Map<String, Object>> getListMapAlerts(List<Alert> alerts, boolean fullMessage){
		List<Map<String, Object>> listAlerts = new ArrayList<>();

		for(Alert alert: alerts) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", alert.getId());
			map.put("from", alert.getFromUsuario() != null ? alert.getFromUsuario().getUsername() : "Sistema");
			map.put("message", fullMessage == true ? alert.getMessage() : formatMessage(alert.getMessage()));
			map.put("read", alert.isRead());
			
			long diference = (new Date().getTime() / 1000) - (alert.getDate().getTime() / 1000);
			if(diference > 86400) {
				map.put("date", Util.formatDateTime(alert.getDate()));
				map.put("showDate", true);
			}else {
				map.put("timeSeconds", alert.getDate().getTime() / 1000);
				map.put("showTime", true);
			}

			listAlerts.add(map);
		}
		return listAlerts;
	}
	
	private String formatMessage(String message) {
		if(message == null) {
			return "";
		}

		if(message.length() > 80) {
			message = message.substring(0, 80) + "...";
		}

		return message;
	}
	
	public int countByToUsuarioIdAndRead(Integer id) {
		return alertRepository.countByToUsuarioIdAndRead(id, false);
	}
	
	public List<Map<String, Object>> findAllByUserId(Integer id){
		List<Alert> alerts = alertRepository.findAllByToUsuarioIdOrderByDateDesc(id);
		return getListMapAlerts(alerts, true);
	}
	
	public Alert findById(Integer id) {
		Optional<Alert> alert = alertRepository.findById(id);
		return alert.get();
	}

	public Alert markAsRead(Integer id) {
		Alert alert = findById(id);
		if(alert != null && !alert.isRead()) {
			alertRepository.markAlertAsRead(id);
			alert = findById(id);
		}
		return alert;
	}

	public Alert markAsUnread(Integer id) {
		Alert alert = findById(id);
		if(alert.isRead()) {
			alertRepository.markAlertAsUnread(id);
			alert = findById(id);
		}
		return alert;
	}
}
