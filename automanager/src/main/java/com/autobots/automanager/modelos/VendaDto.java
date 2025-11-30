package com.autobots.automanager.modelos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VendaDto {
	private Long id;
	private String identificacao;
	private Date cadastro;
	private Long clienteId;
	private Long funcionarioId;
	private Long veiculoId;
}

