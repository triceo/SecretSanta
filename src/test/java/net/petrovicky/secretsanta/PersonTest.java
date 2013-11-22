package net.petrovicky.secretsanta;

import org.hamcrest.number.OrderingComparison;
import org.junit.Assert;
import org.junit.Test;

public class PersonTest {

    private static final Person JLOCKER = new Person("Jiříček");
    private static final Person LPETROVI = new Person("Lukášek");
    private static final Person ZKREJCOV = new Person("Zuzanka");

    @Test
    public void testDifferentNameInequality() {
        Assert.assertNotEquals(PersonTest.JLOCKER, PersonTest.ZKREJCOV);
        Assert.assertNotSame(PersonTest.JLOCKER, PersonTest.ZKREJCOV);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyName() {
        new Person(" ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullName() {
        new Person(null);
    }

    @Test
    public void testOrdering() {
        Assert.assertThat(PersonTest.LPETROVI, OrderingComparison.greaterThan(PersonTest.JLOCKER));
        Assert.assertThat(PersonTest.JLOCKER, OrderingComparison.lessThan(PersonTest.LPETROVI));
        // this is just a training excercise; no one is greater than lpetrovi
        Assert.assertThat(PersonTest.ZKREJCOV, OrderingComparison.greaterThan(PersonTest.LPETROVI));
        Assert.assertThat(PersonTest.LPETROVI, OrderingComparison.lessThan(PersonTest.ZKREJCOV));
        // and finally transitivity
        Assert.assertThat(PersonTest.JLOCKER, OrderingComparison.lessThan(PersonTest.ZKREJCOV));
        Assert.assertThat(PersonTest.ZKREJCOV, OrderingComparison.greaterThan(PersonTest.JLOCKER));
        // equality just in case
        Assert.assertThat(PersonTest.LPETROVI, OrderingComparison.comparesEqualTo(PersonTest.LPETROVI));
    }

    @Test
    public void testSameNameEquality() {
        final Person p2 = new Person(PersonTest.LPETROVI.getName());
        Assert.assertEquals(PersonTest.LPETROVI, p2);
        Assert.assertNotSame(PersonTest.LPETROVI, p2);
    }

    @Test
    public void testSameness() {
        Assert.assertEquals(PersonTest.LPETROVI, PersonTest.LPETROVI);
        Assert.assertSame(PersonTest.LPETROVI, PersonTest.LPETROVI);
    }
}
