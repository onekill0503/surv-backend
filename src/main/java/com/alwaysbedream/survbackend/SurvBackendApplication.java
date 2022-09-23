package com.alwaysbedream.survbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.alwaysbedream.survbackend.filters.AuthFilter;

@SpringBootApplication
public class SurvBackendApplication {
	// main program.
	public static void main(String[] args) {
		SpringApplication.run(SurvBackendApplication.class, args);
	}

	// making filter middleware for some endpoint (it mean you need provide something like Authoriozation token)
	@Bean
	public FilterRegistrationBean<AuthFilter> filterRegistrationBean(){
		FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
		AuthFilter authFilter = new AuthFilter();
		registrationBean.setFilter(authFilter);
		registrationBean.addUrlPatterns("/form/*");
		registrationBean.addUrlPatterns("/response/*");
		registrationBean.addUrlPatterns("/account/update");
		registrationBean.addUrlPatterns("/account/changepassword");
		return registrationBean;
	}
}
