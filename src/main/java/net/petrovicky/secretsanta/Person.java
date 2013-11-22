package net.petrovicky.secretsanta;

public class Person implements Comparable<Person> {

    private final String name;

    public Person(final String name) {
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("Name must contain at least 1 non-whitespace characted.");
        }
        this.name = name;
    }

    @Override
    public int compareTo(final Person o) {
        return this.name.compareTo(o.getName());
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Person)) {
            return false;
        }
        final Person other = (Person) obj;
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        return result;
    }

}
