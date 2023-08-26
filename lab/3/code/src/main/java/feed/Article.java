package feed;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import namedEntity.Company;
import namedEntity.Country;
import namedEntity.NamedEntity;
import namedEntity.Person;
import namedEntity.Theme;
import namedEntity.heuristic.Heuristic;

/*Esta clase modela el contenido de un articulo (ie, un item en el caso del rss feed) */

public class Article implements Serializable {
	private String title;
	private String text;
	private Date publicationDate;
	private String link;
	
	private List<NamedEntity> namedEntityList = new ArrayList<NamedEntity>();
	
	
	public Article(String title, String text, Date publicationDate, String link) {
		super();
		this.title = title;
		this.text = text;
		this.publicationDate = publicationDate;
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	@Override
	public String toString() {
		return "Article [title=" + title + ", text=" + text + ", publicationDate=" + publicationDate + ", link=" + link
				+ "]";
	}
	
	
	
	public NamedEntity getNamedEntity(String namedEntity){
		for (NamedEntity n: namedEntityList){
			if (n.getName().compareTo(namedEntity) == 0){				
				return n;
			}
		}
		return null;
	}
	
	public void addNamedEntity(String s, String category) {
		
		NamedEntity ne = this.getNamedEntity(s);
		if (ne == null) {
			if        (category == "PersonSport") {
				this.namedEntityList.add(new  Person(s, category, 1, new Theme("Sport")));
			} else if (category == "PersonPolitics") {
				this.namedEntityList.add(new  Person(s, category, 1, new Theme("Politics")));
			} else if (category == "CountrySport") {
				this.namedEntityList.add(new Country(s, category, 1, new Theme("Sport")));
			} else if (category == "CountryPolitics") {
				this.namedEntityList.add(new Country(s, category, 1, new Theme("Politics")));
			} else if (category == "CompanySport") {
				this.namedEntityList.add(new Company(s, category, 1, new Theme("Sport")));
			} else if (category == "CompanyPolitics") {
				this.namedEntityList.add(new Company(s, category, 1, new Theme("Politics")));
			}
		}else {
			ne.incFrequency();
		}
	}

	public void computeNamedEntities(Heuristic h){
		String text = this.getTitle() + " " +  this.getText();  
			
		String charsToRemove = ".,;:()'!?\n";
		for (char c : charsToRemove.toCharArray()) {
			text = text.replace(String.valueOf(c), "");
		}
			
		for (String s: text.split(" ")) {
			if (h.isEntity(s)){

				NamedEntity ne = this.getNamedEntity(s);
				if (ne == null) {
					String category = h.getCategory(s);
					if        (category == "PersonSport") {
                        this.namedEntityList.add(new  Person(s, category, 1, new Theme("Sport")));
					} else if (category == "PersonPolitics") {
                        this.namedEntityList.add(new  Person(s, category, 1, new Theme("Politics")));
                    } else if (category == "CountrySport") {
                        this.namedEntityList.add(new Country(s, category, 1, new Theme("Sport")));
                    } else if (category == "CountryPolitics") {
                        this.namedEntityList.add(new Country(s, category, 1, new Theme("Politics")));
                    } else if (category == "CompanySport") {
                        this.namedEntityList.add(new Company(s, category, 1, new Theme("Sport")));
                    } else if (category == "CompanyPolitics") {
                        this.namedEntityList.add(new Company(s, category, 1, new Theme("Politics")));
                    }
				}else {
					ne.incFrequency();
				}
			}
		} 
	}

    public List<NamedEntity> getNamedEntities() {
	    List<NamedEntity> ret = new ArrayList<NamedEntity>();
        
        ret.addAll(namedEntityList);
        
        return ret;
    }
	
	public void prettyPrint() {
		String text = this.getText().replaceAll("\\r|\\n", " ");
		if (text.length() > 120)
			text = text.substring(0, 117) + "...";
		System.out.println("**********************************************************************************************");
		System.out.println("Title: " + this.getTitle());
		System.out.println("Publication Date: " + this.getPublicationDate());
		System.out.println("Link: " + this.getLink());
		System.out.println("Text: " + text);
		System.out.println("**********************************************************************************************");
		
	}
	
	public static void main(String[] args) {
		  Article a = new Article("This Historically Black University Created Its Own Tech Intern Pipeline",
			  "A new program at Bowie State connects computing students directly with companies, bypassing an often harsh Silicon Valley vetting process",
			  new Date(),
			  "https://www.nytimes.com/2023/04/05/technology/bowie-hbcu-tech-intern-pipeline.html"
			  );
		 
		  a.prettyPrint();
	}
	
	
}



