package keel.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

// example-start
@Configuration
public class BatchConfig {

    final MessageSource messageSource;

    public BatchConfig(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        // 精査エラー時のメッセージを、MessageSourceから取得するように設定
        localValidatorFactoryBean.setValidationMessageSource(messageSource);
        return localValidatorFactoryBean;
    }
}
// example-end
