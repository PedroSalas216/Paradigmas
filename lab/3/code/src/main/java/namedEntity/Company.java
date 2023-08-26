package namedEntity;

public class Company extends NamedEntity {

    private String formaCanonica;
    private Integer cantEmpleados;
    private Theme tema;

    public Company(String name, String category, int frequency, Theme tema) {
      super(name, category, frequency);
      this.setTema(tema);
    }
    
    public Theme getTema() {
      return tema;
    }
    
    public void setTema(Theme tema) {
      this.tema = tema;
    }
    
    public Integer getCantEmpleados() {
      return cantEmpleados;
    }

    public void setCantEmpleados(Integer cantEmpleados) {
      this.cantEmpleados = cantEmpleados;
    }

    public String getFormaCanonica() {
      return formaCanonica;
    }
    
    public void setFormaCanonica(String formaCanonica) {
      this.formaCanonica = formaCanonica;
    }
    

  
}
