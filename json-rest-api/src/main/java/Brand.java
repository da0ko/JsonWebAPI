import lombok.Data;

@Data
public class Brand implements Validable {
	private String name;
	private String country;
	private int id; 

	public boolean isValid() {
		return !name.isEmpty()
		&& !country.isEmpty()
		&& id > 0; 
	}
}