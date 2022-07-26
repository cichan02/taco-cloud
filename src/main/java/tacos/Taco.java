package tacos;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Taco {
	private long id;
	
	private Date createAt = new Date();
	
	@NotNull
	@Size(min = 4, message = "Name must be at least 4 characters long")
	private String name;
	
	@NotNull
	@Size(min = 1, message = "You must choose at least 1 ingredient")
	private List<Ingredient> ingredients;
}
