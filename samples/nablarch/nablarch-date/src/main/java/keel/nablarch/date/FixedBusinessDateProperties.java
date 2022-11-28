package keel.nablarch.date;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "business-date")
public class FixedBusinessDateProperties {

    private Map<String, String> fixed = new HashMap<>();

    public Map<String, String> getFixed() {
        return Collections.unmodifiableMap(fixed);
    }

    public void setFixed(Map<String, String> fixed) {
        this.fixed = fixed;
    }
}
