package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Veiculo;

@Component
public class VeiculoSelecionador {
	public Veiculo selecionar(List<Veiculo> veiculos, Long id) {
		for (Veiculo veiculo : veiculos) {
			if (veiculo.getId() == id) {
				return veiculo;
			}
		}
		return null;
	}
}
