package namedEntity;

import java.io.Serializable;

public class Theme implements Serializable {
  private String type;

  public Theme(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


}
