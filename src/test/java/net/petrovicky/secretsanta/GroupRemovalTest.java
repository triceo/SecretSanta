package net.petrovicky.secretsanta;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class GroupRemovalTest {

    private static final Person P1 = new Person("Strejda Winkler");
    private static final Person P2 = new Person("Otec Synek");
    private static final Person P3 = new Person("Děda Pechanec");

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new Random(0), GroupRemovalTest.P1, GroupRemovalTest.P2, GroupRemovalTest.P3},
                {new Random(1), GroupRemovalTest.P1, GroupRemovalTest.P3, GroupRemovalTest.P2},
                {new Random(2), GroupRemovalTest.P2, GroupRemovalTest.P1, GroupRemovalTest.P3},
                {new Random(3), GroupRemovalTest.P2, GroupRemovalTest.P3, GroupRemovalTest.P1},
                {new Random(4), GroupRemovalTest.P3, GroupRemovalTest.P1, GroupRemovalTest.P2},
                {new Random(5), GroupRemovalTest.P3, GroupRemovalTest.P2, GroupRemovalTest.P1}

        });
    }

    private final Person firstToAct;

    private final Group group;
    private final Person secondToAct;
    private final Person thirdToAct;

    public GroupRemovalTest(final Random random, final Person person1, final Person person2, final Person person3) {
        this.firstToAct = person1;
        this.secondToAct = person2;
        this.thirdToAct = person3;
        this.group = new Group(Arrays.asList(new Person[]{person1, person2, person3}), random);
    }

    @Test
    public void test() {
        final Person firstRemoved = this.group.randomlyRemovePerson(this.firstToAct);
        Assert.assertNotEquals(this.firstToAct, firstRemoved);
        final Person secondRemoved = this.group.randomlyRemovePerson(this.secondToAct);
        Assert.assertNotEquals(this.secondToAct, secondRemoved);
        final Person thirdRemoved = this.group.randomlyRemovePerson(this.thirdToAct);
        Assert.assertNotEquals(this.thirdToAct, thirdRemoved);
        // make sure all are removed
        try {
            this.group.randomlyRemovePerson();
            Assert.fail("There should have been no one else to remove.");
        } catch (final IllegalStateException ex) {
            Assert.assertTrue("The group should have been empty.", this.group.getRemainingPeople().isEmpty());
        }
        // make sure all have been chosen
        final Collection<Person> people = new TreeSet<>();
        people.add(firstRemoved);
        people.add(secondRemoved);
        people.add(thirdRemoved);
        Assert.assertEquals("Not all people have been picked.", 3, people.size());
    }

}
