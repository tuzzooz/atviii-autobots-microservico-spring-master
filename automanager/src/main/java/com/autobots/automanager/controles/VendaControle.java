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

import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.modelos.AdicionadorLinkVenda;
import com.autobots.automanager.modelos.VendaSelecionador;
import com.autobots.automanager.modelos.VendaDto;
import com.autobots.automanager.repositorios.VendaRepositorio;

@RestController
@RequestMapping("/venda")
public class VendaControle {

	@Autowired
	private VendaRepositorio repositorio;

	@Autowired
	private VendaSelecionador selecionador;

	@Autowired
	private AdicionadorLinkVenda adicionadorLink;

	@GetMapping("/{id}")
	public ResponseEntity<Venda> obterVenda(@PathVariable long id) {
		List<Venda> vendas = repositorio.findAll();
		Venda venda = selecionador.selecionar(vendas, id);
		if (venda == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		adicionadorLink.adicionarLink(venda);
		return new ResponseEntity<>(venda, HttpStatus.OK);
	}

	@GetMapping("/vendas")
	public ResponseEntity<List<VendaDto>> obterVendas() {
		List<Venda> vendas = repositorio.findAll();
		if (vendas.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		List<VendaDto> dtos = vendas.stream()
				.map(v -> new VendaDto(
						v.getId(),
						v.getIdentificacao(),
						v.getCadastro(),
						v.getCliente() != null ? v.getCliente().getId() : null,
						v.getFuncionario() != null ? v.getFuncionario().getId() : null,
						v.getVeiculo() != null ? v.getVeiculo().getId() : null))
				.collect(Collectors.toList());
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}

	@PostMapping("/cadastro")
	public ResponseEntity<?> cadastrarVenda(@RequestBody Venda venda) {
		if (venda.getId() != null) {
			return new ResponseEntity<>("Venda já possui ID", HttpStatus.CONFLICT);
		}
		repositorio.save(venda);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarVenda(@RequestBody Venda atualizacao) {
		if (atualizacao.getId() == null) {
			return new ResponseEntity<>("ID não fornecido", HttpStatus.BAD_REQUEST);
		}
		Venda venda = repositorio.findById(atualizacao.getId()).orElse(null);
		if (venda == null) {
			return new ResponseEntity<>("Venda não encontrada", HttpStatus.NOT_FOUND);
		}
		repositorio.save(atualizacao);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> excluirVenda(@PathVariable long id) {
		Venda venda = repositorio.findById(id).orElse(null);
		if (venda == null) {
			return new ResponseEntity<>("Venda não encontrada", HttpStatus.NOT_FOUND);
		}
		repositorio.delete(venda);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
