package tn.esprit.ProjetSpring.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Configuration
@EnableWebMvc
public class ResourceWebConfig implements WebMvcConfigurer{
    final Environment environment;

    public ResourceWebConfig(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        String location = environment.getProperty("app.file.storage.mapping");

        registry.addResourceHandler("/**")
                .addResourceLocations(location)
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requestedResource = location.createRelative(resourcePath);
                        if (requestedResource.exists() && requestedResource.isReadable()) {
                            return requestedResource;
                        } else {
                            // If the requested resource does not exist, return a default resource or handle it as needed.
                            return new ClassPathResource("/static/default.pdf");
                        }
                    }
                });
    }
}
