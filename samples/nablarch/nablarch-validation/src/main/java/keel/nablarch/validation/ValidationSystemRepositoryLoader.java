package keel.nablarch.validation;

import nablarch.core.repository.SystemRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import jakarta.validation.Validator;
import java.util.Map;

// class-start
/**
 * バリデーション機能に必要なインスタンスを{@link SystemRepository}へ登録するクラス。
 */
public class ValidationSystemRepositoryLoader implements InitializingBean {

    /**
     * {@link Validator}実装クラスのインスタンス
     */
    private final LocalValidatorFactoryBean localValidatorFactoryBean;

    /**
     * コンストラクタ。
     *
     * @param localValidatorFactoryBean {@link Validator}実装クラスのインスタンス
     */
    public ValidationSystemRepositoryLoader(LocalValidatorFactoryBean localValidatorFactoryBean) {
        this.localValidatorFactoryBean = localValidatorFactoryBean;
    }

    @Override
    public void afterPropertiesSet() {
        SystemRepository.load(() -> Map.of(
                "domainManager", new ExampleDomainManager(),
                "validatorFactoryBuilder", new ValidatorFactoryBuilderImpl(localValidatorFactoryBean)));
    }
}
// class-end
