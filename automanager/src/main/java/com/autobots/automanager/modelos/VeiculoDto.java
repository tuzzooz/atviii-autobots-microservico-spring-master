package com.autobots.automanager.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VeiculoDto {
	private Long id;
	private String modelo;
	private String placa;
	private String tipo;
}
