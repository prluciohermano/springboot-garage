package br.com.garagecontrol.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("deprecation")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Telefone {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="numTelefone")
	private String numero;
	
	@Column(name="tipoTelefone")
	private String tipo;
	
	@ForeignKey(name="pessoa_id")
	@ManyToOne
	private Pessoa pessoa;

}
