package org.example.simple;

import java.io.IOException;

import org.springframework.web.util.UriComponentsBuilder;

public class UriBuilderExample {
	public void run() {
		String url = UriComponentsBuilder.newInstance().scheme("http").host("www.baeldung.com").path("/junit-5").build()
				.toString();

		String url2 = UriComponentsBuilder.newInstance().scheme("https").host("app.knowledgeowl.com").path(null).build()
				.toString();

		String url3 = UriComponentsBuilder.fromHttpUrl("https://app.knowledgeowl.com/api/")
				.path("head/remotelogin.json").build().toString();

		String url4 = UriComponentsBuilder.fromHttpUrl("https://app.knowledgeowl.com").path("home/remote-auth")
				.queryParam("n", "123").queryParam("r", "/abc/help").build().toString();

		System.out.println(url);
		System.out.println(url2);
		System.out.println(url3);
		System.out.println(url4);
	}

	public static void main(String[] args) throws IOException {
		UriBuilderExample app = new UriBuilderExample();
		app.run();
	}
}
