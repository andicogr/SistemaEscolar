package com.agonzales.SistemaEscolar.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class Config {
	private static Properties propiedades;

	private static final int NIVEL=10;

	public static String getPropiedad(String propiedad){
		cargarConfiguracion();
		return propiedades.getProperty(propiedad);
	}

	public static int getPropiedadInt(String propiedad){
		cargarConfiguracion();
		String prop=propiedades.getProperty(propiedad);
		int valor=0;
		if(prop != null)
			try{
				valor=Integer.parseInt(prop);
			}
			catch(NumberFormatException numberformatexception){
			}
		return valor;
	}

	public static boolean getPropiedadBoolean(String propiedad){
		return getPropiedadBoolean(propiedad,false);
	}

	public static boolean getPropiedadBoolean(String propiedad,boolean porDefecto){
		cargarConfiguracion();
		String prop=propiedades.getProperty(propiedad);
		if(prop != null){
			prop=prop.trim();
			if(prop.equalsIgnoreCase("true") || prop.equalsIgnoreCase("yes") || prop.equalsIgnoreCase("verdad") || prop.equalsIgnoreCase("si"))
				return true;
		}
		return porDefecto;
	}

	private static void cargarConfiguracion(){
		cargarConfiguracion("");
	}

	public static void cargarConfiguracion(InputStream archivo){
		if(propiedades != null)
			return;
		if(archivo == null)
			throw new RuntimeException("No se pudo hallar el archivo de configuraci\363n.");
		try{
			propiedades=new Properties();
			propiedades.load(archivo);
			for(Enumeration<?> e=propiedades.propertyNames();e.hasMoreElements();){
				String key=(String) e.nextElement();
				String valor=interpolar(key,1);
				if(valor != null)
					propiedades.setProperty(key,valor);
			}
		}
		catch(IOException ioe){
			throw new RuntimeException("No se puede cargar la configuraci\363n.",ioe);
		}
	}

	public static void cargarConfiguracion(String archivo){
		if(propiedades != null)
			return;
		String rutaArchivo=System.getProperty("chimera.configuracion");
		try{
			InputStream is;
			if(archivo != null && !archivo.equals("")){
				is=new FileInputStream(archivo);
				// elArchivo = new File(archivo);
			}
			else if(rutaArchivo != null){
				is=new FileInputStream(rutaArchivo);
				// elArchivo = new File(rutaArchivo);
			}
			else{
				is=Config.class.getResourceAsStream("/systemConf.properties");
			}
			cargarConfiguracion(is);
		}
		catch(IOException ioe){
			throw new RuntimeException("No se puede cargar la configuraci\363n.",ioe);
		}
	}

	private static String interpolar(String key,int nivel){
		if(nivel > NIVEL)
			throw new IllegalArgumentException((new StringBuilder("Demasiados niveles de recursi\363n para la propiedad ")).append(key).toString());
		String valor=propiedades.getProperty(key);
		int desde=0;
		StringBuffer resultado=null;
		int fin;
		for(;desde < valor.length();desde=fin + 1){
			int inicio=valor.indexOf("${",desde);
			if(inicio < 0)
				break;
			fin=valor.indexOf("}",inicio);
			if(fin < 0)
				break;
			String prop=valor.substring(inicio + 2,fin);
			if(resultado == null)
				resultado=new StringBuffer(valor.substring(desde,inicio));
			else
				resultado.append(valor.substring(desde,inicio));
			if(propiedades.containsKey(prop)){
				String nValor=interpolar(prop,nivel + 1);
				if(nValor != null){
					resultado.append(nValor);
					propiedades.setProperty(prop,nValor);
				}
				else{
					resultado.append(propiedades.getProperty(prop));
				}
			}
		}
		if(resultado != null && desde < valor.length())
			resultado.append(valor.substring(desde));
		return resultado != null ? resultado.toString() : null;
	}

}
