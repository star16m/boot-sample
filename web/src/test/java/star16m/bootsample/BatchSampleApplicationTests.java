package star16m.bootsample;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import star16m.bootsample.web.SampleApplication;
import star16m.bootsample.web.common.SimpleResponse;
import star16m.bootsample.web.model.ResultCode;
import star16m.bootsample.web.resource.sample.Player;
import star16m.bootsample.web.resource.sample.Team;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("enabled-swagger")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SampleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestConfigurations.class)
@Slf4j
public class BatchSampleApplicationTests {

	@Autowired
	TestRestTemplate testRestTemplate;

	@Autowired
	HttpHeaders commonHeaders;

	@Test
	public void testWith() {

		SimpleResponse<Team> teamResponse = exchangeGet("/api/rest/v1/team/3", new ParameterizedTypeReference<SimpleResponse<Team>>() {});
		assertThat(teamResponse).isNotNull();
		assertThat(teamResponse.getResult()).isEqualTo(ResultCode.SUCCESS);
		Team team = teamResponse.getBody();
		assertThat(team.getShortName()).isEqualToIgnoringCase("롯데");

		team.setShortName("롯데-updated");
		SimpleResponse<Team> responseForPost = exchangePost(team, "/api/rest/v1/team/2", new ParameterizedTypeReference<SimpleResponse<Team>>() {});
		assertThat(responseForPost).isNotNull();
		assertThat(responseForPost.getResult()).isEqualTo(ResultCode.SUCCESS);
		assertThat(responseForPost.getBody().getShortName()).isEqualToIgnoringCase("롯데-updated");

		teamResponse = exchangeGet("/api/rest/v1/team/2", new ParameterizedTypeReference<SimpleResponse<Team>>() {});
        assertThat(teamResponse).isNotNull();
        assertThat(teamResponse.getResult()).isEqualTo(ResultCode.SUCCESS);
        team = teamResponse.getBody();
        assertThat(team.getShortName()).isEqualToIgnoringCase("롯데-updated");

        team.setShortName("롯데");
		exchangePost(team, "/api/rest/v1/team/2", new ParameterizedTypeReference<SimpleResponse<Team>>() {});
	}
	@Test
	public void testPlayers() {
		SimpleResponse<List<Player>> players = exchangeGet("/api/rest/v1/player", new ParameterizedTypeReference<SimpleResponse<List<Player>>>() {});
		log.info(players.getBody().stream().map(p -> p.getId() + ", " + p.getName()).collect(Collectors.joining(", ")));
	}

	private <RES> SimpleResponse<RES> exchangeGet(String url, ParameterizedTypeReference<SimpleResponse<RES>> type) {
		return exchange(null, url, HttpMethod.GET, type);
	}
	private <REQ, RES> SimpleResponse<RES> exchangePost(REQ requestBody, String url, ParameterizedTypeReference<SimpleResponse<RES>> type) {
		return exchange(requestBody, url, HttpMethod.POST, type);
	}
	private <REQ, RES> SimpleResponse<RES> exchange(REQ requestBody, String url, HttpMethod httpMethod, ParameterizedTypeReference<SimpleResponse<RES>> type) {
		HttpEntity<REQ> reqHttpEntity = new HttpEntity<>(requestBody, this.commonHeaders);
		ResponseEntity<SimpleResponse<RES>> exchangeResult = testRestTemplate.exchange(url, httpMethod, reqHttpEntity, type);
		return exchangeResult.getBody();
	}
}
