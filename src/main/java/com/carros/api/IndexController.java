package com.carros.api;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.carros.domain.Carro;
import com.carros.domain.dto.CarroDTO;
import com.carros.service.CarroService;

@RestController
@RequestMapping("/api/v1/carros")
public class IndexController {

	@Autowired
	private CarroService service;

	@GetMapping
	public ResponseEntity getCarros() {

		return ResponseEntity.ok(service.getCarros());
	}

	@GetMapping("/{id}")
	public ResponseEntity getCarros(@PathVariable("id") Long id) {
		CarroDTO carro = service.getCarro(id);

		return ResponseEntity.ok(carro);

		// return carro.isPresent() ? ResponseEntity.ok(carro) :
		// ResponseEntity.notFound().build();
	}

	@GetMapping("/tipo/{tipo}")
	public ResponseEntity getCarrosPortipo(@PathVariable("tipo") String tipo) {
		List<CarroDTO> carros = service.getCarroPorTipo(tipo);
		return carros.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(carros);
	}

	@PostMapping
	@Secured({"ROLE_ADMIN"})
	public ResponseEntity post(@RequestBody Carro carro) {
		
		CarroDTO c = service.salvarCarro(carro);
		URI location = getURI(c.getId());

		return ResponseEntity.created(location).build();
	}

	private URI getURI(Long id) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
				buildAndExpand(id).toUri();
	}

	@PutMapping("/{id}")
	public ResponseEntity alterarCarro(@PathVariable("id") Long id, @RequestBody Carro carro) {
		CarroDTO c = service.update(id, carro);
		return c != null ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity apagarCarro(@PathVariable("id") Long id) {
		service.apagar(id);
		return ResponseEntity.ok().build();
	}
}
