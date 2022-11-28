package keel.nablarch.code;

import nablarch.common.code.CodeManager;
import nablarch.core.repository.SystemRepository;
import org.springframework.beans.factory.InitializingBean;

import java.util.Map;

// class-start
/**
 * コード管理機能に必要なインスタンスを{@link SystemRepository}へ登録するクラス。
 */
public class CodeManagementSystemRepositoryLoader implements InitializingBean {

    /**
     * CodeManager
     */
    private final CodeManager codeManager;

    /**
     * コンストラクタ。
     * 
     * @param codeManager CodeManager
     */
    public CodeManagementSystemRepositoryLoader(CodeManager codeManager) {
        this.codeManager = codeManager;
    }

    @Override
    public void afterPropertiesSet() {
        SystemRepository.load(() -> Map.of("codeManager", codeManager));
    }
}
// class-end
