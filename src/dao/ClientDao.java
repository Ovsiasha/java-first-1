package dao;

import entity.Client;
import exception.DaoException;
import util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDao {
    // Singleton
    private static final ClientDao INSTANCE = new ClientDao();

    // SQL-запросы
    private static final String SAVE_SQL = """
            INSERT INTO clients (name, email, phone)
            VALUES (?, ?, ?);
            """;

    private static final String FIND_BY_SQL = """
            SELECT id, name, email, phone
            FROM clients
            WHERE id = ?;
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, name, email, phone
            FROM clients;
            """;

    private static final String UPDATE_SQL = """
            UPDATE clients
            SET name = ?, email = ?, phone = ?
            WHERE id = ?;
            """;

    private static final String DELETE_SQL = """
            DELETE FROM clients
            WHERE id = ?;
            """;

    private ClientDao() {
    }

    // ✅ Добавление клиента
    public Client saveClient(Client clients) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, clients.getName());
            preparedStatement.setString(2, clients.getEmail());
            preparedStatement.setString(3, clients.getPhone());

            int rows = preparedStatement.executeUpdate();
            if (rows == 0) {
                throw new DaoException("Ошибка при сохранениии клиента", new SQLException("Failed to save client"));
            }

            try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                if (keys.next()) {
                    clients.setId(keys.getInt(1));
                }
            }

            return clients;

        } catch (SQLException e) {
            throw new DaoException("Ошибка при сохранении клиента", e);
        }
    }

    // ✅ Поиск клиента по ID
    public Client findByIdClient(int id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_SQL)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return buildClient(resultSet);
                }
            }

            return null;
        } catch (SQLException e) {
            throw new DaoException("Ошибка при поиске клиента по ID", e);
        }
    }

    // ✅ Получение всех клиентов
    public List<Client> findAllClients() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            List<Client> clients = new ArrayList<>();

            while (resultSet.next()) {
                clients.add(buildClient(resultSet));
            }

            return clients;

        } catch (SQLException e) {
            throw new DaoException("Ошибка при получении списка клиентов", e);
        }
    }

    // ✅ Обновление клиента — возвращает true/false
    public boolean updateClient(int id, Client clients) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

            preparedStatement.setString(1, clients.getName());
            preparedStatement.setString(2, clients.getEmail());
            preparedStatement.setString(3, clients.getPhone());
            preparedStatement.setInt(4, id);

            int rows = preparedStatement.executeUpdate();
            return rows > 0; // true, если обновилась хотя бы одна строка

        } catch (SQLException e) {
            throw new DaoException("Ошибка при обновлении клиента", e);
        }
    }

    // ✅ Удаление клиента — возвращает true/false
    public boolean deleteByIdClient(int id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {

            preparedStatement.setInt(1, id);

            int rows = preparedStatement.executeUpdate();
            return rows > 0; // true, если удалена хотя бы одна строка

        } catch (SQLException e) {
            throw new DaoException("Ошибка при удалении клиента по ID", e);
        }
    }

    // ✅ Преобразование ResultSet → Client
    private Client buildClient(ResultSet resultSet) throws SQLException {
        return new Client()
                .setId(resultSet.getInt("id"))
                .setName(resultSet.getString("name"))
                .setEmail(resultSet.getString("email"))
                .setPhone(resultSet.getString("phone"));
    }

    // ✅ Геттер для единственного экземпляра DAO
    public static ClientDao getInstance() {
        return INSTANCE;
    }
}
