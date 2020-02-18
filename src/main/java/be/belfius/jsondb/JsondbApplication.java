package be.belfius.jsondb;

import be.belfius.jsondb.domain.JsonLine;
import be.belfius.jsondb.domain.User;
import be.belfius.jsondb.service.JsonLineService;
import be.belfius.jsondb.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages = "be.belfius")
@Slf4j
public class JsondbApplication {
	@Autowired
	private Environment env;
	@Value("${app.name}")
	private String appName;

	@Value("${app.version}")
	private String appVersion;

	public static void main(String[] args) {
		SpringApplication.run(JsondbApplication.class, args);
	}
	private void run(){

	}

	@Bean
	CommandLineRunner runner(UserService userService){
		return args -> {
			log.info("Application name: {}", env.getProperty("app.name"));
			//read json and write to db
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<User>> typeReference = new TypeReference<List<User>>(){};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/json/users.json");
			try {
				List<User> users = mapper.readValue(inputStream, typeReference);
				userService.save(users);
				System.out.println("users saved");
			}catch (IOException e){
				System.out.println("unable to save " + e.getMessage());
			}
		};
	}
	@Bean
	CommandLineRunner lineRunner(JsonLineService jsonLineService){
		return args -> {
			log.info("runner started");
			HashMap<String, String> map = null;
			ObjectMapper lineMapper = new ObjectMapper();
			TypeReference<List<JsonLine>> typeReference = new TypeReference<List<JsonLine>>() {
			};
			InputStream inputStream = TypeReference.class.getResourceAsStream("json/file");
			try {
				List<JsonLine> jsonlines = lineMapper.readValue(inputStream, typeReference);
				jsonLineService.save(jsonlines);
				System.out.println("users saved");
			}catch (IOException e){
				System.out.println("unable to save " + e.getMessage());
			}
		};
	}
}
