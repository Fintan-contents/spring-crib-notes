package keel.validation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ValidationApp {
    
    public static void main(String[] args) {
        SpringApplication.run(ValidationApp.class, args);
    }
    
    @Configuration
    public static class Config implements WebMvcConfigurer {

        private final MessageSource messageSource;

        public Config(final MessageSource source) {
            messageSource = source;
        }

        @Bean
        public LocalValidatorFactoryBean validator() {
            final LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
            validatorFactoryBean.setValidationMessageSource(messageSource);
            return validatorFactoryBean;
            
        }
    }
}

