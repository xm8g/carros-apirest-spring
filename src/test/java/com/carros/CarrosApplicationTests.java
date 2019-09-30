package com.carros;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.carros.domain.Carro;
import com.carros.domain.dto.CarroDTO;
import com.carros.exception.ObjectNotFoundException;
import com.carros.service.CarroService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarrosApplicationTests {

	@Autowired
	private CarroService service;
	
	@Test
	public void testSave() {
		Carro c = new Carro();
		c.setNome("Aston Martin 2008");
		c.setTipo("Esportivos");
		
		CarroDTO carroDTO = service.salvarCarro(c);
		Long id = carroDTO.getId();
		Assert.assertNotNull(carroDTO);
		
		CarroDTO carro = service.getCarro(id);
		Assert.assertNotNull(carro);
		
		service.apagar(id);
		try {
			CarroDTO carroDepoisDeApagado = service.getCarro(id);
			Assert.fail("O carro não foi apagado");
		} catch(ObjectNotFoundException e) {
			//OK
		}
		
	}

	@Test
	public void testLista() {
		List<CarroDTO> carros = service.getCarros();
		System.out.println(carros.size() == 60);
	}
}
