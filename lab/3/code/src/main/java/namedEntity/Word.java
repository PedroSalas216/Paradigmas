package namedEntity;

public class Word {
  
  private String formaCanonica;
  private String origen;
  
  public Word(String formaCanonica, String origen) {
    this.formaCanonica = formaCanonica;
    this.origen = origen;
  }

  public String getFormaCanonica() {
    return formaCanonica;
  }

  public String getOrigen() {
    return origen;
  }

  public void setOrigen(String origen) {
    this.origen = origen;
  }

  public void setFormaCanonica(String formaCanonica) {
    this.formaCanonica = formaCanonica;
  }
  
}
