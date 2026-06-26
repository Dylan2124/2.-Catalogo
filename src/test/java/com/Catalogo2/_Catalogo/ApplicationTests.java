package com.Catalogo2._Catalogo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

@SpringBootTest // <- Para realizar las pruebas de integracion
@AutoConfigureMockMvc // <- Para inyectar la MockMvc
@ExtendWith(MockitoExtension.class) // <-
class ApplicationTests {

	@Test
	void contextLoads() {
	}

}
