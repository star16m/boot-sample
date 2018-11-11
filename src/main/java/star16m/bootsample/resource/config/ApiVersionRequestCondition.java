package star16m.bootsample.resource.config;

import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApiVersionRequestCondition implements RequestCondition<ApiVersionRequestCondition> {

        // extract the version part from url. example [v0-9]
        private final static Pattern VERSION_PREFIX_PATTERN = Pattern.compile("/v(\\d+)/");

        private int apiVersion;

        public ApiVersionRequestCondition(int apiVersion) {
            this.apiVersion = apiVersion;
        }

        public ApiVersionRequestCondition combine(ApiVersionRequestCondition other) {
            // latest defined would be take effect, that means, methods definition with
            // override the classes definition
            return new ApiVersionRequestCondition(other.getApiVersion());
        }

        public ApiVersionRequestCondition getMatchingCondition(HttpServletRequest request) {
            Matcher m = VERSION_PREFIX_PATTERN.matcher(request.getRequestURI());
            if (m.find()) {
                Integer version = Integer.valueOf(m.group(1));
                if (version >= this.apiVersion) // when applying version number bigger than configuration, then it will take
                    // effect
                    return this;
            }
            return null;
        }

        public int compareTo(ApiVersionRequestCondition other, HttpServletRequest request) {
            // when more than one configured version number passed the match rule, then only
            // the biggest one will take effect.
            return other.getApiVersion() - this.apiVersion;
        }

        public int getApiVersion() {
            return apiVersion;
        }

    }
