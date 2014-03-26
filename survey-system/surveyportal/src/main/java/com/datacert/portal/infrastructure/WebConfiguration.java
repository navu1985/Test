package com.datacert.portal.infrastructure;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
@EnableWebMvc
public class WebConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("mainmenu");
		registry.addViewController("/preportal").setViewName("preportal/preportal");
		registry.addViewController("/admin").setViewName("adminIndex");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/profile/image").setViewName("user/profileimage");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
		registry.addResourceHandler("/signup/static/**").addResourceLocations("/static/");
		registry.addResourceHandler("/forgetpassword/static/**").addResourceLocations("/static/");
		registry.addResourceHandler("/changepassword/static/**").addResourceLocations("/static/");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggingHandlerMethodInterceptor());
	}
}
