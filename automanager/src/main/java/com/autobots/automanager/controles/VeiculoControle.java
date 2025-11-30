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

import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.modelos.AdicionadorLinkVeiculo;
import com.autobots.automanager.modelos.VeiculoSelecionador;
import com.autobots.automanager.modelos.VeiculoDto;
import com.autobots.automanager.repositorios.VeiculoRepositorio;

@RestController
@RequestMapping("/veiculo")
public class VeiculoControle {

	@Autowired
	private VeiculoRepositorio repositorio;

	@Autowired
	private VeiculoSelecionador selecionador;

	@Autowired
	private AdicionadorLinkVeiculo adicionadorLink;

	@GetMapping("/{id}")
	public ResponseEntity<Veiculo> obterVeiculo(@PathVariable long id) {
		List<Veiculo> veiculos = repositorio.findAll();
		Veiculo veiculo = selecionador.selecionar(veiculos, id);
		if (veiculo == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		adicionadorLink.adicionarLink(veiculo);
		return new ResponseEntity<>(veiculo, HttpStatus.OK);
	}

	@GetMapping("/veiculos")
	public ResponseEntity<List<VeiculoDto>> obterVeiculos() {
		List<Veiculo> veiculos = repositorio.findAll();
		if (veiculos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		List<VeiculoDto> dtos = veiculos.stream()
				.map(v -> new VeiculoDto(v.getId(), v.getModelo(), v.getPlaca(), v.getTipo().name()))
				.collect(Collectors.toList());
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}

	@PostMapping("/cadastro")
	public ResponseEntity<?> cadastrarVeiculo(@RequestBody Veiculo veiculo) {
		if (veiculo.getId() != null) {
			return new ResponseEntity<>("Veiculo já possui ID", HttpStatus.CONFLICT);
		}
		repositorio.save(veiculo);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarVeiculo(@RequestBody Veiculo atualizacao) {
		if (atualizacao.getId() == null) {
			return new ResponseEntity<>("ID não fornecido", HttpStatus.BAD_REQUEST);
		}
		Veiculo veiculo = repositorio.findById(atualizacao.getId()).orElse(null);
		if (veiculo == null) {
			return new ResponseEntity<>("Veiculo não encontrado", HttpStatus.NOT_FOUND);
		}
		repositorio.save(atualizacao);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> excluirVeiculo(@PathVariable long id) {
		Veiculo veiculo = repositorio.findById(id).orElse(null);
		if (veiculo == null) {
			return new ResponseEntity<>("Veiculo não encontrado", HttpStatus.NOT_FOUND);
		}
		repositorio.delete(veiculo);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
