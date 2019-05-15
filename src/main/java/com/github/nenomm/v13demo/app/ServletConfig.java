package com.github.nenomm.v13demo.app;

import com.github.nenomm.v13demo.customServlet.CustomServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfig {

    @Autowired
    private ServletRegistrationBean servletRegistrationBean;

    /* workaround 1
    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping() {
        SimpleUrlHandlerMapping simpleUrlHandlerMapping
                = new SimpleUrlHandlerMapping();

        Map<String, Object> urlMap = new HashMap<>();
        urlMap.put("/frontend/**", vaadinForwardingController());
        simpleUrlHandlerMapping.setUrlMap(urlMap);

        // just before ResourceHandlerMapping
        simpleUrlHandlerMapping.setOrder(Ordered.LOWEST_PRECEDENCE - 2);

        return simpleUrlHandlerMapping;
    }

    @Bean
    public Controller vaadinForwardingController() {
        ServletForwardingController controller = new ServletForwardingController();
        controller.setServletName(
                ClassUtils.getShortNameAsProperty(SpringServlet.class));

        return controller;
    }
    */

    /* workaround 2
    @Bean
    public ServletRegistrationBean frontendServletBean() {
        final SpringServlet servlet = (SpringServlet) servletRegistrationBean.getServlet();
        final Object[] existingUrlMappings = servletRegistrationBean.getUrlMappings().toArray();
        final String[] urlMappings = new String[existingUrlMappings.length + 1];

        System.arraycopy(existingUrlMappings, 0, urlMappings, 0, existingUrlMappings.length);
        urlMappings[urlMappings.length - 1] = "/frontend/*";

        return new ServletRegistrationBean(servlet, urlMappings);
    }*/

    // final solution
    @Bean
    public ServletRegistrationBean frontendServletBean() {
        servletRegistrationBean.addUrlMappings("/frontend/*");
        return servletRegistrationBean;
    }

    @Bean
    public ServletRegistrationBean customServletBean() {
        ServletRegistrationBean bean = new ServletRegistrationBean(
                new CustomServlet(), "/customServlet/*");
        bean.setLoadOnStartup(1);
        return bean;
    }
}
