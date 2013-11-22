package net.petrovicky.secretsanta;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

public class Group {

    private final List<Person> people;
    private final Random random;

    public Group(final Collection<Person> people) {
        this(people, new SecureRandom());
    }

    public Group(final Collection<Person> people, final Random random) {
        if (people == null || people.size() == 0) {
            throw new IllegalArgumentException("There must be at least 1 person in the group.");
        } else if (random == null) {
            throw new IllegalArgumentException("Please provide a non-null random number generator.");
        }
        this.random = random;
        this.people = new ArrayList<>(new TreeSet<Person>(people));
        Collections.shuffle(this.people, random);
    }

    public Collection<Person> getRemainingPeople() {
        return Collections.unmodifiableCollection(this.people);
    }

    private Person pickRandomPerson() {
        return this.people.get(this.random.nextInt(this.people.size()));
    }

    public Person randomlyRemovePerson() {
        return this.randomlyRemovePerson(null);
    }

    public synchronized Person randomlyRemovePerson(final Person requestee) {
        if (this.people.size() == 0) {
            throw new IllegalStateException("No one is left to remove.");
        }
        Person p = requestee;
        while (p == requestee) {
            p = this.pickRandomPerson();
        }
        this.people.remove(p);
        return p;
    }

}
