package com.autobots.automanager.modelos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmpresaDto {
	private Long id;
	private String razaoSocial;
	private String nomeFantasia;
	private Date cadastro;
}

