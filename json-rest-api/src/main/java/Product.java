import lombok.Data;

@Data
public class Product implements Validable{
	private String name;
	private int price;
	private String description; 
	private String brand;
	private int id;

	public boolean isValid() {
	    return !name.isEmpty() 
		&& price > 0
	    && !description.isEmpty()
	    && !brand.isEmpty()
	    && id > 0;
	}
}