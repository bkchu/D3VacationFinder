package Frontend.framework.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import javax.annotation.PostConstruct;
import java.io.InputStream;


public class CustomTemplateResolver extends TemplateResolver {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	public CustomTemplateResolver() {
		super();
	}

	@PostConstruct
	private void init() {
		this.setResourceResolver(new CustomResourceResolver());
	}

	public class CustomResourceResolver implements IResourceResolver {
		public String getName() {
			return "CUSTOM";
		}

		public InputStream getResourceAsStream(TemplateProcessingParameters templateProcessingParameters, String resourceName) {
			return applicationContext.getClassLoader().getResourceAsStream(resourceName);
		}
	}
}
