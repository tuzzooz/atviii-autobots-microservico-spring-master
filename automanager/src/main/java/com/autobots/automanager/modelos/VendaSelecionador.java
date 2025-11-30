package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Venda;

@Component
public class VendaSelecionador {
	public Venda selecionar(List<Venda> vendas, Long id) {
		for (Venda venda : vendas) {
			if (venda.getId() == id) {
				return venda;
			}
		}
		return null;
	}
}
