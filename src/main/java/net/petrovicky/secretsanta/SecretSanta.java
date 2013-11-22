package net.petrovicky.secretsanta;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SecretSanta {

    public static void main(final String[] args) {
        // prepare input data
        final List<Person> people = new ArrayList<>();
        for (final String name : args) {
            people.add(new Person(name));
        }
        // prepare data structures
        final Random random = new SecureRandom();
        Collections.shuffle(people, random);
        final Group g = new Group(people, random);
        // assign secret santas
        for (final Person requestee : people) {
            final Person result = g.randomlyRemovePerson(requestee);
            SecretSanta.writeThePick(requestee, result);
        }
    }

    private static void writeThePick(final Person santa, final Person target) {
        final File f = new File(santa.getName());
        try (BufferedWriter w = new BufferedWriter(new FileWriter(f))) {
            w.write("You will become a Secret Santa for " + target.getName());
        } catch (final IOException e) {
            System.out.println("Failed writing the target for " + santa + ".");
        }
    }

}
