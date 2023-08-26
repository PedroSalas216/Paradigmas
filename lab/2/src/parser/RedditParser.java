package parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import feed.Article;
import feed.Feed;
import httpRequest.httpRequester;

/*
 * Esta clase implementa el parser de feed de tipo reddit (json)
 * pero no es necesario su implemntacion 
 * */

public class RedditParser extends GeneralParser {

    public Feed parseFeed(String rawJson) {
        Feed feed = new Feed(null);
        try {
            JSONObject jsonObject = new JSONObject(rawJson);
            JSONArray items = jsonObject.getJSONObject("data").getJSONArray("children");
            String siteName = items.getJSONObject(0).getJSONObject("data").getString("subreddit");
    
            feed.setSiteName(siteName);
            
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i).getJSONObject("data");
                String articleTitle = item.getString("title");
                String articleText = item.getString("selftext");
                String articlelink = item.getString("url");
                long rawDate = item.getLong("created") * 1000L;
    
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                String formattedDate = dateFormat.format(rawDate);
                Date articleDate = dateFormat.parse(formattedDate);
    
                Article article = new Article(articleTitle, articleText, articleDate, articlelink);
    
                feed.addArticle(article);
            }
        } catch (JSONException e) {
            System.out.printf("Problema parseando el archivo json: %s\n", e.getCause().toString());
        } catch (ParseException e) {
            
        }

        return feed;
    }

    public static void main(String[] args) {
        httpRequester httpRequest = new httpRequester();
		String url = new String("https://www.reddit.com/r/Sales/hot/.json?count=100");
		String response = httpRequest.getFeedReedit(url);
        RedditParser parser = new RedditParser();
        parser.parseFeed(response);
    }

}
