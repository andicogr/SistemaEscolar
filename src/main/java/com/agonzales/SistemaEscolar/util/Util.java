package com.agonzales.SistemaEscolar.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;



public class Util {
	
	public static Map<String, Object> crearNotificacion(String type, String title, String text, Integer delay){
		Map<String, Object> notifiaccion = new HashMap<String, Object>();
		notifiaccion.put("styling", "bootstrap3");
		notifiaccion.put("type", type);
		notifiaccion.put("title", title);
		notifiaccion.put("text", text);
		notifiaccion.put("delay", delay);

		return notifiaccion;
	}

	public static Map<String, Object> crearNotificacion(String type, String title, String text){
		return crearNotificacion(type, title, text, 3000);
	}

	public static Map<String, Object> crearNotificacionError(String title, String text){
		return crearNotificacion("error", title, text);
	}
	
	public static Map<String, Object> crearNotificacionInfo(String title, String text){
		return crearNotificacion("info", title, text);
	}
	
	public static Map<String, Object> crearNotificacionSuccess(String title, String text){
		return crearNotificacion("success", title, text);
	}

	public static Map<String, Object> notificacionErrorDelSistema(){
		return crearNotificacion("error", "Error del Sistema", "Ocurrio un error inesperado, por favor comuniquese con el administrador del sistema.", 5000);
	}

	public static String OrderByPagination(Map<String, Object> valor, Integer columna, String orden){
		String query="";
		for (Map.Entry<String, Object> entry : valor.entrySet()) {
			if (entry.getKey().equals(columna.toString())){
				if(orden.toUpperCase().equals("ASC")){
					query += " order by  "+entry.getValue()+" asc" ;
				}else{
					query += " order by  "+entry.getValue()+" desc " ;
				}
			}
			
		}
		
		return query;
	}
	
	public static String querySelect(Map<String, Object> columnas){
		String query = "Select ";

		for (Map.Entry<String, Object> entry : columnas.entrySet()) {
				query += " " + entry.getValue() + ", ";
		}
		query = query.substring(0, query.length() - 2) + " ";
		return query;
	}
	
	public static String obtenerNombreEstado(boolean activo){
		if(activo){
			return Constantes.ESTADO_ACTIVO_ETIQUETA;
		}
		return Constantes.ESTADO_INACTIVO_ETIQUETA;
	}
	

	public static boolean getBooleanValueEstado(String estado){
		if(Constantes.ESTADO_ACTIVO_ETIQUETA.equalsIgnoreCase(estado)){
			return true;
		}
		return false;
	}
	
