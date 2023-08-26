package httpRequest;


/* Esta clase se encarga de realizar efectivamente el pedido de feed al servidor de noticias
 * Leer sobre como hacer una http request en java
 * https://www.baeldung.com/java-http-request
 * */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.net.URISyntaxException;

public class httpRequester {
	
	public String getFeedRss(String urlFeed) {
		String feedRssXml = null;
		try {
			URL url = new URI(urlFeed).toURL();
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			
			int responseCode = connection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				feedRssXml = response.toString();
			}
		} catch (ProtocolException e){
			System.out.printf("Problema con el protocolo: %s\n", e.getCause().toString());
		} catch (MalformedURLException e) {
			System.out.printf("La dirección URL es incorrecta: %s\n", e.getCause().toString());
		} catch (IOException e) {
			System.out.printf("Problema en la conexión con el servidor: %s\n", e.getCause().toString());
		} catch (URISyntaxException e) {
			System.out.printf("Error en la creación de la URI: %s\n", e.getCause().toString());
		}


		return feedRssXml;
	}

	public String getFeedReedit(String urlFeed) {
		String feedReeditJson = null;
		try {
			URL url = new URI(urlFeed).toURL();
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0");

			int responseCode = connection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				feedReeditJson = response.toString();
			}
		} catch (ProtocolException e){
			System.out.printf("Problema con el protocolo: %s\n", e.getCause().toString());
		} catch (MalformedURLException e) {
			System.out.printf("La dirección URL es incorrecta: %s\n", e.getCause().toString());
		} catch (IOException e) {
			System.out.printf("Problema en la conexión con el servidor: %s\n", e.getCause().toString());
		} catch (URISyntaxException e) {
			System.out.printf("Error en la creación de la URI: %s\n", e.getCause().toString());
		} catch (Exception e) {
            System.out.printf("error: %s\n", e.getCause().toString());
        }

		return feedReeditJson;
	}

	public static void main(String[] args) {
		httpRequester httpRequest = new httpRequester();
		String url = "https://rss.nytimes.com/services/xml/rss/nyt/Business.xml";
		System.out.println(httpRequest.getFeedRss(url));
	}
}