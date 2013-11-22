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

    private static final Person P1 = Actor.MWINKLER.getPerson();
    private static final Person P2 = Actor.RSYNEK.getPerson();
    private static final Person P3 = Actor.JPECHANE.getPerson();
    private static final Person P4 = Actor.LPETROVI.getPerson();

    // all possible options of 4 actors, many random seeds
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new Random(0), GroupRemovalTest.P4, GroupRemovalTest.P1, GroupRemovalTest.P2, GroupRemovalTest.P3},
                {new Random(1), GroupRemovalTest.P4, GroupRemovalTest.P1, GroupRemovalTest.P3, GroupRemovalTest.P2},
                {new Random(2), GroupRemovalTest.P4, GroupRemovalTest.P2, GroupRemovalTest.P1, GroupRemovalTest.P3},
                {new Random(3), GroupRemovalTest.P4, GroupRemovalTest.P2, GroupRemovalTest.P3, GroupRemovalTest.P1},
                {new Random(4), GroupRemovalTest.P4, GroupRemovalTest.P3, GroupRemovalTest.P1, GroupRemovalTest.P2},
                {new Random(5), GroupRemovalTest.P4, GroupRemovalTest.P3, GroupRemovalTest.P2, GroupRemovalTest.P1},
                {new Random(6), GroupRemovalTest.P3, GroupRemovalTest.P1, GroupRemovalTest.P2, GroupRemovalTest.P4},
                {new Random(7), GroupRemovalTest.P3, GroupRemovalTest.P1, GroupRemovalTest.P4, GroupRemovalTest.P2},
                {new Random(8), GroupRemovalTest.P3, GroupRemovalTest.P2, GroupRemovalTest.P1, GroupRemovalTest.P4},
                {new Random(9), GroupRemovalTest.P3, GroupRemovalTest.P2, GroupRemovalTest.P4, GroupRemovalTest.P1},
                {new Random(10), GroupRemovalTest.P3, GroupRemovalTest.P4, GroupRemovalTest.P1, GroupRemovalTest.P2},
                {new Random(11), GroupRemovalTest.P3, GroupRemovalTest.P4, GroupRemovalTest.P2, GroupRemovalTest.P1},
                {new Random(12), GroupRemovalTest.P2, GroupRemovalTest.P1, GroupRemovalTest.P3, GroupRemovalTest.P4},
                {new Random(13), GroupRemovalTest.P2, GroupRemovalTest.P1, GroupRemovalTest.P4, GroupRemovalTest.P3},
                {new Random(14), GroupRemovalTest.P2, GroupRemovalTest.P3, GroupRemovalTest.P1, GroupRemovalTest.P4},
                {new Random(15), GroupRemovalTest.P2, GroupRemovalTest.P3, GroupRemovalTest.P4, GroupRemovalTest.P1},
                {new Random(16), GroupRemovalTest.P2, GroupRemovalTest.P4, GroupRemovalTest.P1, GroupRemovalTest.P3},
                {new Random(17), GroupRemovalTest.P2, GroupRemovalTest.P4, GroupRemovalTest.P3, GroupRemovalTest.P1},
                {new Random(18), GroupRemovalTest.P1, GroupRemovalTest.P2, GroupRemovalTest.P3, GroupRemovalTest.P4},
                {new Random(19), GroupRemovalTest.P1, GroupRemovalTest.P2, GroupRemovalTest.P4, GroupRemovalTest.P3},
                {new Random(20), GroupRemovalTest.P1, GroupRemovalTest.P3, GroupRemovalTest.P2, GroupRemovalTest.P4},
                {new Random(21), GroupRemovalTest.P1, GroupRemovalTest.P3, GroupRemovalTest.P4, GroupRemovalTest.P2},
                {new Random(22), GroupRemovalTest.P1, GroupRemovalTest.P4, GroupRemovalTest.P2, GroupRemovalTest.P3},
                {new Random(23), GroupRemovalTest.P1, GroupRemovalTest.P4, GroupRemovalTest.P3, GroupRemovalTest.P2}
});
    }

    private final Person firstToAct;
    private final Group group;
    private final Person secondToAct;
    private final Person thirdToAct;
    private final Person lastToAct;

    public GroupRemovalTest(final Random random, final Person person1, final Person person2, final Person person3, final Person person4) {
        this.firstToAct = person1;
        this.secondToAct = person2;
        this.thirdToAct = person3;
        this.lastToAct = person4;
        this.group = new Group(Arrays.asList(new Person[]{person1, person2, person3, person4}), random);
    }

    @Test
    public void test() {
        final Person firstRemoved = this.group.randomlyRemovePerson(this.firstToAct);
        Assert.assertNotEquals(this.firstToAct, firstRemoved);
        final Person secondRemoved = this.group.randomlyRemovePerson(this.secondToAct);
        Assert.assertNotEquals(this.secondToAct, secondRemoved);
        final Person thirdRemoved = this.group.randomlyRemovePerson(this.thirdToAct);
        Assert.assertNotEquals(this.thirdToAct, thirdRemoved);
        final Person lastRemoved = this.group.randomlyRemovePerson(this.lastToAct);
        Assert.assertNotEquals(this.lastToAct, lastRemoved);
        // make sure all have been chosen
        Assert.assertTrue("The group should have been empty.", this.group.getRemainingPeople().isEmpty());
        final Collection<Person> people = new TreeSet<>();
        people.add(firstRemoved);
        people.add(secondRemoved);
        people.add(thirdRemoved);
        people.add(lastRemoved);
        Assert.assertEquals("Not all people have been picked.", 4, people.size());
    }

}
