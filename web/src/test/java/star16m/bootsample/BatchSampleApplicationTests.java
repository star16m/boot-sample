package star16m.bootsample;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import star16m.bootsample.web.SampleApplication;
import star16m.bootsample.web.resource.sample.Team;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//@Ignore
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SampleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BatchSampleApplicationTests {

	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	public void testWith() {

		ResponseEntity<Team> response = testRestTemplate.getForEntity("/api/v1/rest/team/2", Team.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		Team team = response.getBody();
		System.out.println(team);
		assertThat(team.getShortName()).isEqualToIgnoringCase("롯데");

		team.setShortName("롯데-updated");
		ResponseEntity<Team> responseForPost = this.testRestTemplate.postForEntity("/api/v1/rest/team/2", team, Team.class);
		System.out.println(team);
		assertThat(responseForPost.getBody().getShortName()).isEqualToIgnoringCase("롯데-updated");


		ResponseEntity<Team> responseGet = this.testRestTemplate.getForEntity("/api/v1/rest/team/2", Team.class);
		assertThat(responseGet.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseGet.getBody()).isNotNull();
		team = responseGet.getBody();
		assertThat(team.getShortName()).isEqualToIgnoringCase("롯데-updated");

		team.setShortName("롯데");
		this.testRestTemplate.postForEntity("/api/v1/rest/team/2", team, Team.class);
	}
}
