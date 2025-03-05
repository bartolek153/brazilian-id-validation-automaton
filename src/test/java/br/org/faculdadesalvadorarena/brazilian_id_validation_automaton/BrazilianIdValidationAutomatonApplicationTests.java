package br.org.faculdadesalvadorarena.brazilian_id_validation_automaton;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import br.org.faculdadesalvadorarena.brazilian_id_validation_automaton.enums.IdType;
import br.org.faculdadesalvadorarena.brazilian_id_validation_automaton.services.IdValidator;

@SpringBootTest
class BrazilianIdValidationAutomatonApplicationTests {

	@Autowired
	private IdValidator idValidator;

	@Test
	void contextLoads() {
		assertThat(idValidator).isNotNull();
	}

	@Test
	void cpfIsValid() {
		List<String> ids = Arrays.asList(
			"502.887.278-35", 
			"12345678900", 
			"123456789-00");

		assertThat(ids)
				.allMatch(id -> {
					var res = idValidator.validate(id);
					return res.isValid && res.type == IdType.CPF;
				}, "All CPFs should be valid");
	}

	@Test
	void rgIsValid() {
		List<String> ids = Arrays.asList(
				"39.996.518-X",
				"50.288.727-0",
				"123456789",
				"12345678X",
				"12345678-0",
				"12345678-X");

		assertThat(ids)
				.allMatch(id -> {
					var res = idValidator.validate(id);
					return res.isValid && res.type == IdType.RG;
				}, "All RGs should be valid");
	}

	@Test
	void idIsInvalid() {
		List<String> ids = Arrays.asList(
				"502.887..278-35",
				"502.887278-35",
				"123.a56.789-00",
				"987.654.32100",
				"502.887.278-35cpf",
				"50.288.727-8-",
				"50.288.727-8X",
				"1234567X9",
				"50.288727X",
				"0288727X");

		assertThat(ids)		
				.allMatch(id -> !idValidator.validate(id).isValid, "All IDs should be invalid");
	}			
}
