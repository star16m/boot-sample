package star16m.bootsample.web.service.sample;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@ExtendWith(SpringExtension.class)
public class RegexTest {

    private String targetString =
            "select regexp_replace(nvl(v1haha, 'v1hehe') + 1, 'hoho'  ) as v1, " +
                    "regexp_replace(trim(nvl(v2haha)), 'hoho') as v2, " +
                    "regexp_replace(trim(v3haha), trim(nvl('hoho')), '$1$1') as v3 " +
                    "regexp_replace(v4) as v4 " +
                    "regexp_replace(v5haha) as v5 " +
                    "regexp_replace(haha, nvl(v6haha, hoho), hoho) as v6 " +
                    "regexp_replace(haha, haha, nvl(v7haha) ) as v7 " +
                    "regexp_replace() as v8 " +
                    "regexp_replace( ) as v9 " +
                    "from dual;";

    private Pattern getPattern(String patternString) {
        return Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
    }

    @Test
    public void test1() {
//        Matcher matcher = getPattern("(?=\\()(?:(?=.*?\\((?!.*?\\1)(.*\\)(?!.*\\2).*))(?=.*?\\)(?!.*?\\2)(.*)).)+?.*?(?=\\1)[^(]*(?=\\2$)").matcher(targetString);
//        Matcher matcher = getPattern("(?=regexp_replace\\()(?=((?:(?=.*?\\((?!.*?\\2)(.*\\)(?!.*\\3).*))(?=.*?\\)(?!.*?\\3)(.*)).)+?.*?(?=\\2)[^(]*(?=\\3$)))").matcher(targetString);
//        Matcher matcher = getPattern(".+?place([^(]*([^)]*)[^)]*)\\|([^)]*)").matcher(targetString);
//        Pattern p = getPattern("regexp_replace\\s*(?=\\()(?:(?=.*?\\((?!.*?\\1)(.*\\)(?!.*\\2).*))(?=.*?\\)(?!.*?\\2)(.*)).)+?.*?(?=\\1)[^(]*(?=\\2$)");
//        Matcher matcher = p.matcher(targetString);
//        while (matcher.find()) {
//            String matchesString = matcher.group();
//            String functionString = matchesString.substring(matchesString.indexOf("(") + 1, matchesString.lastIndexOf(")"));
//            String replaceString = replaceComma(functionString);
//            String resultString = matchesString.replace(functionString, replaceString);
//            String[] arguments = resultString.substring(resultString.indexOf("(") + 1, resultString.lastIndexOf(")")).split(",");
//            log.info("\n\nMatched : [{}]\nFunctionInfo : [{}]\nReplace : [{}]\nResult : [{}]", matchesString, functionString, replaceString, resultString);
//            AtomicInteger argumentNum = new AtomicInteger(0);
//            log.info("\n{}", Arrays.asList(arguments).stream().map(v -> "\t [" + argumentNum.incrementAndGet() + "] : " + v.replaceAll("<REGEX_COMMA>", ",")).collect(Collectors.joining("\n")));
//        }
//        List<FunctionInfo> regexp_replace = foundFunctionInfo(targetString, "regexp_replace");
//        log.info("functions ##############\n[{}]\n###################", regexp_replace);

        String subjectString = "select to_date(sysdate - nvl(a.haha_date, sysdate) + 1, 'yyyymmdd'  ) as v1 from dual ";
        Pattern regex = Pattern.compile("[+*/]|(?<=\\s)-");
        Matcher matcher = regex.matcher(subjectString);
        while (matcher.find()) {
            String string = matcher.group();
            System.out.println("matched : " + string);
        }


    }

    private String replaceComma(String originalString) {
        Objects.requireNonNull(originalString);
        Pattern pattern = Pattern.compile("\\w*(?:\\s*)(?=\\()(?:(?=.*?\\((?!.*?\1)(.*\\)(?!.*\2).*))(?=.*?\\)(?!.*?\2)(.*)).)+?.*?(?=\\1)[^(]*(?=\\2$)");
        Matcher matcher = pattern.matcher(originalString);
        String replaceString = originalString;
        if (matcher.find()) {
            String matchedString = matcher.group();
            replaceString = replaceString.replace(matchedString, matchedString.replaceAll(",", "<REGEX_COMMA>"));
        }
        return replaceString;
    }

    @Data
    class FunctionInfo {
        private String functionName;
        private List<String> arguments;

        public FunctionInfo(String functionName, String[] arguments) {
            this.functionName = functionName;
            this.arguments = Arrays.asList(arguments);
        }
    }

    public List<FunctionInfo> foundFunctionInfo(String originalString, String functionName) {
        Objects.requireNonNull(originalString);
        Objects.requireNonNull(functionName);
        if (!originalString.toUpperCase().contains(functionName.toUpperCase())) {
            return Collections.emptyList();
        }
        Pattern functionPattern = Pattern.compile(functionName + "\\s*(?=\\()(?:(?=.*?\\((?!.*?\1)(.*\\)(?!.*\2).*))(?=.*?\\)(?!.*?\2)(.*)).)+?.*?(?=\\1)[^(]*(?=\\2$)", Pattern.CASE_INSENSITIVE);
        Matcher functionMatcher = functionPattern.matcher(originalString);
        List<FunctionInfo> functionInfoList = new ArrayList<>();
        while (functionMatcher.find()) {
            String matchesString = functionMatcher.group();
            String functionString = matchesString.substring(matchesString.indexOf("(") + 1, matchesString.lastIndexOf(")"));
            String replaceString = replaceComma(functionString);
            String resultString = matchesString.replace(functionString, replaceString);
            String[] arguments = resultString.substring(resultString.indexOf("(") + 1, resultString.lastIndexOf(")")).split(",");
//            log.info("\n\nMatched : [{}]\nFunctionInfo : [{}]\nReplace : [{}]\nResult : [{}]", matchesString, functionString, replaceString, resultString);
            AtomicInteger argumentNum = new AtomicInteger(0);
//            log.info("\n{}", Arrays.asList(arguments).stream().map(v -> "\t [" + argumentNum.incrementAndGet() + "] : " + v.replaceAll("<REGEX_COMMA>", ",")).collect(Collectors.joining("\n")));
            functionInfoList.add(new FunctionInfo(functionName.toUpperCase(), arguments));
        }
        return functionInfoList;
    }
}
