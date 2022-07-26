package tacos.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tacos.Ingredient;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {
	private JdbcTemplate jbdcTemplate;
	
	@Autowired
	public JdbcIngredientRepository(JdbcTemplate jbdcTemplate) {
		this.jbdcTemplate = jbdcTemplate;
	}

	@Override
	public List<Ingredient> findAll() {
		return jbdcTemplate.query(
				"select id, name, type from Ingredient",
				this::mapRowToIngredient
		);
	}

	@Override
	public Optional<Ingredient> findById(String id) {
		List<Ingredient> result = jbdcTemplate.query(
				"select id, name, type from Ingredient where id=?", 
				this::mapRowToIngredient,
				id);
		
		return result.size() == 0 ? Optional.empty() : Optional.of(result.get(0));
	}

	@Override
	public Ingredient save(Ingredient ingredient) {
		jbdcTemplate.update(
				"insert into Ingredient (id, name, type) values (?, ?, ?)",
				ingredient.getId(),
				ingredient.getName(),
				ingredient.getType());
		return ingredient;
		
	}
	
	private Ingredient mapRowToIngredient(ResultSet row, int rowNum) throws SQLException {
		return new Ingredient(
				row.getString("id"),
				row.getString("name"), 
				Ingredient.Type.valueOf(row.getString("type"))
			);
	}
}
