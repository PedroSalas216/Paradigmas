package namedEntity.heuristic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Heuristic implements Serializable {
	private static List<String> names = List.of(
        "Emma", "Liam", "Olivia", "Noah", "Ava", "Oliver", "Isabella", "Elijah", "Sophia", "Lucas",
        "Mia", "Mason", "Charlotte", "Logan", "Amelia", "Ethan", "Harper", "Aiden", "Evelyn", "Jackson",
        "Abigail", "Caden", "Emily", "Grayson", "Elizabeth", "Layla", "Jacob", "Avery", "Madison", "Levi",
        "Ella", "Michael", "Scarlett", "Sebastian", "Sofia", "Benjamin", "Aria", "Caleb", "Chloe", "Daniel",
        "Victoria", "Matthew", "Grace", "Samuel", "Penelope", "Ryan", "Riley", "David", "Ellie", "William",
        "Aubrey", "Owen", "Zoey", "Dylan", "Nora", "Connor", "Lily", "Evelyn", "Hannah", "Lucy",
        "Isabelle", "Hazel", "Nathan", "Addison", "Nicholas", "Eleanor", "Tyler", "Natalie", "Christian", "Luna",
        "Jonathan", "Savannah", "Christopher", "Brooklyn", "Jaxon", "Bella", "Leah", "Stella", "Cooper", "Maya",
        "Isaac", "Mila", "Miles", "Aaliyah", "Caroline", "Elena", "Anthony", "Audrey", "Cameron", "Samantha",
        "Mateo", "Genesis", "Kai", "Eva", "Adam", "Paisley", "Leo", "Autumn", "Lincoln", "Alyssa"
    );

    private static List<String> surnames = List.of(
        "Smith", "Johnson", "Brown", "Taylor", "Miller", "Jones", "Garcia", 
        "Davis", "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez", "Perez", "Moore", "Williams", "Jackson", "Anderson", 
        "Martin", "Lee", "Thompson", "White", "Harris", "Clark", "Lewis", "Robinson", "Walker", "Parker", "Hall", "Young", "Allen", 
        "King", "Wright", "Scott", "Green", "Baker", "Adams", "Nelson", "Carter", "Mitchell", "Perez", "Roberts", "Turner", 
        "Phillips", "Campbell", "Parker", "Evans", "Edwards", "Collins", "Stewart", "Sanchez", "Morris", "Rogers", "Reed", 
        "Cook", "Morgan", "Cooper", "Bailey", "Murphy", "Rivera", "Gray", "Ortiz", "Murray", "Freeman", "Wells", "Webb", 
        "Simpson", "Henderson", "Stevens", "Graham", "Pierce", "Berry", "Matthews", "Arnold", "Wagner", "Willis", "Ray", 
        "Watkins", "Olson", "Carroll", "Duncan", "Snyder", "Hart", "Cunningham", "Bradley", "Lane", "Andrews", "Ruiz", 
        "Harper", "Fox", "Riley", "Armstrong", "Carpenter", "Weaver", "Greene", "Lawrence", "Elliott", "Chavez", "Sims", 
        "Austin", "Peters", "Kelley", "Franklin", "Lawson"
    );

    private static List<String> countries = List.of( 
            "USA", "China", "Japan", "Germany", "United Kingdom", "India",
            "France", "Brazil", "Italy", "Canada", "South Korea", "Russia", "Australia", "Spain",
            "Mexico", "Indonesia", "Netherlands", "Switzerland", "Turkey", "Saudi Arabia", "Taiwan", "Poland", "Sweden",
            "Belgium", "Argentina", "Thailand", "Iran", "Austria", "Norway", "United Arab Emirates", "Nigeria", "Israel",
            "South Africa", "Hong Kong", "Denmark", "Singapore", "Malaysia", "Philippines", "Colombia", "Egypt", "Pakistan",
            "Finland", "Chile", "Bangladesh", "Greece", "Ireland", "Portugal", "Vietnam", "Peru", "Czech Republic", "Romania"
    );
	private static List<String> companies = List.of( 
			"Apple","Amazon","Microsoft","Alphabet","Tesla","Facebook","JP Morgan Chase","Berkshire Hathaway","Visa",
			"Procter & Gamble", "Johnson & Johnson", "Walmart", "Mastercard", "UnitedHealth Group", "Nvidia", "The Home Depot", 
			"PayPal Holdings", "Nestle", "Bank of America", "Walt Disney", "Intel", "Coca-Cola", "Novartis", "McDonald's", "Pfizer",
			"Nike", "Verizon Communications", "Toyota", "Adobe", "Caterpillar", "Salesforce.com", "Comcast", "Oracle", "IBM", "PepsiCo",
			"Merck & Co.", "Philip Morris International", "Abbott Laboratories", "Chevron", "Cisco Systems", "Starbucks", "ExxonMobil", 
			"Boeing", "Goldman Sachs Group", "Accenture", "3M", "Bristol-Myers Squibb", "Dow", "Amgen", "General Electric", 
			"Honeywell International", "United Technologies", "Medtronic", "American Express", "Coca-Cola", "Deutsche Telekom",
			"NextEra Energy", "Intuit", "AstraZeneca", "BHP Group", "Gilead Sciences", "Rio Tinto", "Morgan Stanley",
			"Royal Dutch Shell", "BlackRock", "Bayer", "AbbVie", "BASF", "Vodafone Group", "Thermo Fisher Scientific", "Siemens", 
			"Deutsche Bank", "Danaher", "General Motors", "Enel", "Wells Fargo", "Charter Communications", "Anheuser-Busch InBev", "Novo Nordisk",
			"Sony", "Cigna", "Allianz", "Reliance Industries", "Eli Lilly and Company", "Delta Air Lines", "Ford Motor", "Walgreens Boots Alliance", 
			"SAP", "Cisco", "Phillips 66", "Alibaba Group Holding", "Baidu", "PNC Financial Services", "Nissan Motor", 
			"Sumitomo Mitsui Financial Group", "T-Mobile US", "BASF SE", "AIA Group", "AmerisourceBergen", "Mitsubishi UFJ Financial Group"
	);

	private static Map<String, String> categoryMap = Map.of(
			"Microsft", "Company", 
			"Apple", "Company", 
			"Google", "Company",
			"Musk", "Person",
			"Biden", "Person",
			"Trump", "Person",
			"Messi", "Person",
			"Federer", "Person",
			"USA", "Country",
			"Russia", "Country"
			);
	
	public String getCategory(String entity){
		String ret = "Unknown";
		if(names.contains(entity) || surnames.contains(entity)) ret = "Person";
		if(countries.contains(entity)) ret = "Country";
		if(companies.contains(entity)) ret = "Company";
 
		return ret;
	}
	
	public List<String> getCategoryList() {

		Set<String> set = new HashSet<>(categoryMap.values());
		List<String> result = new ArrayList<String>(set);
		result.add("Unknown");

		return result;
	}



	public abstract boolean isEntity(String word);
		

}
