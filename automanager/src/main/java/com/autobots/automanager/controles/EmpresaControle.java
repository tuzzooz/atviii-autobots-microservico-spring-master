package com.autobots.automanager.controles;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.modelos.AdicionadorLinkEmpresa;
import com.autobots.automanager.modelos.EmpresaSelecionador;
import com.autobots.automanager.modelos.EmpresaDto;
import com.autobots.automanager.repositorios.RepositorioEmpresa;

@RestController
@RequestMapping("/empresa")
public class EmpresaControle {

	@Autowired
	private RepositorioEmpresa repositorio;

	@Autowired
	private EmpresaSelecionador selecionador;

	@Autowired
	private AdicionadorLinkEmpresa adicionadorLink;

	@GetMapping("/{id}")
	public ResponseEntity<Empresa> obterEmpresa(@PathVariable long id) {
		List<Empresa> empresas = repositorio.findAll();
		Empresa empresa = selecionador.selecionar(empresas, id);
		if (empresa == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		adicionadorLink.adicionarLink(empresa);
		return new ResponseEntity<>(empresa, HttpStatus.OK);
	}

	@GetMapping("/empresas")
	public ResponseEntity<List<EmpresaDto>> obterEmpresas() {
		List<Empresa> empresas = repositorio.findAll();
		if (empresas.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		// Retorna apenas um DTO leve para evitar respostas enormes e repetitivas
		List<EmpresaDto> dtos = empresas.stream()
				.map(e -> new EmpresaDto(e.getId(), e.getRazaoSocial(), e.getNomeFantasia(), e.getCadastro()))
				.collect(Collectors.toList());
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}

	@PostMapping("/cadastro")
	public ResponseEntity<?> cadastrarEmpresa(@RequestBody Empresa empresa) {
		if (empresa.getId() != null) {
			return new ResponseEntity<>("Empresa já possui ID", HttpStatus.CONFLICT);
		}
		repositorio.save(empresa);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarEmpresa(@RequestBody Empresa atualizacao) {
		if (atualizacao.getId() == null) {
			return new ResponseEntity<>("ID não fornecido", HttpStatus.BAD_REQUEST);
		}
		Empresa empresa = repositorio.findById(atualizacao.getId()).orElse(null);
		if (empresa == null) {
			return new ResponseEntity<>("Empresa não encontrada", HttpStatus.NOT_FOUND);
		}
		repositorio.save(atualizacao);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> excluirEmpresa(@PathVariable long id) {
		Empresa empresa = repositorio.findById(id).orElse(null);
		if (empresa == null) {
			return new ResponseEntity<>("Empresa não encontrada", HttpStatus.NOT_FOUND);
		}
		repositorio.delete(empresa);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
