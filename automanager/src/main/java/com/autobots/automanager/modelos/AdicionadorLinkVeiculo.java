package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.VeiculoControle;
import com.autobots.automanager.entidades.Veiculo;

@Component
public class AdicionadorLinkVeiculo implements AdicionadorLink<Veiculo> {

	@Override
	public void adicionarLink(Veiculo veiculo) {
		long id = veiculo.getId();
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(VeiculoControle.class)
						.obterVeiculo(id))
				.withSelfRel();
		veiculo.add(linkProprio);
	}

	@Override
	public void adicionarLink(List<Veiculo> veiculos) {
		for (Veiculo veiculo : veiculos) {
			adicionarLink(veiculo);
		}
	}
}
