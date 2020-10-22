package org.example.httpurl;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class HttpUrlExapmple
{
	public static void main(String[] args)
	{
		try
		{
			CookieManager cookieManager = new CookieManager();
			cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
			CookieHandler.setDefault(cookieManager);

			String reportURL = "https://uat-app2.protecht.com.au/alm-uat/alarms/client/app/Reports?op=portal_Report";
			URL url = new URL(reportURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent", "Java 1.1");
			conn.setRequestProperty("Connection", "keep-alive");

			conn.setRequestProperty("Cookie", "SESSIONID=0EE3BBA70147CC83887B4EBFBF3665AC");

			int answerCode = conn.getResponseCode();

			if (answerCode == HttpURLConnection.HTTP_OK)
			{
				System.out.println("User successfully log-in back to Inetsoft");
			}
			else
			{
				System.out.println(String.format("Failed to log-in to Inetsoft server using url: %s, code: %d, reason: %s",
						reportURL, conn.getResponseCode(), conn.getResponseMessage()));
			}

			List<HttpCookie> cookies = cookieManager.getCookieStore().getCookies();
			for (HttpCookie cookie : cookies)
			{
				System.out.println(cookie.getDomain());
				System.out.println(cookie);
			}
		}
		catch (IOException e)
		{
			System.out.print("Something wrong");
		}
	}
}
