package helper;

import java.util.HashMap;
import java.util.LinkedList;

public class Documento {

	String filename;
	HashMap<String, LinkedList<Posicao>> dicionario = new HashMap<>();

	public Documento(String nome) {
		filename = nome;
	}

	public void adiciona(String docId, LinkedList<Posicao> arquivo) {
		dicionario.put(docId, arquivo);
	}

	public String getFilename() {
		return filename;
	}

	public HashMap<String, LinkedList<Posicao>> getDicionario() {
		return dicionario;
	}

	public static String clean(String str){ 
		String aux; 
		str = str.toLowerCase(); 
		str = str.replaceAll("&.{2,4};", " "); 
		str = str.replaceAll("\\{\\{!\\}\\}", " "); 

		int start, end; 
		start = str.indexOf("{{"); 
		while(start != -1){ 
			end = str.indexOf("}}"); 
			aux = str.substring(start, end+2); 
			str = str.replace(aux, " "); 
			start = str.indexOf("{{"); 
		} 
		str = str.replaceAll("[^a-z0-9Á·ÈÌÛ˙‡„ı‚ÍÙ-]", " "); 
		return(str); 
	} 

}