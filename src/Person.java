public class Person {
    private final String name;
    private final String surname;


    public Person(String name, String surname){
        this.name = name;
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return String.format("%s %s", name, surname);
    }

    enum Gender{
        Male,
        Female
    }
}
