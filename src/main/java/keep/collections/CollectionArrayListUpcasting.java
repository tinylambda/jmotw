package keep.collections;

import java.util.ArrayList;

class Human {
    private final String name;
    public Human(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
class Teacher extends Human {
    public Teacher(String name) {
        super(name);
    }

    public void teach() {
        System.out.println("I can teach !");
    }
}
class Student extends Human {
    public Student(String name) {
        super(name);
    }

    public void study() {
        System.out.println("I can study !");
    }
}

public class CollectionArrayListUpcasting {
    public static void main(String[] args) {
        ArrayList<Human> persons = new ArrayList<Human>();
        persons.add(new Teacher("Tom"));
        persons.add(new Student("Felix"));
        persons.add(new Student("Jenny"));

        for(int i=0; i<persons.size(); i++) {
            Human person = persons.get(i);
            // person.teach() not works
            // person.study() not works
            System.out.println(person.getName());
        }
    }
}
