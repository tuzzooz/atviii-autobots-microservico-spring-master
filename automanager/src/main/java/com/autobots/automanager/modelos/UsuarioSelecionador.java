package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Usuario;

@Component
public class UsuarioSelecionador {
	public Usuario selecionar(List<Usuario> usuarios, Long id) {
		for (Usuario usuario : usuarios) {
			if (usuario.getId() == id) {
				return usuario;
			}
		}
		return null;
	}
}
