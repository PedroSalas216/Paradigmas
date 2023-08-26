import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import feed.Article;
import feed.Feed;
import httpRequest.httpRequester;
import namedEntity.NamedEntity;
import namedEntity.heuristic.Heuristic;
import namedEntity.heuristic.RandomHeuristic;
import namedEntity.heuristic.SimpleHeuristic;
import parser.RedditParser;
import parser.RssParser;
import parser.SubscriptionParser;
import subscription.SingleSubscription;
import subscription.Subscription;

public class FeedReaderMain {

	private static void printHelp(){
		System.out.println("Please, call this program in correct way: FeedReader [-ne]");
	}
	
	private static List<Feed> getFeeds(Subscription subscription) {
		List<Feed> result = new ArrayList<>();
		List<SingleSubscription> subscriptions = subscription.getSubscriptionsList();
		RssParser rssParser = new RssParser();
		RedditParser redditParser = new RedditParser();

		httpRequester httpRequester = new httpRequester();
		
		for (SingleSubscription sub : subscriptions) {
			for (String param : sub.getUlrParams()) {
				String url = String.format(sub.getUrl(), param);
				String urlType = sub.getUrlType();
				String request;
				Feed feed;

				switch (urlType) {
					case "rss":
						request = httpRequester.getFeedRss(url);
						feed = rssParser.parseFeed(request); 
						result.add(feed);
						break;
					
					case "reddit":
						request = httpRequester.getFeedReedit(url);
						feed = redditParser.parseFeed(request); 
						result.add(feed);
						break;
		
					default:
						System.out.println("Invalid Type");
				}
			}
		}
		
		return result;
	}

	private static Integer getFrequencyByCategory(List<NamedEntity> neList, String category) {
		Integer result = 0;
		for (NamedEntity ne : neList) {
			if (ne.getCategory() == category) {
				result++;
			}
		}
		return result;
	}



	public static void main(String[] args) {
		
		System.out.println("************* FeedReader version 1.0 *************");

		if (args.length == 27015) {
			SubscriptionParser parser = new SubscriptionParser(Paths.get("config/subscriptions.json"));
			Subscription subscription = parser.parse();
			List<Feed> feeds = getFeeds(subscription);
			for (Feed feed : feeds) {
				feed.prettyPrint();
			}

		// } else if ( args.length == 1 && args[0] == "-ne") {
		} else if ( args.length == 0) {
			SubscriptionParser parser = new SubscriptionParser(Paths.get("config/subscriptions.json"));
			Subscription subscription = parser.parse();
			List<Feed> feeds = getFeeds(subscription);

			// Heuristic heuristic = new QuickHeuristic();		// CAMBIAR ACA PARA VER OTRA HEURISTICA
			// Heuristic heuristic = new MyHeuristic();		// CAMBIAR ACA PARA VER OTRA HEURISTICA
			// Heuristic heuristic = new RandomHeuristic();		// CAMBIAR ACA PARA VER OTRA HEURISTICA
			Heuristic heuristic = new SimpleHeuristic();		// CAMBIAR ACA PARA VER OTRA HEURISTICA
			List<NamedEntity> neList = new ArrayList<>();
			for (Feed feed : feeds) {
				for (Article article : feed.getArticleList()) {
					article.computeNamedEntities(heuristic);				
					neList.addAll(article.getNamedEntities());
				}
				feed.prettyPrint();
			}
			
			List<String> categoryList = heuristic.getCategoryList();
			for (String category : categoryList) {
				System.out.printf("%s: %d\n",category,getFrequencyByCategory(neList, category));
			}
		} else {
			printHelp();
		}
			
	}
}
	
