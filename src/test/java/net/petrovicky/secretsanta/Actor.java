package net.petrovicky.secretsanta;

public enum Actor {

    JLOCKER("Jiříček"), JPECHANE("Děda Pechanec"), LPETROVI("Lukášek"), MWINKLER("Otec Winkler"), RSYNEK("Strejda Synek"), ZKREJCOV("Zuzanka");

    private final Person person;

    Actor(final String name) {
        this.person = new Person(name);
    }

    public Person getPerson() {
        return this.person;
    }

}
