package ru.travin.springapp4.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.travin.springapp4.models.Person;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Class.forName;

@Component
public class
PersonDAO {
    // private static int PEOPLE_COUNT;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    private static final String URL = "jdbc:postgresql://localhost:5432/first_db";// указываем путь к нашей базе данных
//    private static final String USERNAME = "postgres"; // user
//    private static final String PASSWORD = "postgres"; // password
//    private static Connection connection; // заводим переменную connection
//
//
//    static {
//        try {
//            Class.forName("org.postgresql.Driver");
//           // Class.forName("org.postgresql.Driver"); // проверяем что получили драйвер базы данных
//
//        }catch (ClassNotFoundException e) {  // обрабатываем ошибку, если вдруг драйвер недоступен
//           e.printStackTrace();
//        }
//
//        try {
//            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); // выполняем подключение к базе данных по ключам
//        } catch (SQLException throwables) { // обрабатываем ошибку
//            throwables.printStackTrace();
//        }
// }


    //private final List<Person> people; // создаем список людей класса Person и называем его people

    //    {
//        people = new ArrayList<>();
//
//        people.add(new Person(++PEOPLE_COUNT, "Tom", 24,"Tom@mail.ru"));
//        people.add(new Person(++PEOPLE_COUNT, "Jack", 10,"Jack@mail.ru"));
//        people.add(new Person(++PEOPLE_COUNT, "Bob", 19,"Bob@mail.com"));
//        people.add(new Person(++PEOPLE_COUNT, "Mark", 44,"Mark@yandex.ru"));
//
//
//    }
    // получение списка всех людей
    public List<Person> index() { // создаем метод, который возвращаем список людей
//        List<Person> people = new ArrayList<>(); // создаем список людей
//
//        try {
//            Statement statement = connection.createStatement(); // создает табличное представление нашей базы данных
//            String SQL = "SELECT * FROM Person"; // указываем запрос к нашей базе данных
//            ResultSet resultSet = statement.executeQuery(SQL); // ResultSet инкапсулирует каждую нашу строку в таблице и теперь надо пройтись по всем строкам и перевести в java объект
//            while (resultSet.next()) {
//                Person person = new Person();
//
//                person.setId(resultSet.getInt("id"));// получаем id
//                person.setName(resultSet.getString("names")); // получаем name
//                person.setAge(resultSet.getInt("age")); // получаем возраст
//                person.setEmail(resultSet.getString("email")); // получаем email
//
//                people.add(person); // добавляем всех полученных людей в список Person
//            }
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return people;
//
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    // получение конкретного человека по id
    public Person show(int id) {
//        Person person = null;
//        //return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
//        try {
//            PreparedStatement preparedStatement =
//                    connection.prepareStatement("SELECT FROM Person WHERE id=?");
//            preparedStatement.setInt(1, id);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//            resultSet.next();
//
//            person = new Person();
//            person.setId(resultSet.getInt("id"));
//            person.setName(resultSet.getString("names"));
//            person.setAge(resultSet.getInt("age"));
//            person.setEmail(resultSet.getString("email"));
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//
//        return person;

        return jdbcTemplate.query("SELECT FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    // метод добавления нового человека
    public void save(Person person) {
//        person.setId(++PEOPLE_COUNT);
//        people.add(person);

//        try {
//            PreparedStatement preparedStatement =
//                    connection.prepareStatement("INSERT INTO Person VALUES(1,?,?,?)");
//            preparedStatement.setString(1, person.getName());
//            preparedStatement.setInt(2,person.getAge());
//            preparedStatement.setString(3,person.getEmail());
//
//            preparedStatement.executeUpdate(); // после получения новых значений выполняем обновление
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }

        jdbcTemplate.update("INSERT INTO Person(name, age, email) VALUES(?, ?, ?)",  person.getName(), person.getAge(), person.getEmail());
    }

    // метод обновления данных людей
    public void update(int id, Person updatePerson) { // получаем пользователя по id и обновленного пользователя в классе Person
//        Person personToBeUpdate = show(id); // получаем пользователя по id
//        personToBeUpdate.setName(updatePerson.getName()); // кладем пользователя введеного с клавиатуры в updatePerson
//        personToBeUpdate.setEmail(updatePerson.getEmail()); // получаем новый email
//        personToBeUpdate.setAge(updatePerson.getAge()); // получаем новый возраст

//        try {
//            PreparedStatement preparedStatement =
//                    connection.prepareStatement("UPDATE Person SET names=?, age=?, email=? WHERE id=?");
//            preparedStatement.setString(1, updatePerson.getName());
//            preparedStatement.setInt(2,updatePerson.getAge());
//            preparedStatement.setString(3,updatePerson.getEmail());
//            preparedStatement.setInt(4,id);
//
//            preparedStatement.executeUpdate();
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
        jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=? WHERE id=?", updatePerson.getName(), updatePerson.getAge(),
                updatePerson.getEmail(), id);
    }

    // метод удаления
    public void delete(int id) {
        // people.removeIf(person -> person.getId() == id);
//        try {
//            PreparedStatement preparedStatement =
//                    connection.prepareStatement("DELETE FROM Person WHERE id=?");
//            preparedStatement.setInt(1,id);
//
//            preparedStatement.executeUpdate();
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }


    /////////////////////////
    //////// Тестируем производительность
    ///////////////////////

    public void testMultipleUpdate(){
        List<Person> people = create1000People(); // вызываем метод, который будет генеририровать 1000 пользователей в БД

        long before = System.currentTimeMillis(); // метод который начинает отсчет времени

        // выполняем запрос в базу данных, сгенерировать 1000 пользователей
        for (Person person : people) {
            jdbcTemplate.update("INSERT INTO Person VALUES(?, ?, ?, ?)", person.getId(), person.getName(), person.getAge(), person.getEmail());
        }


        long after = System.currentTimeMillis(); // метод который заканчивает подсчет времени
        System.out.println("Time: " + (after - before)); // выводим в консоль разницу во времени

    }

    public void testBatchUpdate(){
        List<Person> people = create1000People();

        long before = System.currentTimeMillis();

        jdbcTemplate.batchUpdate("INSERT INTO Person VALUES(?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                    preparedStatement.setInt(1,people.get(i).getId());
                    preparedStatement.setString(2,people.get(i).getName());
                    preparedStatement.setInt(3,people.get(i).getAge());
                    preparedStatement.setString(4,people.get(i).getEmail());


                    }

                    @Override
                    public int getBatchSize() {
                        return people.size();
                    }
                });
        long after = System.currentTimeMillis();
        System.out.println("Time: " + (after - before));
    }

    // метод создания 1000 пользователей
    private List<Person> create1000People() {
        List<Person> people = new ArrayList<>();

        // проходим циклом for от 0 до 1000 и создаем пользователя
        for ( int i = 0; i < 1000; i++){
            people.add(new Person(" Name" + i, 30, "test" + i + "mail.ru"));// вызываем метод add для создания пользователя, выводим у него ИМЯ ВОЗРАСТ EMAIL
        }
        return people;
    }



}