package keel.nablarch.date;

import keel.nablarch.db.DbAccessConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import nablarch.core.date.BasicBusinessDateProvider;
import nablarch.core.date.BusinessDateProvider;
import nablarch.core.db.transaction.SimpleDbTransactionManager;

/**
 * Nablarchの業務日付管理機能を使用するための設定。
 */
@Import(DbAccessConfiguration.class)
public class BusinessDateConfiguration {

    // provider-start
    /**
     * BasicBusinessDateProviderを構築する。
     * 
     * @param transactionManager SimpleDbTransactionManager
     * @return 構築されたインスタンス
     */
    @Bean(initMethod = "initialize")
    @ConfigurationProperties(prefix = "nablarch.business-date")
    public BasicBusinessDateProvider businessDateProvider(SimpleDbTransactionManager transactionManager) {
        BasicBusinessDateProvider businessDateProvider = new BasicBusinessDateProvider();
        businessDateProvider.setTableName("business_date");
        businessDateProvider.setSegmentColumnName("segment_id");
        businessDateProvider.setDateColumnName("biz_date");
        businessDateProvider.setDefaultSegment("00");
        businessDateProvider.setDbTransactionManager(transactionManager);
        // サンプルでは取得前に更新を行うため、キャッシュを無効にする
        businessDateProvider.setCacheEnabled(false);

        return businessDateProvider;
    }
    // provider-end

    // repository-start
    /**
     * BusinessDateSystemRepositoryLoaderを構築する。
     * 
     * @param businessDateProvider BusinessDateProvider
     * @param properties 業務日付を上書きするためのProperties
     * @return 構築されたインスタンス
     */
    @Bean
    public BusinessDateSystemRepositoryLoader businessDateSystemRepositoryLoader(
            BusinessDateProvider businessDateProvider, FixedBusinessDateProperties properties) {
        return new BusinessDateSystemRepositoryLoader(businessDateProvider, properties);
    }
    // repository-end
}
