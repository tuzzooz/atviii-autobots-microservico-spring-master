package com.autobots.automanager.controles;

import java.util.List;

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

import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.modelos.AdicionadorLinkServico;
import com.autobots.automanager.modelos.ServicoSelecionador;
import com.autobots.automanager.repositorios.ServicoRepositorio;

@RestController
@RequestMapping("/servico")
public class ServicoControle {

	@Autowired
	private ServicoRepositorio repositorio;

	@Autowired
	private ServicoSelecionador selecionador;

	@Autowired
	private AdicionadorLinkServico adicionadorLink;

	@GetMapping("/{id}")
	public ResponseEntity<Servico> obterServico(@PathVariable long id) {
		List<Servico> servicos = repositorio.findAll();
		Servico servico = selecionador.selecionar(servicos, id);
		if (servico == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		adicionadorLink.adicionarLink(servico);
		return new ResponseEntity<>(servico, HttpStatus.OK);
	}

	@GetMapping("/servicos")
	public ResponseEntity<List<Servico>> obterServicos() {
		List<Servico> servicos = repositorio.findAll();
		if (servicos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		adicionadorLink.adicionarLink(servicos);
		return new ResponseEntity<>(servicos, HttpStatus.OK);
	}

	@PostMapping("/cadastro")
	public ResponseEntity<?> cadastrarServico(@RequestBody Servico servico) {
		if (servico.getId() != null) {
			return new ResponseEntity<>("Servico já possui ID", HttpStatus.CONFLICT);
		}
		repositorio.save(servico);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarServico(@RequestBody Servico atualizacao) {
		if (atualizacao.getId() == null) {
			return new ResponseEntity<>("ID não fornecido", HttpStatus.BAD_REQUEST);
		}
		Servico servico = repositorio.findById(atualizacao.getId()).orElse(null);
		if (servico == null) {
			return new ResponseEntity<>("Servico não encontrado", HttpStatus.NOT_FOUND);
		}
		repositorio.save(atualizacao);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> excluirServico(@PathVariable long id) {
		Servico servico = repositorio.findById(id).orElse(null);
		if (servico == null) {
			return new ResponseEntity<>("Servico não encontrado", HttpStatus.NOT_FOUND);
		}
		repositorio.delete(servico);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
