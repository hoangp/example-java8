package org.example.rest;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

public class SimpleRestClientTest
{
	private RestTemplate restTemplate;
	private MockRestServiceServer mockServer;
	private String mockURL;
	SimpleRestService simpleRestService;

	public class SimpleRestService
	{
		public String getMessage()
		{
			String result;
			try
			{
				String httpResult = restTemplate.getForObject(mockURL, String.class);
				result = "Message SUCCESS result: " + httpResult;
			}
			catch (HttpStatusCodeException e)
			{
				result = "Get FAILED with HttpStatusCode: " + e.getStatusCode() + "|" + e.getStatusText();
			}
			catch (RuntimeException e)
			{
				result = "Get FAILED\n" + ExceptionUtils.getFullStackTrace(e);
			}
			return result;
		}
	}

	@Before
	public void setUp()
	{
		String rawLocalIp = "localhost";
		String randomPort = String.valueOf(RandomUtils.nextInt(9999));
		mockURL = "https://" + rawLocalIp + ":" + randomPort + "/api/";

		restTemplate = new RestTemplate();
		mockServer = MockRestServiceServer.createServer(restTemplate);
		simpleRestService = new SimpleRestService();
	}

	@Test
	public void testGetMessage()
	{
		mockServer.expect(requestTo(mockURL)).andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess("resultSuccess", MediaType.TEXT_PLAIN));

		String result = simpleRestService.getMessage();

		mockServer.verify();
		assertThat(result, allOf(containsString("SUCCESS"), containsString("resultSuccess")));
	}

	@Test
	public void testGetMessage_500()
	{
		mockServer.expect(requestTo(mockURL)).andExpect(method(HttpMethod.GET))
				.andRespond(withServerError());

		String result = simpleRestService.getMessage();

		mockServer.verify();
		assertThat(result, allOf(containsString("FAILED"), containsString("500")));
	}

	@Test
	public void testGetMessage_404()
	{
		mockServer.expect(requestTo(mockURL)).andExpect(method(HttpMethod.GET))
				.andRespond(withStatus(HttpStatus.NOT_FOUND));

		String result = simpleRestService.getMessage();

		mockServer.verify();
		assertThat(result, allOf(containsString("FAILED"), containsString("404")));
	}
}
