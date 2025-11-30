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

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelos.AdicionadorLinkUsuario;
import com.autobots.automanager.modelos.UsuarioSelecionador;
import com.autobots.automanager.repositorios.UsuarioRepositorio;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControle {

	@Autowired
	private UsuarioRepositorio repositorio;

	@Autowired
	private UsuarioSelecionador selecionador;

	@Autowired
	private AdicionadorLinkUsuario adicionadorLink;

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> obterUsuario(@PathVariable long id) {
		List<Usuario> usuarios = repositorio.findAll();
		Usuario usuario = selecionador.selecionar(usuarios, id);
		if (usuario == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		adicionadorLink.adicionarLink(usuario);
		return new ResponseEntity<>(usuario, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Usuario>> obterUsuarios() {
		List<Usuario> usuarios = repositorio.findAll();
		if (usuarios.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		adicionadorLink.adicionarLink(usuarios);
		return new ResponseEntity<>(usuarios, HttpStatus.OK);
	}

	@PostMapping("/cadastro")
	public ResponseEntity<?> cadastrarUsuario(@RequestBody Usuario usuario) {
		if (usuario.getId() != null) {
			return new ResponseEntity<>("Usuario já possui ID", HttpStatus.CONFLICT);
		}
		repositorio.save(usuario);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarUsuario(@RequestBody Usuario atualizacao) {
		if (atualizacao.getId() == null) {
			return new ResponseEntity<>("ID não fornecido", HttpStatus.BAD_REQUEST);
		}
		Usuario usuario = repositorio.findById(atualizacao.getId()).orElse(null);
		if (usuario == null) {
			return new ResponseEntity<>("Usuario não encontrado", HttpStatus.NOT_FOUND);
		}
		repositorio.save(atualizacao);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> excluirUsuario(@PathVariable long id) {
		Usuario usuario = repositorio.findById(id).orElse(null);
		if (usuario == null) {
			return new ResponseEntity<>("Usuario não encontrado", HttpStatus.NOT_FOUND);
		}
		repositorio.delete(usuario);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
