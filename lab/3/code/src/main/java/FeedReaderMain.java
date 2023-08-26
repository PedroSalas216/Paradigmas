import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import scala.Tuple2;

import feed.Article;
import feed.Feed;
import httpRequest.httpRequester;
import namedEntity.NamedEntity;
import namedEntity.heuristic.Heuristic;
import namedEntity.heuristic.SimpleHeuristic;
import parser.RedditParser;
import parser.RssParser;
import parser.SubscriptionParser;
import subscription.SingleSubscription;
import subscription.Subscription;

public class FeedReaderMain {

	private static void printHelp() {
		System.out.println("Please, call this program in correct way: FeedReader [-ne]");
	}



	private static JavaRDD<Tuple2<Article,Integer>> getSortedArticlesKeyWord(JavaRDD<Article> articleRDD, String keyWord) {
		JavaRDD<Tuple2<Article,Integer>> articleFilterRDD = articleRDD.flatMap((Article article)-> {
			List<Tuple2<Article,Integer>> articles = new ArrayList<>();
			String text = article.getTitle() + article.getText();
			int cont = Collections.frequency(Arrays.asList(text.split(" ")), keyWord);
			if (cont>0) {
				articles.add(new Tuple2(article, cont));
			}
			return articles.iterator();
		});
		return articleFilterRDD.sortBy(tuple -> tuple._2(), false, 1);
	}

	public static void main(String[] args) {
		
		System.out.println("************* FeedReader version 1.0 *************");

		SparkConf conf = new SparkConf().setAppName("FeedReader").setMaster("local[*]");

		JavaSparkContext sc = new JavaSparkContext(conf);
		sc.setLogLevel("ERROR");

		Path filePath = Paths.get("config/subscriptions.json");


		SubscriptionParser parser = new SubscriptionParser(filePath);
		RssParser rssParser = new RssParser();
		RedditParser redditParser = new RedditParser();
		httpRequester httpRequester = new httpRequester();
		Subscription subscription = parser.parse();

		JavaRDD<SingleSubscription> subscriptionsRDD = sc.parallelize(subscription.getSubscriptionsList());

		JavaRDD<Feed> feeds = subscriptionsRDD.flatMap(sub -> {
			List<Tuple2<String,String>> result = new ArrayList<>();

			for (String param : sub.getUlrParams()) {
				String url = String.format(sub.getUrl(), param);
				String urlType = sub.getUrlType();
				result.add(new Tuple2<>(url, urlType));
			}
			return result.iterator();
		}).map((Tuple2<String,String> t) -> {
			String request;
			Feed feed;
			if (t._2().equals("rss")) {
				request = httpRequester.getFeedRss(t._1());
				feed = rssParser.parseFeed(request);
				return feed;
			} else if (t._2().equals("reddit")) {
				request = httpRequester.getFeedReedit(t._1());
				feed = redditParser.parseFeed(request);
				return feed;
			} else {
				System.out.println("Invalid Type");
				return null;
			}
		});

		if (args.length == 0) {
			// ACA YA TENEMOS TODOS LOS FEEDS
			feeds.foreach(Feed::prettyPrint);

		}
		else if (args.length == 1 && args[0].equals("-s")) {
			feeds.foreach(Feed::prettyPrint);

			JavaRDD<Article> articleRDD = feeds.flatMap((Feed feed) -> {
				return feed.getArticleList().iterator();
			});

			System.out.print("Ingrese que termino buscar\n > ");
			Scanner scanner = new Scanner(System.in);
			String term = scanner.nextLine();

			System.out.println(term);
			scanner.close();

			JavaRDD<Tuple2<Article, Integer>> sortedRDD = getSortedArticlesKeyWord(articleRDD, term);

			sortedRDD.foreach(t -> {
				t._1().prettyPrint();
				System.out.println("Ocurrencias: " + t._2());
			});

		}

		else if ((args.length >= 1 && args[0].equals("-ne")) && args.length <= 2) {

			/*
			 Heuristic heuristic = new QuickHeuristic();		// CAMBIAR ACA PARA VER OTRA HEURISTICA
			 Heuristic heuristic = new MyHeuristic();			// CAMBIAR ACA PARA VER OTRA HEURISTICA
			 Heuristic heuristic = new RandomHeuristic();		// CAMBIAR ACA PARA VER OTRA HEURISTICA
			*/
			Heuristic heuristic = new SimpleHeuristic();		// CAMBIAR ACA PARA VER OTRA HEURISTICA

			// ACA YA TENEMOS TODOS LOS FEEDS
			feeds.foreach(Feed::prettyPrint);


			JavaRDD<Article> articleRDD = feeds.flatMap((Feed feed) -> {
				List<Article> articles = new ArrayList<>();
				for (Article article : feed.getArticleList()) {
					article.computeNamedEntities(heuristic);
					articles.add(article);
				}
				return articles.iterator();
			});

			JavaRDD<NamedEntity> neRDD = articleRDD.flatMap(article -> article.getNamedEntities().iterator());

			List<String> categoryList = heuristic.getCategoryList();
			for (String category : categoryList) {
				long cont = neRDD.filter((n)-> n.getCategory().equals(category)).count();
				System.out.printf("%s: %d\n", category, cont);
			}

			if (args.length == 2 && args[1].equals("-s")) {

				System.out.print("Ingrese que termino buscar\n > ");
				Scanner scanner = new Scanner(System.in);
				String term = scanner.nextLine();

				System.out.println(term);
				scanner.close();

				JavaRDD<Tuple2<Article, Integer>> sortedRDD = getSortedArticlesKeyWord(articleRDD, term);
				sortedRDD.foreach(t -> {
					t._1().prettyPrint();
					System.out.println("Ocurrencias: " + t._2());
				});
			}
		}

		else {
			printHelp();
		}
		sc.close();

	}
}
	
