package namedEntity;


public class Country extends NamedEntity {

  private Word nombre;
  private Integer poblacion;
  private String lenguaje;
  private Theme tema;

  public Country(String name, String category, int frequency, Theme tema) {
    super(name, category, frequency);
    this.tema = tema;
  }

  
  public Theme getTema() {
    return tema;
  }

  public Word getNombre() {
    return nombre;
  }

  public void setNombre(Word nombre) {
    this.nombre = nombre;
  }

  public Integer getPoblacion() {
    return poblacion;
  }

  public void setPoblacion(Integer poblacion) {
    this.poblacion = poblacion;
  }

  public String getLenguaje() {
    return lenguaje;
  }

  public void setLenguaje(String lenguaje) {
    this.lenguaje = lenguaje;
  }

  public void setTema(Theme tema) {
    this.tema = tema;
  }

}
