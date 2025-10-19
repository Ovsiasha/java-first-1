package dao;

import entity.Client;
import exception.DaoException;
import util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDao {
    private static final ClientDao INSTANCE = new ClientDao();
    private static final String FIND_BY_SQL = """
            SELECT id, full_name, birth_date, email, student.admission_year
            FROM student
            WHERE id = ?;
            """;
    private static final String FIND_ALL_SQl = """
            SELECT id, full_name, birth_date, email, admission_year
            FROM student;
            """;
    private static final String SAVE_SQL = """
            INSERT INTO student(full_name, birth_date, email, admission_year)
            VALUES (?, ?, ?, ?)
            RETURNING id, full_name, birth_date, email, admission_year;
            """;
    private static final String DELETE_SQL = """
            DELETE FROM student
            WHERE id = ?
            RETURNING id, full_name, birth_date, email, admission_year;
            """;
    private static final String UPDATE_SQL = """
            UPDATE student
            SET full_name = ?, birth_date = ?, email = ?, admission_year = ?
            WHERE id = ?
            RETURNING id, full_name, birth_date, email, admission_year;
            """;


    public Client save(Client client) {
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getEmail());
            preparedStatement.setString(3, client.getPhone());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()) {
                client.setId(resultSet.getInt(1));
            }
            return client;
        }catch (SQLException e){
            throw new DaoException(e);
        }
    }

    public  Client findById(int id) {
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_SQL)){

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return buildStudent(resultSet);
            }
            return null;
        }catch(Exception e){
            throw new DaoException(e);
        }
    }

    public List<Client> findAll() {
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQl)){

            List<Client> students = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                students.add(buildStudent(resultSet));
            }
            return students;
        }catch (SQLException e){
            throw new DaoException(e);
        }
    }

    public Client updateStudent(int id, Client student) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(3, student.getEmail());
            preparedStatement.setString(3, student.getPhone());
            preparedStatement.setInt(5, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return buildStudent(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Client deleteById(int id) {
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)){

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return buildStudent(resultSet);
            }
            return null;
        }catch(SQLException e){
            throw new DaoException(e);
        }
    }

    private Client buildStudent(ResultSet resultSet) throws SQLException {
        return new Client(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("phone"));
    }

    public static ClientDao getInstance() {
        return INSTANCE;
    }
}
