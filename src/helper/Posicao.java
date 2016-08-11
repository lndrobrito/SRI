package helper;

public class Posicao implements Comparable<Posicao>{
	String palavra;
	int docId;
	int qtde; 

	public Posicao(String palavra, int docId, int qtde) {
		this.palavra = palavra;
		this.docId = docId;
		this.qtde = qtde;
	}

	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public int getQtde() {
		return qtde;
	}

	public void setQtde(int qtde) {
		this.qtde = qtde;
	}
	
	public String getPalavra() {
		return palavra;
	}

	public void setPalavra(String palavra) {
		this.palavra = palavra;
	}

	@Override
	public String toString() {
		return "("+ docId + "," + qtde + ")";
	}

	public int compareTo( Posicao other ) { 
		return Double.compare( other.docId, docId ); 
	} 

}