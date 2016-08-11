package helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Indice {

	int fileID=0;
	private static Documento doc;
	private BufferedReader reader;

	public Indice() {
	}

	public static Documento getDoc() {
		return doc;
	}

	public static void setDoc(Documento doc) {
		Indice.doc = doc;
	}

	public void createFile(String file) throws IOException {
        int index = 0;
        File origin = new File(file);
        doc = new Documento(origin.getName());
        try {
            StringBuilder builder = new StringBuilder();
            Scanner scanner = new Scanner(origin, "UTF-8");
                while(scanner.hasNextLine()){
                    
                    String line = scanner.nextLine();
                    line = Documento.clean(line);
                    if(!line.contains("DOCNO")){
                        builder.append(line)
                               .append(System.lineSeparator());
                        continue;
                    }
                    write(index, builder.toString());
                    builder = new StringBuilder();
                    index++;
                }
                write(index, builder.toString());
        } catch (FileNotFoundException err){
        	System.out.println("Erro ao criar arquivos");
        }
	}

	/** Intersecao entre duas listas */ 
	private static LinkedList intersect( LinkedList<Posicao> list1, LinkedList<Posicao> list2 ) { 
		if ( list1 == null || list2 == null ) 
			return null; 

		LinkedList<String> answer = new LinkedList<String>(); 

		int i1 = 0, i2 = 0;   
		while ( i1 < list1.size() && i2 < list2.size() ) { 
			Posicao pos1 = list1.get(i1);
			Posicao pos2 = list2.get(i2);

			if ( pos1.getDocId() == pos2.getDocId()) { 
				answer.add(String.valueOf(pos1.getQtde()+","+pos1.getPalavra()));
				++i1; 
				++i2; 
			} else if( pos1.getDocId() < pos2.getDocId()) { 
				++i1; 
				++i2;
			} 
		} 
		return answer; 
	}
	
    private void write(int index, String content){
        
        String fileName = "DOC_ID" + index + ".txt";
        File file = new File("C:\\Users\\leandro\\workspace\\sri\\docid", fileName);
        
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.append(content);
        } catch (FileNotFoundException err){
        	System.out.println("Erro ao criar arquivos");
        }
    }
}
