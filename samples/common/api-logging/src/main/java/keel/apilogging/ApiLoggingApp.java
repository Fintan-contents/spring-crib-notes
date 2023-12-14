package keel.apilogging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.json.JsonPathBodyFilters;
import org.zalando.logbook.spring.LogbookClientHttpRequestInterceptor;

@SpringBootApplication
public class ApiLoggingApp {

    public static void main(String[] args) {
        SpringApplication.run(ApiLoggingApp.class, args);
    }

    @Bean
    public Logbook logbook() {
        // Logbookを生成（リクエスト/レスポンスボディの id 項目をマスクする）
        Logbook logbook = Logbook.builder()
                .bodyFilter(JsonPathBodyFilters.jsonPath("$['id']").replace("***"))
                .build();

        return logbook;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder, Logbook logbook) {
        // サンプルで使用するRestTemplateにLogbookを設定する
        RestTemplate restTemplate = builder
                .additionalInterceptors(new LogbookClientHttpRequestInterceptor(logbook))
                .build();

        return restTemplate;
    }
}
