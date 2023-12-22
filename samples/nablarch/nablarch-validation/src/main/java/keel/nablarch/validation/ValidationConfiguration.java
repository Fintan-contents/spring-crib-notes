package keel.nablarch.validation;

import jakarta.validation.Validator;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * バリデーション機能の設定。
 */
@Configuration(proxyBeanMethods = false)
@PropertySource(value = "classpath:nablarch/core/validation/charset-definition.config", encoding = "UTF-8")
public class ValidationConfiguration {

    // charset-props-start
    /**
     * CharsetDefPropertiesを構築する。
     * 
     * @return 構築されたインスタンス
     */
    @Bean
    @ConfigurationProperties("nablarch")
    public CharsetDefProperties charsetDefProperties() {
        return new CharsetDefProperties();
    }
    // charset-props-end

    // charset-loader-start
    /**
     * CharsetDefSystemRepositoryLoaderを構築する。
     *
     * @param properties CharsetDefProperties
     * @return 構築されたインスタンス
     */
    @Bean
    public CharsetDefSystemRepositoryLoader charsetDefSystemRepositoryLoader(CharsetDefProperties properties) {
        return new CharsetDefSystemRepositoryLoader(properties);
    }
    // charset-loader-end

    // repository-start
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
    // repository-end
}
