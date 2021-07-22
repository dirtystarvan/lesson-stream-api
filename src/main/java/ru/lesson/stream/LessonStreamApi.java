package ru.lesson.stream;

import ru.lesson.stream.dto.Employee;
import ru.lesson.stream.dto.PositionType;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Задачи для семинара по Stream API.
 */
public class LessonStreamApi {

    public static void main(String[] args) {
        Employee employee1 = new Employee(1, "Ivan", 40);
        Employee employee2 = new Employee(2, "Olga", 50);
        Employee employee3 = new Employee(3, "John", 60);
        Employee employee4 = new Employee(4, "Tom", 85);
        List<Employee> employees = Arrays.asList(
                employee1, employee2, employee2, employee3, employee3, employee4, employee4
        );

        LessonStreamApi lessonStreamApi = new LessonStreamApi();

        List<Employee> result = lessonStreamApi.task1(employees);
    }

    /**
     * Задача №1.
     * Получить список сотрудников, у которых рейтинг {@link Employee#getRating()} больше 50.
     * Важно: Необходимо учесть, что List<Employee> employees может содержать дублирующие записи.
     */
    public List<Employee> task1(List<Employee> employees) {

        return employees.stream()
                .distinct()
                .filter(emp -> emp.getRating() > 50)
                .collect(Collectors.toList());
    }

    /**
     * Задача №2.
     * Получить список сотрудников (List<String> - формат строки {@link Employee#getName()}"="{@link Employee#getRating()})
     * у которых рейтинг {@link Employee#getRating()} меньше 50.
     */
    public List<String> task2(List<Employee> employees) {
        return employees.stream()
                .filter(emp -> emp.getRating() < 50)
                .map(emp -> emp.getName() + "=" + emp.getRating())
                .collect(Collectors.toList());

        //Ivan=40, Bob=45
        //return null;
    }

    /**
     * Задача №3.
     * Получить средний райтинг всех сотрудников.
     */
    public double task3(List<Employee> employees) {
        OptionalDouble od = employees.stream()
                .mapToInt(Employee::getRating)
                .average();

        return od.isPresent() ? od.getAsDouble() : 0;
    }

    /**
     * Задача №4.
     * Получить общий список сотрудников из разных отделов и
     * отсортировать его по убыванию райтинга {@link Employee#getRating()}.
     * Важно: Необходимо учесть, что один сотрудник может числиться в различных отделах, устранить дублирование.
     *
     * @param employeeDepartments список сотрудников различных отделов
     * @return список сотрудников
     */
    public List<Employee> task4(List<List<Employee>> employeeDepartments) {
        return employeeDepartments.stream()
                .flatMap(Collection::stream)
                .distinct()
                .sorted(new Comparator<Employee>() {
                    @Override
                    public int compare(Employee employee, Employee t1) {
                        return t1.getRating() - employee.getRating();
                    }
                })
                .collect(Collectors.toList());

        //return null;
    }

    /**
     * Задача №5.
     * Предположим, что требуется выводить список сотрудников на веб-страницу.
     * Необходимо реализовать постраничный вывод (пагинцию).
     *
     * Пример:
     * Если number = 1, size = 3, то результат список List<Employee>
     * Employee{id=1, name='Name1', rating=11}, Employee{id=2, name='Name2', rating=12}, Employee{id=3, name='Name3', rating=13}
     *
     * Если number = 2, size = 3, то результат список List<Employee>
     * Employee{id=4, name='Name4', rating=14}, Employee{id=5, name='Name5', rating=15}, Employee{id=6, name='Name6', rating=16}
     *
     * @param employees список сотрудников
     * @param number номер страницы (значение от 1)
     * @param size размер выборки
     * @return список сотрудников
     */
    public List<Employee> task5(List<Employee> employees, int number, int size) {
        if (number <= 0)

        {
            throw new IllegalArgumentException(Integer.toString(number));
        }

        if (number > 1)
            return employees.stream()
                    .skip((number - 1) * size)
                    .limit(size)
                    .collect(Collectors.toList());
        else
            return employees.stream()
                    .limit(size)
                    .collect(Collectors.toList());


//        return null;
    }

    /**
     * Задача №6.
     * Получить имена сотрудников в String = начинается строка с "[", затем следуют имена сотрудников через ", " и заканчивается строка "]".
     * Пример результата: [Ivan, Olga, John]
     *
     * @param employees список сотрудников
     * @return имена сотрудников в String
     */
    public String task6(List<Employee> employees) {
        return employees.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(", ", "[", "]"));

//        return null;
    }

    /**
     * Задача №7.
     * Проверить наличае дублирующихся имен сотрудников {@link Employee#getName()} в списке.
     *
     * @param employees список сотрудников
     * @return если дубли существуют, то true, иначе false
     */
    public boolean task7(List<Employee> employees) {
        employees.stream()
                .map(Employee::getName)
                .collect(Collector.of())
        //реализовать коллектор который добавлял бы во множество и если не получается (там есть такой элемент),
        //то добавлял бы его в список. Возвращать признак полученный список пуст или нет

        return false;
    }

    /**
     * Задача №8.
     * Получить средний райтинг {@link Employee#getRating()} по каждой должности сотрудника.
     *
     * @param employees список сотрудников
     * @return словарь должность и райтинг
     */
    public Map<PositionType, Double> task8(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.toMap(
                        Employee::getPositionType,
                        emp -> emp.getRating() / 1.0,
                        (a, b) -> (a + b) / 2.0
                ));

//        return null;
    }

    /**
     * Задача №9.
     * Получить словарь, который содержит две записи:
     * Ключи: true - эффективные и false - неэффективные сотрудники.
     * Значение: количество сотрудников для каждой из двух групп.
     * Сотрудник является эффективыным, если его райтинг больше 50.
     *
     * @param employees список сотрудников
     * @return словарь с количеством эффективных и неэффективных сотрудников
     */
    public Map<Boolean, Long> task9(List<Employee> employees) {
        return null;
    }

    /**
     * Задача №10.
     * Получить словарь, который содержит две записи:
     * Ключи: true - эффективные и false - неэффективные сотрудники.
     * Значение: списком имен {@link Employee#getName()} через ",".
     * Сотрудник является эффективыным, если его райтинг больше 50.
     *
     * @param employees список сотрудников
     * @return словарь с списком имен {@link Employee#getName()} через ", " эффективных и неэффективных сотрудников
     */
    public Map<Boolean, String> task10(List<Employee> employees) {
        return null;
    }

}