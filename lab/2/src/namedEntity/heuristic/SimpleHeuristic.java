package namedEntity.heuristic;

import java.util.List;

public class SimpleHeuristic extends Heuristic{

    private static List<String> simpleEntities = List.of( 
        "Messi", "Trump", "Adidas", "Twitter", "Scaloneta", "Argentina"
    );
    private static List<String> simpleCategories = List.of( 
        "PersonSport","PersonPolitics", 
        "CountrySport","CountryPolitics", 
        "CompanySport" , "CompanyPolitics" ,
        "Unknown"
    );

    @Override
    public boolean isEntity(String word) {
        
        return simpleEntities.contains(word);
    }


    public boolean isPerson() {
        return true;
    }

    public boolean isCompany() {
        return true;
    }
    public boolean isCountry() {
        return true;
    }



    @Override
    public String getCategory(String entity){
        String ret = "Unknown";
        switch (entity) {
            case "Messi":
                ret = "PersonSport";
                break;
            case "Trump":
                ret = "PersonPolitics";
                break;
            case "Adidas":
                ret = "CompanySport";
                break;
            
            case "Twitter":
                ret = "CompanyPolitics";
                break;
            
            case "Scaloneta":
                ret = "CountrySport";
                break;
            case "Argentina":
                ret = "CountryPolitics";
                break;
        
            default:
                break;
        }
        return ret;
    }

    @Override 
    public List<String> getCategoryList(){
        return simpleCategories;

    }
  
}
