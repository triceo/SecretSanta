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
        Assert.assertNotEquals(JLOCKER, ZKREJCOV);
        Assert.assertNotSame(JLOCKER, ZKREJCOV);
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
    public void testSameNameEquality() {
        final Person p2 = new Person(LPETROVI.getName());
        Assert.assertEquals(LPETROVI, p2);
        Assert.assertNotSame(LPETROVI, p2);
    }

    @Test
    public void testSameness() {
        Assert.assertEquals(LPETROVI, LPETROVI);
        Assert.assertSame(LPETROVI, LPETROVI);
    }


    @Test
    public void testOrdering() {
        Assert.assertThat(LPETROVI, OrderingComparison.greaterThan(JLOCKER));
        Assert.assertThat(JLOCKER, OrderingComparison.lessThan(LPETROVI));
        // the only case where anyone would be greater than LPETROVI ;-)
        Assert.assertThat(ZKREJCOV, OrderingComparison.greaterThan(LPETROVI));
        Assert.assertThat(LPETROVI, OrderingComparison.lessThan(ZKREJCOV));
        // and finally transitivity
        Assert.assertThat(JLOCKER, OrderingComparison.lessThan(ZKREJCOV));
        Assert.assertThat(ZKREJCOV, OrderingComparison.greaterThan(JLOCKER));
        // equality just in case
        Assert.assertThat(LPETROVI, OrderingComparison.comparesEqualTo(LPETROVI));
    }
}
