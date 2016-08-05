package co.com.expertla.training.web.configuration;

import java.nio.charset.StandardCharsets;
import javax.servlet.Filter;
import javax.servlet.ServletRegistration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @author <a href="mailto:edwin.gomez@expertla.com">Edwin Gomez</a>
 * @project training-web
 * @class IndexInitializer
 * @since 7/07/2016
 *
 */
public class IndexInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setInitParameter("dispatchOptionsRequest", "true");
        registration.setAsyncSupported(true);
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{WebSocketConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class< ?>[] { IndexConfiguration.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding(StandardCharsets.UTF_8.name());
        return new Filter[]{characterEncodingFilter};
    }
}
