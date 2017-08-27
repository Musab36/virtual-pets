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
		 assertTrue(testMonster.equals(anotherMonster));
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
	@Test
	public void save_savesPersonIdIntoDB_true() {
		Person testPerson = new Person("Henry", "henry@Henry.com");
		testPerson.save();
		Monster testMonster = new Monster("Bubbies", testPerson.getId());
		testMonster.save();
		Monster saveMonster = Monster.find(testMonster.getId());
		assertEquals(saveMonster.getPersonId(), testPerson.getId());
	}

	@Test
	public void monster_instantiatesWithHalfFullPlayLevel() {
		Monster testMonster = new Monster("Bubbies", 1);
		assertEquals(testMonster.getPlayLevel(), (Monster.MAX_PLAY_LEVEL / 2));
	}

	@Test
	public void monsters_instantiateWithHalfFullSleepLevel() {
		Monster testMonster = new Monster("Bubbies", 1);
		assertEquals(testMonster.getSleepLevel(), (Monster.MAX_SLEEP_LEVEL / 2));
	}

	@Test
	public void monster_instantiatesWithHalfFullFoodLevel() {
		Monster testMonster =new Monster("Bubbies", 1);
		assertEquals(testMonster.getFoodLevel(), (Monster.MAX_FOOD_LEVEL / 2));
	}

	@Test
	public void isAlive_confirmIfMonsterIsAliveIfAllLevelsAboveMinimum_true() {
		Monster testMonster = new Monster("Bubbies", );
		assertEquals(testMonster.isAlive(), true);
	}

	@Test
	public void depleteLevels_reduceAllLevels() {
		Monster testMonster = new Monster("Bubbies", 1);
		testMonster.depleteLevels();
		assertEquals(testMonster.getFoodLevel(), (Monster.MAX_FOOD_LEVEL / 2) - 1);
		assertEquals(testMonster.getSleepLevel(), (Monster.MAX_SLEEP_LEVEL / 2) - 1);
		assertEquals(testMonster.getPlayLevel(), (Monster.MAX_PLAY_LEVEL / 2) - 1);
	}

	@Test
	public void isAlive_recognizesMonsterIsDeadWhenLevelsReachMinimum_false() {
		Monster testMonster = new Monster("Bubbies", 1);
		for(int i = Monster.MIN_ALL_LEVELS; i <= Monster.MAX_FOOD_LEVEL; i++) {
			testMonster.depleteLevels();
			assertEquals(testMonster.isAlive(), false);
		}
	}

	@Test
		public void play_increasesMonsterPlayLevel() {
			Monster testMonster = new Monster("Bubbies", 1);
			try {
			testMonster.play();
		} catch (UnsupportedOperationException exception) { }
			assertTrue(testMonster.getPlayLevel() > (Monster.MAX_PLAY_LEVEL / 2));
		}
		

		@Test
		public void sleep_increasesMonsterSleepLevel() {
			Monster testMonster = new Monster("Bubbies", 1);
			try {
             testMonster.sleep();
			} catch (UnsupportedOperationException exception) { }
			assertTrue(testMonster.getSleepLevel() > (Monster.MAX_SLEEP_LEVEL / 2));
		}

		@Test
		public void food_increasesMonsterFoodLevel() {
			Monster testMonster = new Monster("Bubbies", 1);
			testMonster.feed();
			assertTrue(testMonster.getFoodLevel() > (Monster.MAX_FOOD_LEVEL / 2));
		}

		@Test
		public void monster_foodLevelCannotGoBeyondMaxValue() {
			Monster testMonster = new Monster("Bubbies", 1);
			for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_FOOD_LEVEL + 2); i++ ) {
				try {
					testMonster.feed();
				} catch (UnsupportedOperationException exception) { }
				
			}

			assertTrue(testMonster.getFoodLevel() <= Monster.MAX_FOOD_LEVEL);
		}

		@Test(expected = UnsupportedOperationException.class)
		public void feed_throwsExceptionIfFoodLevelIsAtMaxValue() {
			Monster testMonster = new Monster("Bubbies", 1);
			for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_FOOD_LEVEL); i++) {
				testMonster.feed();
			}
		}

		@Test(expected = UnsupportedOperationException.class)
		public void play_throwsExceptionIfPlayLevelIsAtMaxValue() {
			Monster testMonster = new Monster("Bubbies", 1);
			for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_PLAY_LEVEL); i++) {
				testMonster.play();
			}
		}

		@Test(expected = UnsupportedOperationException.class)
		public void sleep_throwsExceptionIfSleepLevelIsAtMaxValue() {
			Monster testMonster = new Monster("Bubbies", 1);
			for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_SLEEP_LEVEL); i++) {
				testMonster.sleep();
			}
		}

}