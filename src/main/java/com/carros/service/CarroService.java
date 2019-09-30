package com.carros.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.carros.dao.CarroRepository;
import com.carros.domain.Carro;
import com.carros.domain.dto.CarroDTO;
import com.carros.exception.ObjectNotFoundException;

@Service
public class CarroService {

	@Autowired
	private CarroRepository carroRepository;

	public List<CarroDTO> getCarros() {
		List<Carro> carros = carroRepository.findAll();
		// return carros.stream().map(c -> new
		// CarroDTO(c)).collect(Collectors.toList());
		return carros.stream().map(CarroDTO::create).collect(Collectors.toList());
	}

	public CarroDTO getCarro(Long id) {
		return carroRepository.findById(id).map(CarroDTO::create).
				orElseThrow(() -> new ObjectNotFoundException("Carro não encontrado"));
	}

	public List<CarroDTO> getCarroPorTipo(String tipo) {

		return carroRepository.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
	}

	public CarroDTO salvarCarro(Carro carro) {
		Assert.isNull(carro.getId(),"Não foi possível inserir o registro");
		return CarroDTO.create(carroRepository.save(carro));
	}

	public CarroDTO update(Long id, Carro carro) {
		Assert.notNull(id, "Não foi possível atualizar o registro");

		Optional<Carro> optional = carroRepository.findById(id);
		if (optional.isPresent()) {
			Carro db = optional.get();
			db.setNome(carro.getNome());
			db.setTipo(carro.getTipo());

			carroRepository.save(db);

			return CarroDTO.create(db);
		} else {
			return null;
		}
	}

	public void apagar(Long id) {
		carroRepository.deleteById(id);
	}
}