	public static String formatDateTime(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constantes.FORMATO_FECHA_DDMMYYYY_HHMMSS);
		return simpleDateFormat.format(date);
	}
	
	public static String formatDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constantes.FORMATO_FECHA_DDMMYYYY);
		return simpleDateFormat.format(date);
	}
	
	public static Date formatDateHTML(String fechaHtml) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constantes.FORMATO_FECHA_DDMMYYYY);
		Date fechaFormateada = null;
		try {
			fechaFormateada = simpleDateFormat.parse(fechaHtml);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fechaFormateada;
	}
	
	public static String query(List<Map<String, Object>> lista) {
		String query = "";
		for (Map<String, Object> map : lista) {
			for (Map.Entry<String, Object> entry : map.entrySet()) {
					if (query.equals(""))
						query = " where " + entry.getValue();
					else 
						query =query + " and " +  entry.getValue();					
			}
		}
		return query;
	}

	public static String toMD5(String entrada){
		try{
			MessageDigest d=MessageDigest.getInstance("MD5");
			d.update(entrada.getBytes());
			byte[] md5=d.digest();
			StringBuffer result=new StringBuffer();
			for(byte b : md5){
				int low=b & 0x0F;
				int high=b & 0xF0;
				result.append(Integer.toHexString(high).substring(0,1));
				result.append(Integer.toHexString(low));
			}
			return result.toString();
		}
		catch(NoSuchAlgorithmException e){
		}
		return null;
	}

	public static boolean vacio(String cadena){
		return cadena == null || cadena.equals("");
	}

	@SuppressWarnings("unchecked")
	public static String crearJson(Map<? extends Object,? extends Object> objeto){
		Set<Object> keys=(Set<Object>) objeto.keySet();
		StringBuffer sb=new StringBuffer("{");
		for(Object key : keys){
			if(sb.length() > 1){
				sb.append(",");
			}
			sb.append("\"" + key.toString() + "\"");
			sb.append(':');
			sb.append("\"" + objeto.get(key).toString() + "\"");
		}
		sb.append("}");
		return sb.toString();
	}

	public static String quitarAcentos(String cadena){
		return Normalizer.normalize(cadena,Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+","");
	}

	/*public static String getTexto(String archivo){
		StringBuffer sb=new StringBuffer();
		try{
			FileReader fr=new FileReader(new File(Config.getPropiedad("juergologo.textos") + File.separator + archivo));
			BufferedReader br=new BufferedReader(fr);
			String linea;
			while((linea=br.readLine()) != null){
				sb.append(linea).append("<br/>");
			}
			br.close();
			fr.close();
		}
		catch(FileNotFoundException e){
			log.error("No se entontro el archivo " + archivo);
			return null;
		}
		catch(IOException e){
			log.error("Error leyendo archivo " + archivo,e);
			return null;
		}
		return sb.toString();
	}*/

	public static int diferenciasDeFechas(Date fechaInicial,Date fechaFinal){
		DateFormat df=DateFormat.getDateInstance(DateFormat.MEDIUM);
		String fechaInicioString=df.format(fechaInicial);
		try{
			fechaInicial=df.parse(fechaInicioString);
		}
		catch(ParseException ex){
		}
		String fechaFinalString=df.format(fechaFinal);
		try{
			fechaFinal=df.parse(fechaFinalString);
		}
		catch(ParseException ex){
		}
		long fechaInicialMs=fechaInicial.getTime();
		long fechaFinalMs=fechaFinal.getTime();
		long diferencia=fechaFinalMs - fechaInicialMs;
		double dias=Math.floor(diferencia / (1000 * 60 * 60 * 24));
		return ((int) dias);
	}

	public static String getNombreMes(int mes){
		String nombreMes=null;
		switch(mes){
		case 1:
			nombreMes="Enero";
			break;
		case 2:
			nombreMes="Febrero";
			break;
		case 3:
			nombreMes="Marzo";
			break;
		case 4:
			nombreMes="Abril";
			break;
		case 5:
			nombreMes="Mayo";
			break;
		case 6:
			nombreMes="Junio";
			break;
		case 7:
			nombreMes="Julio";
			break;
		case 8:
			nombreMes="Agosto";
			break;
		case 9:
			nombreMes="Setiembre";
			break;
		case 10:
			nombreMes="Octubre";
			break;
		case 11:
			nombreMes="Noviembre";
			break;
		case 12:
			nombreMes="Diciembre";
			break;
		default:
			nombreMes="Enero";
			break;
		}
		return nombreMes;
	}

	public static Date sumarFechasDias(Date fch,int dias){
		Calendar cal=new GregorianCalendar();
		cal.setTimeInMillis(fch.getTime());
		cal.add(Calendar.DATE,dias);
		return new Date(cal.getTimeInMillis());
	}

	public static long diferenciaFechasHoras(Date fechaInicio,Date fechaFin){
		Calendar cal1=Calendar.getInstance();
		Calendar cal2=Calendar.getInstance();
		// Establecer las fechas
		cal1.setTime(fechaInicio);
		cal2.setTime(fechaFin);
		// conseguir la representacion de la fecha en milisegundos
		long milis1=cal1.getTimeInMillis();
		long milis2=cal2.getTimeInMillis();
		// calcular la diferencia en milisengundos
		long diff=milis2 - milis1;
		// calcular la diferencia en horas
		long diffHours=diff / (60 * 60 * 1000);
		return diffHours;
	}
	
	@SuppressWarnings("unused")
	private static int calcularEdad(Date fechaNacimiento){
		int edad;
		 Calendar fechaNacimientoCalendar=Calendar.getInstance();
		 fechaNacimientoCalendar.setTime(fechaNacimiento);
         Calendar fechaHoy=Calendar.getInstance();  
         fechaHoy.get(Calendar.YEAR);
		  DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	      System.out.println("Fecha nacimiento:"+dateFormat.format(fechaNacimiento));
	      System.out.println("Fecha nacimiento:"+dateFormat.format(fechaHoy));
	      edad=fechaHoy.get(Calendar.YEAR)-fechaNacimientoCalendar.get(Calendar.YEAR);
	      if((fechaNacimientoCalendar.get(Calendar.MONTH)-fechaHoy.get(Calendar.MONTH))>0){
	        edad--;
	      }
	     else if((fechaNacimientoCalendar.get(Calendar.MONTH)-fechaHoy.get(Calendar.MONTH))==0){
	       if((fechaNacimientoCalendar.get(Calendar.DATE)-fechaHoy.get(Calendar.DATE))>0){
	       edad--;
	       }
	 }
		return edad;
		
	}
	
	public static String formatearMontos(Float monto){

		DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
		simbolos.setDecimalSeparator('.');
		   //float d = 300.99f;
	        DecimalFormat f = new DecimalFormat("#####.00",simbolos);  // this will helps you to always keeps in two decimal places
	        System.out.println(f.format(monto));
		
		   return f.format(monto);
	}
	
	
	public static String subeArchivo(MultipartFile archivoSubir, HttpServletRequest request, String prefijo) {
		String resultado = "error";
		String archivo = archivoSubir.getOriginalFilename();
		Integer indexExtencion =  archivo.lastIndexOf(".");
		String extencion = archivo.substring(indexExtencion);
		String fileName = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		if (archivoSubir.getSize() > 0) {
			try {
				String nombreArchivo = prefijo + extencion;
				inputStream = archivoSubir.getInputStream();
				String archivosDir = Config.getPropiedad(Constantes.PROPERTIES_IMAGENES_PATH);
				fileName = archivosDir + File.separator + nombreArchivo;
				outputStream = new FileOutputStream(fileName);
				int readBytes = 0;
				byte[] buffer = new byte[10000];
				while ((readBytes = inputStream.read(buffer, 0, 10000)) != -1) {
					outputStream.write(buffer, 0, readBytes);
				}
				outputStream.close();
				inputStream.close();
				resultado = nombreArchivo;
			} catch (IOException e) {
				return resultado = "error";
			}
		} else {
			resultado = "vacio";
		}
		return resultado;
	}
	
	/**
	 * 
	 * @param nombreArchivo Nombre del archivo en funcion a la noticia
	 * @return 0 = no existe la imagen, 1 = se elimno la imagen, 2 = no se elimino la imagen 
	 */
	public static Integer eliminarArchivo(String nombreArchivo) {
		Integer resultado = 0;
		String archivosDir = Config.getPropiedad(Constantes.PROPERTIES_IMAGENES_PATH);

		File archivo = new File(archivosDir + File.separator + nombreArchivo);
		if (archivo.exists()) {
			if (archivo.delete()) {
				resultado = 1;
			} else {
				resultado = 2;
			}
		} else {
			resultado = 0;
		}
		return resultado;
	}

	public static String otorgarNombreImagen(HttpServletRequest request,MultipartFile mImagen, String nombreOriginalImagen, String perfijoImg){
		String nombreImagen = "";
		if(mImagen.getSize() > 0){
			String[] inicialesTitulo = nombreOriginalImagen.split(" ");
			nombreImagen = perfijoImg;
			//String numeroRandom = RandomStringUtils.randomNumeric(4);
			String numeroRandom = null;
			for(String x : inicialesTitulo){
				nombreImagen += x.substring(0, 1);
			}
			nombreImagen += numeroRandom;
			
			nombreImagen  = subeArchivo(mImagen, request, nombreImagen);
		}
		return nombreImagen;
	}
	
	public static String otorgarNombreImagen(HttpServletRequest request,MultipartFile mImagen, String perfijoImg){
		return otorgarNombreImagen(request, mImagen, mImagen.getOriginalFilename(), perfijoImg);
	}

	public static Map<String, Object> reordenarColumnasPorConfiguracionMultiCompania(Map<String, Object> columnas){
		Map<String, Object> columnasSinCompania = new HashMap<String, Object>();

		Iterator<Map.Entry<String, Object>> iterador = columnas.entrySet().iterator();
		
		boolean primerRegistro = true;
		while (iterador.hasNext()){
			Map.Entry<String, Object> map = iterador.next();
			if(!primerRegistro){
				Integer numeroColumn = Integer.parseInt(map.getKey()) - 1;
				columnasSinCompania.put(numeroColumn.toString(), map.getValue());
			}
			primerRegistro = false;
		}

		return columnasSinCompania;
	}
	
}
