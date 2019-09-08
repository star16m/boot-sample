package star16m.bootsample.web.service.sample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringJUnit4ClassRunner.class)
public class RegexTest {

    private String targetString =
            "select regexp_replace(nvl(v1haha, 'v1hehe'), 'hoho'  ) as v1, " +
            "regexp_replace(trim(nvl(v2haha)), 'hoho') as v2, " +
            "regexp_replace(v3haha, 'hoho', '$1$1') as v3 " +
            "regexp_replace(v4) as v4 " +
            "regexp_replace(v5haha) as v5 " +
            "regexp_replace(haha, nvl(v6haha, hoho), hoho) as v6 " +
            "regexp_replace(haha, haha, nvl(v7haha) ) as v7 " +
            "from dual;";
    private Pattern getPattern(String patternString) {
        return Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
    }
    @Test
    public void test1() {
//        Matcher matcher = getPattern("(?=\\()(?:(?=.*?\\((?!.*?\\1)(.*\\)(?!.*\\2).*))(?=.*?\\)(?!.*?\\2)(.*)).)+?.*?(?=\\1)[^(]*(?=\\2$)").matcher(targetString);
//        Matcher matcher = getPattern("(?=regexp_replace\\()(?=((?:(?=.*?\\((?!.*?\\2)(.*\\)(?!.*\\3).*))(?=.*?\\)(?!.*?\\3)(.*)).)+?.*?(?=\\2)[^(]*(?=\\3$)))").matcher(targetString);
//        Matcher matcher = getPattern(".+?place([^(]*([^)]*)[^)]*)\\|([^)]*)").matcher(targetString);
        Pattern p = getPattern("\\w+?(?=\\()(?:(?=.*?\\((?!.*?\\1)(.*\\)(?!.*\\2).*))(?=.*?\\)(?!.*?\\2)(.*)).)+?.*?(?=\\1)[^(]*(?=\\2$)");
        Matcher matcher = p.matcher(targetString);
        while (matcher.find()) {
            String matchesString = matcher.group();
//            List<String> parameters = Arrays.asList(matchesString.split(","));
//            parameters.stream().filter(s -> s.)
            System.out.println("founded = " + matchesString);
        }
//        System.out.println(matcher.replaceAll("###$0###"));

//        String result = matcher.replaceAll(matcher.group());
//        System.out.println(result);
//        while (matcher.find()) {
//            String result = matcher.replaceAll(matcher.group());
//            System.out.println(result);
//        }

    }

    private String functionString(String originalString) {
        Pattern p = getPattern("(?=\\()(?=((?:(?=.*?\\((?!.*?\\2)(.*\\)(?!.*\\3).*))(?=.*?\\)(?!.*?\\3)(.*)).)+?.*?(?=\\2)[^(]*(?=\\3$)))");;
        Matcher matcher = p.matcher(originalString);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
