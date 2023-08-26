package namedEntity;


/*Esta clase modela la nocion de entidad nombrada*/

import java.io.Serializable;

public class NamedEntity implements Serializable {
	String name;
	String category; 
	int frequency;
	
	public NamedEntity(String name, String category, int frequency) {
		super();
		this.name = name;
		this.category = category;
		this.frequency = frequency;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public void incFrequency() {
		this.frequency++;
	}

	@Override
	public String toString() {
		return "ObjectNamedEntity [name=" + name + ", frequency=" + frequency + "]";
	}
	public void prettyPrint(){
		System.out.println(this.getName() + " " + this.getFrequency());
	}
	
	
}



