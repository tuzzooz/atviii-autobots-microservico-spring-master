package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.VendaControle;
import com.autobots.automanager.entidades.Venda;

@Component
public class AdicionadorLinkVenda implements AdicionadorLink<Venda> {

	@Override
	public void adicionarLink(Venda venda) {
		long id = venda.getId();
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(VendaControle.class)
						.obterVenda(id))
				.withSelfRel();
		venda.add(linkProprio);
	}

	@Override
	public void adicionarLink(List<Venda> vendas) {
		for (Venda venda : vendas) {
			adicionarLink(venda);
		}
	}
}
