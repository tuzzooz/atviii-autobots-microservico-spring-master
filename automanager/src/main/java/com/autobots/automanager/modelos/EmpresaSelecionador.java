package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Empresa;

@Component
public class EmpresaSelecionador {
	public Empresa selecionar(List<Empresa> empresas, Long id) {
		for (Empresa empresa : empresas) {
			if (empresa.getId() == id) {
				return empresa;
			}
		}
		return null;
	}
}
