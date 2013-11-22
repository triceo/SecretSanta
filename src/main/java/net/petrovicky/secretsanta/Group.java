package net.petrovicky.secretsanta;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Group {

    private static final Logger LOGGER = LoggerFactory.getLogger(Group.class);

    private final SortedSet<Person> beenPickingAlready = new TreeSet<>();
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
        Group.LOGGER.info("New group: {}.", this.people);
    }

    public Collection<Person> getRemainingPeople() {
        return Collections.unmodifiableCollection(this.people);
    }

    private Person pickRandomPerson() {
        return this.people.get(this.random.nextInt(this.people.size()));
    }

    private Person pickSecondToLast(final Person requestee, final int index) {
        final Person p = this.people.remove(index);
        this.beenPickingAlready.add(requestee);
        Group.LOGGER.debug("{} picks the second-to-last remaining person: {}.", requestee, p);
        return p;
    }

    public synchronized Person randomlyRemovePerson(final Person requestee) {
        Group.LOGGER.info("{} is picking.", requestee);
        Group.LOGGER.trace("The following people have already been picking: {}.", this.beenPickingAlready);
        Group.LOGGER.trace("The following people are remaining: {}.", this.people);
        if (this.people.size() == 0) {
            throw new IllegalStateException("No one is left to remove.");
        } else if (this.people.size() == 1) {
            final Person p = this.people.remove(0);
            Group.LOGGER.debug("{} picks the last remaining person: {}.", requestee, p);
            this.beenPickingAlready.clear(); // don't hold stale information
            return p;
        } else if (this.people.size() == 2) {
            final Person firstPerson = this.people.get(0);
            if (this.people.contains(requestee)) {
                // pick the other person at all costs
                if (firstPerson.equals(requestee)) {
                    return this.pickSecondToLast(requestee, 1);
                } else {
                    return this.pickSecondToLast(requestee, 0);
                }
            } else {
                // pick the person that hasn't yet been picking
                if (this.beenPickingAlready.contains(firstPerson)) {
                    return this.pickSecondToLast(requestee, 1);
                } else {
                    return this.pickSecondToLast(requestee, 0);
                }
            }
        }
        // more than 2 people remain
        this.beenPickingAlready.add(requestee);
        Person p = requestee;
        while (p == requestee) {
            p = this.pickRandomPerson();
        }
        this.people.remove(p);
        Group.LOGGER.debug("{} picks person: {}.", requestee, p);
        return p;
    }

}
