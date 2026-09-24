package tacos.models;

import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.JoinColumn;

@Data
@Entity
@Table(name = "taco")
@NoArgsConstructor()
public class Taco {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	@NotNull
	@Size(min=5, message="il nome deve contenere almeno 5 caratteri")
	private String name;
	
	@NotNull
	@Size(min=1, message = "Devi scegliere almeno 1 ingrediente")
	
	@ManyToMany
	@JoinTable(
	    name = "taco_ingredient",
	    joinColumns = @JoinColumn(name = "taco_id"),
	    inverseJoinColumns = @JoinColumn(name = "ingredient_id")
	)
	private List<Ingredient> ingredients;

}
