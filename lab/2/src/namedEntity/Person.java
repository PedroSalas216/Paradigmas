package namedEntity;

public class Person extends NamedEntity {
  
  private Integer id;
  private Theme tema;
  private Word apellido;
  private Word nombre;

  
  public Person(String name, String category, int frequency, Theme theme) {
    super(name, category, frequency);
    this.setTema(theme);
  }


  public Word getNombre() {
    return nombre;
  }


  public void setNombre(Word nombre) {
    this.nombre = nombre;
  }


  public Word getApellido() {
    return apellido;
  }


  public void setApellido(Word apellido) {
    this.apellido = apellido;
  }


  public Theme getTema() {
    return tema;
  }


  public void setTema(Theme tema) {
    this.tema = tema;
  }


  public Integer getId() {
    return id;
  }


  public void setId(Integer id) {
    this.id = id;
  }


}
