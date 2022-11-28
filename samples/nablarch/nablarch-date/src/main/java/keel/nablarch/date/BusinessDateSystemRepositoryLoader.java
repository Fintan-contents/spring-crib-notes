package keel.nablarch.date;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

import nablarch.core.date.BusinessDateProvider;
import nablarch.core.repository.SystemRepository;

// class-start
/**
 * 業務日付管理機能に必要なインスタンスを{@link SystemRepository}へ登録するクラス。
 */
public class BusinessDateSystemRepositoryLoader implements InitializingBean {

    /**
     * BusinessDateProvider
     */
    private final BusinessDateProvider businessDateProvider;

    /**
     * コンストラクタ。
     * 
     * @param businessDateProvider BusinessDateProvider
     */
    public BusinessDateSystemRepositoryLoader(BusinessDateProvider businessDateProvider) {
        this.businessDateProvider = businessDateProvider;
    }

    @Override
    public void afterPropertiesSet() {
        SystemRepository.load(() -> Map.of("businessDateProvider", businessDateProvider));
    }
}
// class-end
