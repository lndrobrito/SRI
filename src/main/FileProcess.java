package main;

import java.io.File;
import java.io.IOException;

import helper.*;

public class FileProcess {
	
	private static String dir = "C:\\Users\\leandro\\workspace\\sri\\docid";
	public static void main(String[] args) throws IOException {

		try {
			Indice idx = new Indice();
			Config conf = new Config();
			boolean isBM25 = false;
			new File(dir).mkdirs();
			idx.createFile("C:\\Users\\leandro\\workspace\\sri\\input\\ptwiki-v2.trec");
			conf.lerDocs(dir);
			conf.luceneTesteBusca("arqueologia -egito",5,isBM25);
			conf.deleteFiles(new File(dir)); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
