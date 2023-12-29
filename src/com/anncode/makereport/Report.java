package com.anncode.makereport;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * <h1>Report</h1>
 * Librería que permite realizar reportes
 *
 * <p>
 * Esta  librería nos permite generar reportes de las Movies, Series junto con sus chapters y libros
 * a  excepción de los magazines que tienen la restricción de no poderse visualizar
 *
 * @author Andres F.B.S
 * @version 1.1
 * @since 2019
 */

public class Report {

	/**
	 * <h2> Variables </h2>
	 * {@code String nameFile} almacena el nombre del archivo/reporte
	 * {@code String title} contiene el titulo que lleve el archivo/reporte
	 * {@code String content} almacena el contenido del reporte
	 * {@code String extension} contiene la extensión  del reporte
	 */
	
	private String nameFile;
	private String title;
	private String content;
	private String extension;
	
	public String getNameFile() {
		return nameFile;
	}
	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public void makeReport() {
		if ( (getNameFile() != null) && (getTitle() != null) && (getContent() != null) ) {
			//Crear el archivo
			try {
				
				File file = new File(getNameFile()+"."+getExtension());
				FileOutputStream fos = new FileOutputStream(file);
				OutputStreamWriter osw = new OutputStreamWriter(fos);
				BufferedWriter bw = new BufferedWriter(osw);
				bw.write(getContent());
				bw.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		} else {
			System.out.println("Ingresa los datos del archivo");
		}
	}
	
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}

}










