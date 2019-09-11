package star16m.bootsample.web.service.sample;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import star16m.bootsample.web.resource.sample.Team;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(SpringExtension.class)
public class SpringBootJunit5Test {
    @Autowired
    private TeamRepository teamRepository;

    @Test
    public void test1() {
        assertThat(this.teamRepository).isNotNull();
        List<Team> allTeam = this.teamRepository.findAll();
        Team lotte = allTeam.stream().filter(t -> t.getFullName().contains("롯데")).findAny().orElse(null);
        assertThat(lotte.getId()).isNotNull().isEqualTo(3);
    }

    @TestFactory
    public List<DynamicTest> testTeam() {
        assertThat(this.teamRepository).isNotNull();
        List<Team> allTeam = this.teamRepository.findAll();
        return allTeam.stream().map(t -> DynamicTest.dynamicTest("테스트[" + t.getFullName() + "]", ()-> assertThat(t).isNotNull().extracting("shortName").isNotNull().contains("롯데"))).collect(Collectors.toList());
    }
}
