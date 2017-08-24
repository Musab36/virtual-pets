import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

	@Override
	protected void before() {
		DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/virtual_pets?user=postgres&password=31546Bbd", null, null);
	}

	@Override
	protected void after() {
		try(Connection con = DB.sql2o.open()) {
			String deltePersonsQuery = "DELETE FROM persons *;";
      String deleteMonstersQuery = "DELETE FROM monsters *;";
			con.createQuery(deltePersonsQuery).executeUpdate();
		}
	}
}
