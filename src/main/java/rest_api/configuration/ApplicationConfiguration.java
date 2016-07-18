package rest_api.configuration;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@ComponentScan(basePackages = "rest_api")
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("Home");
		registry.addViewController("/home").setViewName("Home");
		registry.addViewController("/admin").setViewName("Admin");
		registry.addViewController("/userpanel").setViewName("User");
		registry.addViewController("/panel").setViewName("UserManagement");
		registry.addViewController("/file").setViewName("FileManagement");
		registry.addViewController("/login").setViewName("Login");
		registry.addViewController("/register").setViewName("Registration");
	}
	
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {

		return new EmbeddedServletContainerCustomizer() {
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {

				ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
				ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
				ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
				ErrorPage error405Page = new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED,"/405.html");
				container.addErrorPages(error401Page, error404Page, error405Page,error500Page);
			}
		};
	}	
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		registry.viewResolver(viewResolver);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// when static resources are inside resources folder under WEB-INF
		// registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/resources/");

		// when static resources are inside static folder under webapp
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}
}