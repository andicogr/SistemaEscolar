package com.agonzales.SistemaEscolar.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;

public class SqlExceptionMessage {


	private final static String NO_SQL_ERROR = "NO_SQL_ERROR";
	private final static String PROPERTIES_FILE = "sqlErrorCode.properties";
	private static SqlExceptionMessage instance;
	private String errorCode;
	private String errorMessage;

	public static SqlExceptionMessage getInstance() {
		if(instance == null) {
			instance = new SqlExceptionMessage(); 
		}
		return instance;
	}

	public String getMessage(DataAccessException dataAccessException) {
		String message = "";
		this.initializeVars();
		Properties properties = loadSqlErrorCodeProperties();
		this.getSqlErrorCode(dataAccessException);
		if(errorCode != null) {
			message = properties.getProperty(errorCode);
			if(message == null) {
				message = properties.getProperty(NO_SQL_ERROR);
				properties.setProperty("NOT_DEFINED_" + errorCode, errorMessage);
				this.saveSqlErrorCodeProperties(properties);
			}
		}

		return message;
	}
	
	public Map<String, Object> getMessage(DataAccessException dataAccessException, String title) {
		String message = getMessage(dataAccessException);
		return Util.crearNotificacion("error", title, message, 5000);
	}
	
	private Properties loadSqlErrorCodeProperties() {
		Properties properties = null;
		InputStream input = null;
		try {
			input = new ClassPathResource(PROPERTIES_FILE).getInputStream();
			properties = new Properties();
			properties.load(input);
		} catch (IOException  e) {
			e.printStackTrace();
		}finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return properties;
	}
	
	private void saveSqlErrorCodeProperties(Properties properties) {
		OutputStream output = null;
		String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		try {
			output = new FileOutputStream(new ClassPathResource(PROPERTIES_FILE).getFile());
			properties.store(output, "Codigo Nuevo Agregado " + date);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void getSqlErrorCode(DataAccessException dataAccessException) {
		if(dataAccessException.getRootCause() instanceof SQLException) {
			SQLException sqlException = (SQLException) dataAccessException.getRootCause();
			this.errorCode =  sqlException.getSQLState();
			this.errorMessage = sqlException.getMessage();
		}
	}
	
	private void initializeVars() {
		this.errorCode = null;
		this.errorMessage = null;
	}
	
	private SqlExceptionMessage() {
	}

}
