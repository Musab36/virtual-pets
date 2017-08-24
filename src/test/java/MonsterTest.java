import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class MonsterTest {

	@Rule
	public DatabaseRule database = new DatabaseRule();

	@Test
	public void monster_instantiatesCorrectly_true() {
		Monster testMonster = new Monster("Bubbies", 1);
		assertEquals(true, testMonster instanceof Monster);
	}

	// Monster attributes
	@Test
	public void monster_instantiatesWithName_String() {
		Monster testMonster = new Monster("Bubbies", 1);
		assertEquals("Bubbies", testMonster.getName());
	}

	@Test
	public void monster_instantiatesWithPersonId_true() {
		Monster testMonster = new Monster("Bubbies", 1);
		assertEquals(1, testMonster.getId());
	}
	// Overriding
	@Test
	public void equlas_returnsTrueIfNameAndPersonIdAreSame_true() {
		 Monster testMonster = new Monster("Bubbies", 1);
		 Monster anotherMonster = new Monster("Bubbies", 1);
		 assertEquals(testMonster.equals(anotherMonster));
	}

	// Saving Monsters
	public void save_returnsTrueIfDescriptionsAreTheSame() {
		Monster testMonster = new Monster("Bubbies", 1);
		testMonster.save();
		assertTrue(Monster.all().get(0).equals(testMonster));
	}

	// Monsters ids
	@Test
	public void save_assignsMonstersWithId() {
		Monster testMonster = new Monster("Bubbies", 1);
		testMonster.save();
		Monster saveMonster = Monster.all().get(0);
		assertEquals(saveMonster.getId(), testMonster.getId());
	}

	// Returning all database entries
	@Test
	public void all_returnsAllInstancesOfMonster_true() {
		Monster firstMonster = new Monster("Bubbies", 1);
		firstMonster.save();
		Monster secondMonster = new Monster("Spud", 1);
		secondMonster.save();
		assertEquals(true, Monster.all().get(0).equals(firstMonster));
		assertEquals(true, Monster.all().get(1).equals(firstMonster));
	}

	// Finding Monsters
	@Test
	public void find_returnsMonstersWithSameId_secondMonster() {
		Monster firstMonster = new Monster("Bubbies", 1);
		firstMonster.save();
		Monster secondMonster = new Monster("Spud", 1);
		secondMonster.save();
		assertEquals(Monster.find(secondMonster.getId()), secondMonster);
	}

	// One to many relations
	/*
	@Test
	public void save_savesPersonIdIntoDB_true() {
		Person testPerson = new Person("Henry", "henry@Henry.com");
		testPerson.save();
		*/
	}
}