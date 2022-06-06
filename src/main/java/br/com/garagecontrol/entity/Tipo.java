package br.com.garagecontrol.entity;

public enum Tipo {

	CLIENTE("Cliente"),
	FORNECEDOR("Fornecedor"),
	FUNCIONARIO("Funcionário"),
	COLABORADOR("Colaborador");
	
	private String nome;
	
	@SuppressWarnings("unused")
	private String valor;
	
	
	private Tipo (String nome) {
		this.nome = nome;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor = this.name();
	}
	
}
