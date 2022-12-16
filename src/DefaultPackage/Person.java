package DefaultPackage;

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

    public void setGenderByCode(int genderCode){
        this.gender = Gender.values()[genderCode];
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Gender getGender(){
        return this.gender;
    }

    public enum Gender{
        UNDEFINED,
        FEMALE,
        MALE,
    }
}
