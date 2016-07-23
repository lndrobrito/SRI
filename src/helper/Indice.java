package helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Indice {

	HashMap<Integer, String> arquivos = new HashMap<>();
	int fileID=0;
	Documento doc;
	private BufferedReader reader;
	
	public Indice() {
	}
	
	public void indexFile(File file) throws IOException {
		doc = new Documento(file.getName());
		arquivos.put(Integer.valueOf(fileID),file.getName());
		String linha;
		int pos = 0;
		reader = new BufferedReader(new FileReader(file));
		for (String line = reader.readLine(); line != null; line = reader.readLine()) {
			linha = line.replaceAll("\\<.*?>","");
			if(!linha.isEmpty()){
				for (String _word : linha.split("\\W+")) {
					String word = _word.toLowerCase().trim();
					pos++;
					LinkedList<Posicao> idx = doc.getDicionario().get(word);
					if (idx == null) {
						idx = new LinkedList<Posicao>();
						doc.adiciona(word, idx);
					}
					idx.add(new Posicao(arquivos.get(fileID), pos));
				}
			}
		}
	}
 
	public void search(List<String> listaPalavras) {
		Set<String> resposta = new HashSet<String>();

		for (String word : listaPalavras) {
			String palavra = word.toLowerCase().trim();
			
			List<Posicao> idx = doc.getDicionario().get(palavra);
			if (idx != null) {
				for (Posicao posId : idx) {
					resposta.add(String.valueOf(posId.posicaoArquivo));
				}
			}
			System.out.print(palavra);
			for (String id : resposta) {
				System.out.print(" " + id);
			}
			System.out.println("");
		}
	}
}
