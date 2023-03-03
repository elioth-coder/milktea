package milkytea;

public class Flavor {
	int id;
	String image;
	String flavor;
	Double price;
	
	public Flavor setID(int id) {
		this.id = id;
		return this;
	}
	
	public Flavor setImage(String image) {
		this.image = image;
		return this;
	}
	
	public Flavor setFlavor(String flavor) {
		this.flavor = flavor;
		return this;
	}
	
	public Flavor setPrice(Double price) {
		this.price = price;
		return this;
	}
	
	public int    getID()     { return this.id; }
	public String getImage()  { return this.image; }
	public String getFlavor() { return this.flavor; }
	public Double getPrice()  { return this.price; }
}

