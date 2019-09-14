package star16m.bootsample;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import star16m.bootsample.web.SampleApplication;
import star16m.bootsample.web.common.SimpleResponse;
import star16m.bootsample.web.model.ResultCode;
import star16m.bootsample.web.resource.sample.Team;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("enabled-swagger")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SampleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BatchSampleApplicationTests {

	@Autowired
	TestRestTemplate testRestTemplate;
	@Value("${app.swagger.security.header.key:fake-token-for-swagger}")
	private String swaggerHeaderKey;

	@Test
	public void testWith() {

		SimpleResponse<Team> teamResponse = exchangeGet(null, "/api/rest/v1/team/2", new ParameterizedTypeReference<SimpleResponse<Team>>() {});
		assertThat(teamResponse).isNotNull();
		assertThat(teamResponse.getResult()).isEqualTo(ResultCode.SUCCESS);
		Team team = teamResponse.getBody();
		assertThat(team.getShortName()).isEqualToIgnoringCase("롯데");

		team.setShortName("롯데-updated");
		SimpleResponse<Team> responseForPost = exchangePost(team, "/api/rest/v1/team/2", new ParameterizedTypeReference<SimpleResponse<Team>>() {});
		assertThat(responseForPost).isNotNull();
		assertThat(responseForPost.getResult()).isEqualTo(ResultCode.SUCCESS);
		assertThat(responseForPost.getBody().getShortName()).isEqualToIgnoringCase("롯데-updated");

        teamResponse = exchangeGet(null, "/api/rest/v1/team/2", new ParameterizedTypeReference<SimpleResponse<Team>>() {});
        assertThat(teamResponse).isNotNull();
        assertThat(teamResponse.getResult()).isEqualTo(ResultCode.SUCCESS);
        team = teamResponse.getBody();
        assertThat(team.getShortName()).isEqualToIgnoringCase("롯데-updated");

        team.setShortName("롯데");
		exchangePost(team, "/api/rest/v1/team/2", new ParameterizedTypeReference<SimpleResponse<Team>>() {});
	}

	private <REQ, RES> SimpleResponse<RES> exchangeGet(REQ request, String url, ParameterizedTypeReference<SimpleResponse<RES>> type) {
		return exchange(request, url, HttpMethod.GET, type);
	}
	private <REQ, RES> SimpleResponse<RES> exchangePost(REQ request, String url, ParameterizedTypeReference<SimpleResponse<RES>> type) {
		return exchange(request, url, HttpMethod.POST, type);
	}
	private <REQ, RES> SimpleResponse<RES> exchange(REQ req, String url, HttpMethod httpMethod, ParameterizedTypeReference<SimpleResponse<RES>> type) {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.add(this.swaggerHeaderKey, "admin");
		HttpEntity<REQ> reqHttpEntity = new HttpEntity<>(req, requestHeaders);

		ResponseEntity<SimpleResponse<RES>> exchangeResult = testRestTemplate.exchange(url, httpMethod, reqHttpEntity, type);
		return exchangeResult.getBody();
	}
}
