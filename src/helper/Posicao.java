package helper;

public class Posicao {
	String arquivoId;
	int posicaoArquivo; 

	public Posicao(String arquivoId, int posicaoArquivo) {
		this.arquivoId = arquivoId;
		this.posicaoArquivo = posicaoArquivo;
	}

	public String getArquivoId() {
		return arquivoId;
	}

	public void setArquivoId(String arquivoId) {
		this.arquivoId = arquivoId;
	}

	public int getPosicaoArquivo() {
		return posicaoArquivo;
	}

	public void setPosicaoArquivo(int posicaoArquivo) {
		this.posicaoArquivo = posicaoArquivo;
	}
}