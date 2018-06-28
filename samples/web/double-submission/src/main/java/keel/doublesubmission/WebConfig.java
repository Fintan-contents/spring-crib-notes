package keel.doublesubmission;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.support.RequestDataValueProcessor;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenInterceptor;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenRequestDataValueProcessor;

// example-start
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Thymeleafのth:actionが指定されている場合は、Hidden項目にトークンを埋め込むプロセッサを定義します。
    @Bean
    public RequestDataValueProcessor requestDataValueProcessor() {
        return new TransactionTokenRequestDataValueProcessor();
    }

    // トークンチェックを実施するインターセプタを定義します。
    @Bean
    public TransactionTokenInterceptor transactionTokenInterceptor() {
        return new TransactionTokenInterceptor();
    }

    // トークンチェックを実施するインターセプタ設定します。
    // また、インターセプタを適用するパスを設定します。
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(transactionTokenInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/resources/**");
    }
}
// example-end