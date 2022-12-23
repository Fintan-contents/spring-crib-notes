package keel.nablarch.date;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

import nablarch.core.date.SystemTimeProvider;
import nablarch.core.repository.SystemRepository;

// class-start
/**
 * システム日時管理機能に必要なインスタンスを{@link SystemRepository}へ登録するクラス。
 */
public class SystemTimeSystemRepositoryLoader implements InitializingBean {

    /**
     * SystemTimeProvider
     */
    private final SystemTimeProvider systemTimeProvider;

    /**
     * コンストラクタ。
     * 
     * @param systemTimeProvider SystemTimeProvider
     */
    public SystemTimeSystemRepositoryLoader(SystemTimeProvider systemTimeProvider) {
        this.systemTimeProvider = systemTimeProvider;
    }

    @Override
    public void afterPropertiesSet() {
        SystemRepository.load(() -> Map.of("systemTimeProvider", systemTimeProvider));
    }
}
// class-end
