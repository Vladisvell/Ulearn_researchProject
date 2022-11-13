public class Person {
    private final String name;
    private Gender gender = Gender.UNDEFINED;


    public Person(String name){
        this.name = name;
    }


    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return String.format("%s", name);
    }

    public void setGender(Gender newGender){
        this.gender = newGender;
    }

    public Gender getGender(){
        return this.gender;
    }

    enum Gender{
        UNDEFINED,
        FEMALE,
        MALE,
    }
}
