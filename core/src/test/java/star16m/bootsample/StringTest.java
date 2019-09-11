package star16m.bootsample;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class StringTest {
    enum Patterns {
        ALPHA_NUMERIC("^[a-zA-Z0-9]+$"),
        ALPHA("^[a-zA-Z]+$"),
        ASCII("[\\x00-\\x7F]"),
        DIGIT("^[0-9]+$"),
        ALPHA_LOWER("^[a-z]+$"),
        ALPHA_UPPER("^[A-Z]+$"),
        PASSWORD("(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)(?=.*?[!@#\\$%^&\\*\\(\\)\\-_=\\+])"),
        EMAIL("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"),
        TEL("^\\d{2,3}-\\d{4}-\\d{4}$"),
        ;
        private Pattern pattern;
        Patterns(String patternString) {
            this.pattern = Pattern.compile(patternString);
        }
        public boolean match(String testString) {
            return pattern.matcher(testString).find();
        }

    }
    @Test
    public void test문자열포함() {
        // 문자열 길이
        assertThat(Patterns.PASSWORD.match("a1aA234")).isFalse();
        // 문자열 길이(초과)
        assertThat(Patterns.PASSWORD.match("a1A" + IntStream.rangeClosed(1, 100).mapToObj(i->"a").collect(Collectors.joining()))).isFalse();
        // 숫자만 포함
        assertThat(Patterns.PASSWORD.match("12345678")).isFalse();
        // 숫자 소문자 포함
        assertThat(Patterns.PASSWORD.match("1a234567890")).isFalse();
        // 숫자 대소문자 포함(숫자 시작)
        assertThat(Patterns.PASSWORD.match("1aA234567890")).isFalse();
        // 숫자 대소문자 포함

        assertThat(Patterns.PASSWORD.match("@a1aA234567890")).isTrue();
        assertThat(Patterns.PASSWORD.match("a1aA234567890#")).isTrue();


        assertThat(Patterns.PASSWORD.match("&a1aA2345_67890")).isTrue();
    }
}
