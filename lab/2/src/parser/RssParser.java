package parser;

/* Esta clase implementa el parser de feed de tipo rss (xml)
 * https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm 
 * */

import org.w3c.dom.*;

import feed.Feed;
import httpRequest.httpRequester;
import feed.Article;

import javax.xml.parsers.*;
import java.io.*;

import java.util.Date;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RssParser extends GeneralParser {


    public Feed parseFeed(String rawRss) {
        Feed feed = new Feed(null);
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            ByteArrayInputStream input = new ByteArrayInputStream(rawRss.getBytes("UTF-8"));
            Document xml = builder.parse(input);

            String title = xml.getElementsByTagName("title").item(0).getTextContent();
            
            feed.setSiteName(title);

            // Todos los elementos "item"
            NodeList items = xml.getElementsByTagName("item");
            
            // Por cada elemento, creamos un articulo
            for (int i = 0; i < items.getLength(); i++) {
                // Elemento/articulo actual
                Node itemNode = items.item(i);
                
                if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element item = (Element) itemNode;
                    // Obtenemos toda la info            
                    String articleTitle = item.getElementsByTagName("title").item(0).getTextContent();
                    String articleText = item.getElementsByTagName("description").item(0).getTextContent();
                    String articlelink = item.getElementsByTagName("link").item(0).getTextContent();
                    String rawDate = item.getElementsByTagName("pubDate").item(0).getTextContent();
                    
                    // Formateamos la fecha            
                    SimpleDateFormat dateFormat = 
                                    new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
                    Date articleDate = dateFormat.parse(rawDate);

                    // Creamos el articulo
                    Article article = new Article(articleTitle, articleText, articleDate, articlelink);
                    feed.addArticle(article);
                }
            }
        } catch (ParserConfigurationException e) {
            System.out.printf("Error de configuracion: %s\n", e.getCause().toString());
        } catch (UnsupportedEncodingException e) {
            System.out.printf("Caracter no aceptado por UTF-8: %s\n", e.getCause().toString());
        } catch (ParseException e) {
            System.out.printf("Error de parseo: %s\n", e.getCause().toString());
        } catch (IOException e) {
            System.out.printf("Error I/O: %s\n", e.getCause().toString());
        } catch (Exception e) {
            System.out.printf("Error: %s\n", e.getCause().toString());
        }
        

        return feed;                
    }
    public static void main(String[] args) {
        httpRequester httpRequest = new httpRequester();
		String url = new String("https://rss.nytimes.com/services/xml/rss/nyt/Business.xml");
		String response = httpRequest.getFeedRss(url);

        RssParser parser = new RssParser();
        Feed feed = parser.parseFeed(response);

        System.out.println(feed.getSiteName());
    }
}
