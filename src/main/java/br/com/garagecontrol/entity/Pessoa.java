package br.com.garagecontrol.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="nomeCliente", nullable = true)
	private String nome;
	
	@Column(name="identCliente", nullable = true)
	private String identidade;
	
	@Column(name="cpfCliente", nullable = true)
	private String cpf;
	
	@Column(name="cepCliente", nullable = true)
	private String cep;
	
	@Column(name="ruaCliente", nullable = true)
	private String rua;
	
	@Column(name="numeroCliente", nullable = true)
	private String numero;
	
	@Column(name="bairroCliente", nullable = true)
	private String bairro;
		
	@Column(name="compCliente", nullable = true)
	private String comp;
	
	@Column(name="cidadeCliente", nullable = true)
	private String cidade;
	
	@Column(name="ufCliente", nullable = true)
	private String uf;
	
	@Column(name="sexoCliente", nullable = true)
	private String sexopessoa;
	
	@OneToMany(mappedBy="pessoa", cascade = CascadeType.ALL)
	private List<Telefone> telefones;

}
