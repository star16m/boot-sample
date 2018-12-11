package star16m.bootsample;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class StringTest {
    enum Patterns {
        PASSWORD("^[a-z]+(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)[a-zA-Z\\d]{8,100}$"),
        ;
        private Pattern pattern;
        Patterns(String patternString) {
            this.pattern = Pattern.compile(patternString);
        }
        public boolean valid(String testString) {
            return pattern.matcher(testString).find();
        }

    }
    @Test
    public void test문자열포함() {
        // 문자열 길이
        assertThat(Patterns.PASSWORD.valid("a1aA234")).isFalse();
        // 문자열 길이(초과)
        assertThat(Patterns.PASSWORD.valid("a1A" + IntStream.rangeClosed(1, 100).mapToObj(i->"a").collect(Collectors.joining()))).isFalse();
        // 숫자만 포함
        assertThat(Patterns.PASSWORD.valid("12345678")).isFalse();
        // 숫자 소문자 포함
        assertThat(Patterns.PASSWORD.valid("1a234567890")).isFalse();
        // 숫자 대소문자 포함(숫자 시작)
        assertThat(Patterns.PASSWORD.valid("1aA234567890")).isFalse();
        // 숫자 대소문자 포함
        assertThat(Patterns.PASSWORD.valid("a1aA234567890")).isTrue();
    }
}
