package keel.nablarch.date;

import java.util.HashMap;
import java.util.Map;

import nablarch.core.date.BasicBusinessDateProvider;
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
     * 業務日付を上書きするためのProperties
     */
    private final FixedBusinessDateProperties properties;

    /**
     * コンストラクタ。
     * 
     * @param businessDateProvider BusinessDateProvider
     * @param properties 業務日付を上書きするためのProperties
     *
     */
    public BusinessDateSystemRepositoryLoader(BusinessDateProvider businessDateProvider,
                                              FixedBusinessDateProperties properties) {
        this.businessDateProvider = businessDateProvider;
        this.properties = properties;
    }

    @Override
    public void afterPropertiesSet() {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("businessDateProvider", businessDateProvider);
        properties.getFixed().forEach((key, value) ->
                objectMap.put(BasicBusinessDateProvider.class.getSimpleName() + "." + key, value));
        SystemRepository.load(() -> objectMap);
    }
}
// class-end
