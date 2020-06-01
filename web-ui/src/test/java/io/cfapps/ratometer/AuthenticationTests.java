package io.cfapps.ratometer;

import io.cfapps.ratometer.model.dto.LoginDTO;
import io.cfapps.ratometer.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@SpringBootTest(classes = Application.class)
class AuthenticationTests {

	@Autowired
	LoginService loginService;

	@Test
	@DisplayName("Authentication")
	void authenticate() {
		Executable executable = () -> {
			LoginDTO loginDTO = new LoginDTO();
			loginDTO.setUsername("admin");
			loginDTO.setPassword("admin");
			String token = Stream.of(loginService.authenticate(loginDTO).getResult()).filter(e -> e.getName().equals("Authorization"))
					.findFirst().get().getValue();
			Assertions.assertTrue(StringUtils.isNotBlank(token), "Could not get the authorization token!");
		};

		Assertions.assertDoesNotThrow(executable, "Could connect to server for authentication!");
	}

}
