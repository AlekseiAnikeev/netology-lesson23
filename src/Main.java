import java.util.*;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 04.09.2022
 */
public class Main {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        showMinors(persons);
        List<String> recruit = createRecruitList(persons);
        //recruit.forEach(System.out::println);
        List<String> ableBodiedPopulation = ableBodiedPopulation(persons);
        //ableBodiedPopulation.forEach(System.out::println);
    }

    private static List<String> ableBodiedPopulation(Collection<Person> persons) {
        return persons.stream()
                .filter(p -> Education.HIGHER.equals(p.getEducation()))
                .filter(p -> Sex.WOMAN.equals(p.getSex()) ? p.getAge() >= 18 && p.getAge() < 60 : p.getAge() >= 18 && p.getAge() < 65)
                .sorted(Comparator.comparing(Person::getFamily))
                .map(Person::getFamily)
                .toList();
    }

    private static List<String> createRecruitList(Collection<Person> persons) {
        return persons.stream()
                .filter(p -> p.getAge() >= 18 && p.getAge() <= 27 && p.getSex().equals(Sex.MAN))
                .map(Person::getFamily)
                .toList();
    }

    private static void showMinors(Collection<Person> persons) {
        long count = persons.stream()
                .filter(p -> p.getAge() < 18)
                .count();
        System.out.println("Зарегистрировано несовершеннолетних: " + count);
    }
}
