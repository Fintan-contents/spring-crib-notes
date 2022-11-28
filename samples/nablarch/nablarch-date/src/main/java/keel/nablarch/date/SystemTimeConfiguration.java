package keel.nablarch.date;

import org.springframework.context.annotation.Bean;

import nablarch.core.date.BasicSystemTimeProvider;
import nablarch.core.date.SystemTimeProvider;

/**
 * Nablarchのシステム日時管理機能を使用するための設定。
 */
public class SystemTimeConfiguration {

    // provider-start
    /**
     * BasicSystemTimeProviderを構築する。
     * 
     * @return 構築されたインスタンス
     */
    @Bean
    public BasicSystemTimeProvider systemTimeProvider() {
        return new BasicSystemTimeProvider();
    }
    // provider-end

    // repository-start
    /**
     * SystemTimeSystemRepositoryLoaderを構築する。
     * 
     * @param systemTimeProvider SystemTimeProvider
     * @return 構築されたインスタンス
     */
    @Bean
    public SystemTimeSystemRepositoryLoader systemTimeSystemRepositoryLoader(SystemTimeProvider systemTimeProvider) {
        return new SystemTimeSystemRepositoryLoader(systemTimeProvider);
    }
    // repository-end
}
