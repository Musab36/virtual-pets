import java.util.Arrays;
import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;	

public class PersonTest {

	@Rule
	public DatabaseRule database = new DatabaseRule();

	@Test
	public void person_instantiatesCorrectly_true() {
		Person testPerson = new Person("Henry", "[email protected]");
		assertEquals(true, testPerson instanceof Person);
	}

	@Test
	public void getName_personInstantiatesWithName_Henry() {
		Person testPerson = new Person("Henry", "[email protected]");
		assertEquals("Henry", testPerson.getName());
	}

	@Test
	public void getEmail_personInstantiatesWithEmail_String() {
		Person testPerson = new Person("Henry", "[email protected]");
		assertEquals("[email protected]", testPerson.getEmail());
	}

	@Test
	public void equals_returnsTrueIfNameAndEmailAreSame_true() {
		Person firstPerson = new Person("Henry", "[email protected]");
		Person anotherPerson = new Person("Henry", "[email protected]");
		assertTrue(firstPerson.equals(anotherPerson));
	}

	// Database
	@Test
	public void save_insertsObjectIntoDatabase_person() {
		Person testPerson = new Person("Henry", "[email protected]");
		testPerson.save();
		assertTrue(Person.all().get(0).equals(testPerson));
	}

	// Returning all database enteries
	@Test
	public void all_returnsAllInstancesOfPerson_true() {
		Person firstPerson = new Person("Henry", "henry@henry.com");
		firstPerson.save();
		Person secondPerson = new Person("Harriet", "harriet@harriet.com");
		secondPerson.save();
		assertEquals(true, Person.all().get(0).equals(firstPerson));
		assertEquals(true, Person.all().get(1).equals(secondPerson));
	}

	// Assigning ids
	@Test
	public void save_assignsIdObject() {
		Person testPerson = new Person("Henry", "henry@henry.com");
		testPerson.save();
		Person savePerson = Person.all().get(0);
		assertEquals(testPerson.getId(), savePerson.getId());
	}

	// Finding persons based on their ids
	@Test
	public void find_returnsPersonWithSameId_secondPerson() {
		Person firstPerson = new Person("Henry", "henry@henry.com");
		firstPerson.save();
		Person secondPerson = new Person("Harriet", "harriet@harriet.com");
		secondPerson.save();
		assertEquals(Person.find(secondPerson.getId()), secondPerson);
	}

	//
	@Test
	public void getMonsters_retievesAllMonstersFromTheDatabase_monsterList() {
		Person testPerson = new Person("Henry", "henry@henry.com");
		testPerson.save();
		FireMonster firstMonster = new FireMonster("Bubbies", testPerson.getId());
		firstMonster.save();
		FireMonster secondMonster = new FireMonster("Spud", testPerson.getId());
		secondMonster.save();
		Object[] monsters = new Object[] { firstMonster, secondMonster };
		assertTrue(testPerson.getMonsters().containsAll(Arrays.asList(monsters)));
	}

	// Getting communities a person belongs to
	@Test
	public void getCommunities_ReturnAllCommunites_List() {
		Community testCommunity = new Community("Fire Enthusiasts", "Flame on!");
		testCommunity.save();
		Person testPerson = new Person("Henry", "[email protected]");
		testPerson.save();
		testCommunity.addPerson(testPerson);
		List savedCommunities = testPerson.getCommunities();
		assertEquals(1, savedCommunities.size());
	}
}