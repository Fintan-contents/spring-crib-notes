package keel.validation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ValidationApp {
    public static void main(String[] args) {
        SpringApplication.run(ValidationApp.class, args);
    }

    // non-native-to-ascii-start
    @Configuration
    public static class Config implements WebMvcConfigurer {

        @Bean
        public LocalValidatorFactoryBean validator() {
            final LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
            validatorFactoryBean.setValidationMessageSource(messageSource());
            return validatorFactoryBean;

        }

        /**
         * Native to Asciiを実施しないでも日本語を扱えるように、
         * {@link ReloadableResourceBundleMessageSource}を使用して、ValidationMessages.propertiesをロードします。
         */
        private MessageSource messageSource() {
            ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
            messageSource.setBasename("classpath:/ValidationMessages");
            messageSource.setDefaultEncoding("UTF-8");
            return messageSource;
        }
    }
    // non-native-to-ascii-end
}

