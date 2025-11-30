package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.UsuarioControle;
import com.autobots.automanager.entidades.Usuario;

@Component
public class AdicionadorLinkUsuario implements AdicionadorLink<Usuario> {

	@Override
	public void adicionarLink(Usuario usuario) {
		long id = usuario.getId();
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(UsuarioControle.class)
						.obterUsuario(id))
				.withSelfRel();
		usuario.add(linkProprio);
	}

	@Override
	public void adicionarLink(List<Usuario> usuarios) {
		for (Usuario usuario : usuarios) {
			adicionarLink(usuario);
		}
	}
}
