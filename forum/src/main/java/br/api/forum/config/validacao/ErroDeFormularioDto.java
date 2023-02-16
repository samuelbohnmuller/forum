package br.api.forum.config.validacao;

public class ErroDeFormularioDto { //JSON devolvido em informação inválida será devolvido por essa classe.

	private String campo; //Campo que ocorreu o erro.
	private String erro; //Mensagem do erro.
	
	public ErroDeFormularioDto(String campo, String erro) {
		this.campo = campo;
		this.erro = erro;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}
	
}
