package rest_api.configuration;

import javax.servlet.Filter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { ApplicationConfiguration.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {
		Filter[] singleton = { new CORSFilter() };
		return singleton;
	}
}

/*
 * implements WebApplicationInitializer { public void onStartup(ServletContext
 * servletContext) throws ServletException {
 * AnnotationConfigWebApplicationContext ctx = new
 * AnnotationConfigWebApplicationContext();
 * ctx.register(ApplicationConfiguration.class);
 * ctx.setServletContext(servletContext); ctx.refresh(); Dynamic dynamic =
 * servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
 * dynamic.addMapping("/"); dynamic.setLoadOnStartup(1);
 * dynamic.setMultipartConfig(ctx.getBean(MultipartConfigElement.class)); } }
 */
