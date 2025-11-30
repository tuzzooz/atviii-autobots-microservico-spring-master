package com.autobots.automanager.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
public class CredencialUsuarioSenha extends Credencial {
	@Column(nullable = false, unique = true)
	private String nomeUsuario;
	@Column(nullable = false)
	private String senha;
}
