package helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.lucene.analysis.br.BrazilianAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

public class Config {
	
	private IndexWriter index;
	private IndexWriterConfig idxConfig ;

	
	public Config() throws IOException {
	 	Directory dir = new RAMDirectory(); 
	 	idxConfig = new IndexWriterConfig(new BrazilianAnalyzer()); 
	 	index = new IndexWriter(dir, idxConfig);
	}
	
	public void lerDocs(String pasta) throws IOException{ 
 		File arquivos[]; 
 		File diretorio = new File(pasta); 
 		arquivos = diretorio.listFiles(); 
 		for(int i = 0; i < arquivos.length; i++){ 
 			String texto = new String(Files.readAllBytes(arquivos[i].toPath())); 
 			Document doc = new Document(); 
 			doc.add(new StringField("nomearq", arquivos[i].getName(), Field.Store.YES)); 
 			doc.add(new TextField("texto", texto, Field.Store.YES)); 
 			index.addDocument(doc); 
 		} 
 	} 
 	 
 	public void luceneTesteBusca(String busca, int n, boolean BM25) throws IOException, ParseException{ 
 		QueryParser qp = new QueryParser("texto", new BrazilianAnalyzer()); 
 		IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(index));
 		if(BM25){
 			searcher.setSimilarity(new BM25Similarity());
 			idxConfig.setSimilarity(new BM25Similarity());
 		}
 		Query query = qp.parse(busca); 
 		TopDocs topDocs = searcher.search(query, n); 
 		ScoreDoc[] hits = topDocs.scoreDocs; 
 		for (int i = 0; i < hits.length; i++) { 
 			Document doc = searcher.doc(hits[i].doc); 
 			System.out.println(doc.get("nomearq")); 
 		} 
 	}
 	
 	public static void deleteFiles(File folder) {
 	    File[] files = folder.listFiles();
 	    if(files!=null) { //some JVMs return null for empty dirs
 	        for(File f: files) {
 	            if(f.isDirectory()) {
 	                deleteFiles(f);
 	            } else {
 	                f.delete();
 	            }
 	        }
 	    }
 	    folder.delete();
 	}

}
