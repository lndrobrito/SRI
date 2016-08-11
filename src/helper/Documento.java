package helper;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Documento {

	String filename;
	HashMap<String, String> invertedIndex = new HashMap<>();
	Map <String,String> dicionario = new HashMap<String,String>();


	public Documento(String nome) {
		filename = nome;
	}


	/*
	 * Cria indice com token;docId,frequencia
	 */
	public void insert( String token, int docID) {
		int freq = 0;
		if(token.length()>1){
			if(!invertedIndex.containsKey(token)){ 
				freq = 1;
				Posicao pos = new Posicao(token, docID, freq);
				String temp="(" +docID+ "," +freq +")"; 
				invertedIndex.put(token, temp); 
			} else{
				String temp=invertedIndex.get(token); 
				if(temp.contains(String.valueOf(docID))){ 

					String[] parseTemp=temp.split("\\)"); 
					for(int x=0; x<parseTemp.length;x++){ 
						if(parseTemp[x].contains(String.valueOf(docID))){ 

							String temp1 = parseTemp[x];  
							String[] freqTemp = temp1.split("\\,");  
							freq=Integer.parseInt(freqTemp[1]);  
							freq++; 

							String temp2="("+docID+","+freq; 
							temp=temp.replace(temp1,temp2); 
							invertedIndex.put(token, temp); 
						}	 
					} 
				} 
				else{ 
					freq=1;
					Posicao posNew = new Posicao(token, docID, freq);
					String tempNew="("+docID+","+freq+")"; 
					invertedIndex.put(token,temp + tempNew); 
				} 
			}
		}
	}
	
	public void recuperaInvertedIndex(String consulta) throws FileNotFoundException, UnsupportedEncodingException{
		int N = 1000; 
		
		for (Entry<String, String> entry : invertedIndex.entrySet()) {
			dicionario.put(entry.getKey(),entry.getValue());
		}
		
			int freq=0;  
			HashMap <String,String> temp = new HashMap<String,String>(); 
			
			StringTokenizer tok = new StringTokenizer( consulta );
			while ( tok.hasMoreTokens() ) { 
				String token = tok.nextToken();

				if(!temp.containsKey(token)) 
				{ 
					freq=1; 
					String value = dicionario.get(token);     
					temp.put(token, value); 
				} 
				else{  
					freq = freq +1; 
				} 
			} // end of for 
			recuperaScore(temp, N, freq);  

	}	

	public void recuperaScore(HashMap<String,String> h2, int NOfDoc, int qfi) 
 	{ 
 			Set<String> keys = h2.keySet(); 
         	Iterator<String> itr = keys.iterator(); 
   
  	        String key; 
  	        String value; 
         	Hashtable <String,Double> temp = new Hashtable<String,Double>();
			TreeMap<String,Double> ordena = new TreeMap<String,Double>(Collections.reverseOrder()); 

 	        while(itr.hasNext()) 
 	        { 

 	        	key = (String)itr.next(); 
 	        	value = (String)h2.get(key); 

 	        	String[] docId_freque = value.split("\\)"); 
 	        	int ni = docId_freque.length; 
 	        	for(int j=0;j<docId_freque.length;j++) 
 	        	{ 
 	        		String[] s1 = docId_freque[j].split("\\,"); 
 	        		String docId = s1[0].substring(1); 
 	        		int fi= Integer.parseInt(s1[1]); 
 	        		double score = getScoreBMS25(NOfDoc,ni,qfi,fi); 
 	        		String saida = "rank | " + docId + " | "+ key+ " |"; 

 	        		if(!temp.containsKey(saida)) 
 	        		{ 
 	        			ordena.put(saida, score); 
 	        		} 
 	        		else 
 	        		{ 
 	        			Double aux = ordena.get(saida); 
 	        			score = score + aux; 
 	        			ordena.put(saida, score);		 

 	        		} 
 	        	} 
 	        } 
 	        

			Set set = ordena.entrySet(); 
			Iterator i = set.iterator(); 
			int rank = 0; 
			System.out.println("rank | docId | palavra | score");
			while(i.hasNext())  
			{ 
				Map.Entry me = (Map.Entry) i.next(); 
				rank++; 
				String result = (String) me.getKey(); 
				double scores = (double) me.getValue(); 
				result = result.replace("rank", String.valueOf(rank)); 

				System.out.println(result); 
			} 
 	} 

	public double getScoreBMS25(int N, int ni, int qfi, int fi){ 
		int ri=0; 
		int R=0; 
		double k1 = 1.2; 
		double k2 = 100; 
		double eq10=0;
		
		double eq1 = 	(ri + 0.5) / (R - ri + 0.5); 
		double eq2 = 	(ni - ri + 0.5) / (N - ni - R + ri + 0.5); 
		double eq3 = 	(k1 + 1) * fi; 
		double eq4 = 	fi; 
		double eq5 = 	(k2 + 1) * qfi; 
		double eq6 = 	(k2 + qfi); 
		double eq7 = 	eq1 / eq2; 
		double eq8 = 	eq3 / eq4; 
		double eq9 = 	eq5 / eq6; 
		eq10 = 	Math.log(eq7 * eq8 * eq9); 
		return eq10; 
	} 

	public static String clean(String str){ 
		str = str.replaceAll("&.{2,4};", " ")
				.replaceAll("\\{\\{!\\}\\}", " ")
				.replaceAll("[ãâàáä]", "a")   
				.replaceAll("[êèéë]", "e")   
				.replaceAll("[îìíï]", "i")   
				.replaceAll("[õôòóö]", "o")   
				.replaceAll("[ûúùü]", "u")   
				.replaceAll("[ÃÂÀÁÄ]", "A")   
				.replaceAll("[ÊÈÉË]", "E")   
				.replaceAll("[ÎÌÍÏ]", "I")   
				.replaceAll("[ÕÔÒÓÖ]", "O")   
				.replaceAll("[ÛÙÚÜ]", "U")   
				.replace('ç', 'c')   
				.replace('Ç', 'C')   
				.replace('ñ', 'n')   
				.replace('Ñ', 'N')
				.replaceAll("!", " ")
				.replaceAll("=", " ")
				.replaceAll ("\\[\\´\\`\\?!\\@\\#\\$\\%\\¨\\*"," ")
				.replaceAll("\\(\\)\\=\\{\\}\\[\\]\\~\\^\\]"," ")
				.replaceAll("\\p{P}"," ")
				.replaceAll("\\d|\\|"," ")
				.replaceAll("[\\.\\;\\-\\_\\+\\'\\ª\\º\\:\\;\\/]"," ");

		return(str); 
	}


	public HashMap<String, String> getInvertedIndex() {
		return invertedIndex;
	}


	public void setInvertedIndex(HashMap<String, String> invertedIndex) {
		this.invertedIndex = invertedIndex;
	}


	public Map<String, String> getDicionario() {
		return dicionario;
	}


	public void setDicionario(Map<String, String> dicionario) {
		this.dicionario = dicionario;
	} 

}