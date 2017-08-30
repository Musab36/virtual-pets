import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Community {
	private String name;
	private String description;
	private int id;

	public Community(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getId() {
		return id;
	}

	public void addPerson(Person person) {
		try(Connection con = DB.sql2o.open()) {
			String sql = "INSERT INTO communities_persons (community_id, person_id) VALUES (:community_id, person_id)";
			con.createQuery(sql)
			.addParameter("community_id", this.getId())
			.addParameter("person_id", person.getId())
			.executeUpdate();
		}
	}

	public void save() {
		try(Connection con = DB.sql2o.open()) {
			String sql = "INSERT INTO communities (name, description) VALUES (:name, :description)";
			this.id = (int) con.createQuery(sql, true)
			.addParameter("name", this.name)
			.addParameter("description", this.description)
			.executeUpdate()
			.getKey();
		}
	}

	@Override
	public boolean equals(Object otherCommunity) {
		if(!(otherCommunity instanceof Community)) {
			return false;
		} else {
			Community newCommunity = (Community) otherCommunity;
			return this.getName().equals(newCommunity.getName()) &&
			this.getDescription().equals(newCommunity.getDescription());
		}
	}

	public static List<Community> all() {
		String sql = "SELECT * FROM communities";
		try(Connection con = DB.sql2o.open()) {
			return con.createQuery(sql).executeAndFetch(Community.class);
		}
	}

	public List<Person> getPersons() {
		try(Connection con = DB.sql2o.open()) {
			String joinQuery = "SELECT person_id FROM communities_persons WHERE community_id = :community_id";
			List<Integer> personIds = con.createQuery(joinQuery)
			.addParameter("community_id", this.getId())
			.executeAndFetch(Integer.class);

			List<Person> persons = new ArrayList<Person>();

			for(Integer personId : personIds) {
				String personQuery = "SELECT * FROM persons WHERE id = :personId";
				Person person = con.createQuery(personQuery)
				.addParameter("personId", personId)
				.executeAndFetchFirst(Person.class)
				persons.add(persons);
			} 
			return persons;
		}
	}
}