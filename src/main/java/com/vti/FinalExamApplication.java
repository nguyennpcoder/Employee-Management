package com.vti;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.vti.entity.Account;
import com.vti.form.AccountCreateForm;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication // tk này đã có @Configuration r
public class FinalExamApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalExamApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.typeMap(AccountCreateForm.class, Account.class)
				.addMappings(mapper -> mapper.skip(Account::setId));
		return modelMapper;
	}

	@Bean
	public ObjectWriter objectWriter(){
		return new ObjectMapper()
				.findAndRegisterModules()
				.writerWithDefaultPrettyPrinter();
	}
	// dùng để ghi object ra ngoai màn hình
	// tìm tk nào có object writer
	// formal lại & viết ra


	// cho phép domain khác truy cập vào api để thao tác dữ lieu
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry
						.addMapping("/**")
						.allowedOrigins("localhost:5500")
						.allowedMethods("GET", "POST", "PUT", "DELETE");
			}
		};
	}
}
