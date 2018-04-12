package net.onebean.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@ComponentScan(
		basePackages = {"net.onebean.kepler.service",
				"net.onebean.kepler.common",
				"net.onebean.kepler.security",
				"net.onebean.kepler.dao.mongo",
				"net.onebean.component"},
		includeFilters = {
				@ComponentScan.Filter(value = Service.class, type = FilterType.ANNOTATION),
				@ComponentScan.Filter(value = Component.class, type = FilterType.ANNOTATION)
		})
@ComponentScan(
		basePackages = {"net.onebean.kepler.web"},
		includeFilters = {
				@ComponentScan.Filter(value = Controller.class, type = FilterType.ANNOTATION),
		})
@ImportResource(locations={"classpath*:META-INF/spring/*.xml"})
@EnableAutoConfiguration
@Configuration
public class KeplerMain extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(KeplerMain.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(KeplerMain.class, args);
	}


}


