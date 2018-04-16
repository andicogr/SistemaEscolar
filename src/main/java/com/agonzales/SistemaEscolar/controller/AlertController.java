package com.agonzales.SistemaEscolar.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.agonzales.SistemaEscolar.domain.Alert;
import com.agonzales.SistemaEscolar.service.AlertService;
import com.agonzales.SistemaEscolar.util.VariablesSession;

@Controller
@RequestMapping("/alerts")
public class AlertController {
	
	@Autowired
	private AlertService alertService;
	
	@RequestMapping("/viewAlerts")
	public String viewAlerts(Model model) {
		Integer idUsuario = VariablesSession.getUserSessionId();

		List<Map<String, Object>> alertList = alertService.findAllByUserId(idUsuario);
		
		model.addAttribute("alertList", alertList);
		return "alertsList";
	}
	
	@RequestMapping("/viewAlert")
	public String viewAlert(Model model, Integer id) {
		Alert alert = alertService.markAsRead(id);
		model.addAttribute("alert", alert);
		return "verAlerta";
	}
	
	@RequestMapping("/mark/unread")
	public String markAsUnread(Model model, Integer id) {
		Alert alert = alertService.markAsUnread(id);
		model.addAttribute("alert", alert);
		return "verAlerta";
	}
	
	@RequestMapping("/notifications")
	public String getAlertNotifications(Model model) {
		Integer userId = VariablesSession.getUserSessionId();
		List<Map<String, Object>> listAlerts = alertService.findFirstFourAlertsNotRead(userId);
		int cantidadAlertas =  alertService.countByToUsuarioIdAndRead(userId);

		model.addAttribute("alerts", listAlerts);
		model.addAttribute("cantidadAlertas", cantidadAlertas);

		return "alertNotification";
	}

}
