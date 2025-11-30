package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Servico;

@Component
public class ServicoSelecionador {
	public Servico selecionar(List<Servico> servicos, Long id) {
		for (Servico servico : servicos) {
			if (servico.getId() == id) {
				return servico;
			}
		}
		return null;
	}
}
