package keel.nablarch.validation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import jakarta.validation.Validator;

/**
 * バリデーション機能の設定。
 */
@Configuration(proxyBeanMethods = false)
public class ValidationConfiguration {

    /**
     * ValidationSystemRepositoryLoaderを構築する。
     * 
     * @param localValidatorFactoryBean {@link Validator}実装クラスのインスタンス
     * @return 構築されたインスタンス
     */
    @Bean
    public ValidationSystemRepositoryLoader validationSystemRepositoryLoader(LocalValidatorFactoryBean localValidatorFactoryBean) {
        return new ValidationSystemRepositoryLoader(localValidatorFactoryBean);
    }
}
