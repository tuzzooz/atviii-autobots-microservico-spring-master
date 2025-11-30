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

import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.modelos.AdicionadorLinkMercadoria;
import com.autobots.automanager.modelos.MercadoriaSelecionador;
import com.autobots.automanager.repositorios.MercadoriaRepositorio;

@RestController
@RequestMapping("/mercadoria")
public class MercadoriaControle {

	@Autowired
	private MercadoriaRepositorio repositorio;

	@Autowired
	private MercadoriaSelecionador selecionador;

	@Autowired
	private AdicionadorLinkMercadoria adicionadorLink;

	@GetMapping("/{id}")
	public ResponseEntity<Mercadoria> obterMercadoria(@PathVariable long id) {
		List<Mercadoria> mercadorias = repositorio.findAll();
		Mercadoria mercadoria = selecionador.selecionar(mercadorias, id);
		if (mercadoria == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		adicionadorLink.adicionarLink(mercadoria);
		return new ResponseEntity<>(mercadoria, HttpStatus.OK);
	}

	@GetMapping("/mercadorias")
	public ResponseEntity<List<Mercadoria>> obterMercadorias() {
		List<Mercadoria> mercadorias = repositorio.findAll();
		if (mercadorias.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		adicionadorLink.adicionarLink(mercadorias);
		return new ResponseEntity<>(mercadorias, HttpStatus.OK);
	}

	@PostMapping("/cadastro")
	public ResponseEntity<?> cadastrarMercadoria(@RequestBody Mercadoria mercadoria) {
		if (mercadoria.getId() != null) {
			return new ResponseEntity<>("Mercadoria já possui ID", HttpStatus.CONFLICT);
		}
		repositorio.save(mercadoria);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarMercadoria(@RequestBody Mercadoria atualizacao) {
		if (atualizacao.getId() == null) {
			return new ResponseEntity<>("ID não fornecido", HttpStatus.BAD_REQUEST);
		}
		Mercadoria mercadoria = repositorio.findById(atualizacao.getId()).orElse(null);
		if (mercadoria == null) {
			return new ResponseEntity<>("Mercadoria não encontrada", HttpStatus.NOT_FOUND);
		}
		repositorio.save(atualizacao);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> excluirMercadoria(@PathVariable long id) {
		Mercadoria mercadoria = repositorio.findById(id).orElse(null);
		if (mercadoria == null) {
			return new ResponseEntity<>("Mercadoria não encontrada", HttpStatus.NOT_FOUND);
		}
		repositorio.delete(mercadoria);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
