package dao;

import entity.Order;
import entity.Client;
import exception.DaoException;
import util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    private static final OrderDao INSTANCE = new OrderDao();

    private static final String FIND_ALL_SQL = """
            SELECT o.id,
                   o.client_id,
                   o.order_date,
                   c.id AS c_id,
                   c.name AS c_name,
                   c.email AS c_email,
                   c.phone AS c_phone
            FROM orders o
            JOIN clients c ON o.client_id = c.id;
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT o.id,
                   o.client_id,
                   o.order_date,
                   c.id AS c_id,
                   c.name AS c_name,
                   c.email AS c_email,
                   c.phone AS c_phone
            FROM orders o
            JOIN clients c ON o.client_id = c.id
            WHERE o.id = ?;
            """;

    private static final String SAVE_SQL = """
            INSERT INTO orders (client_id, order_date)
            VALUES (?, ?)
            RETURNING id;
            """;

    private static final String UPDATE_SQL = """
            UPDATE orders
            SET client_id = ?,
                order_date = ?
            WHERE id = ?;
            """;

    private static final String DELETE_SQL = """
            DELETE FROM orders
            WHERE id = ?;
            """;

    private OrderDao() {}

    public static OrderDao getInstance() {
        return INSTANCE;
    }

    public List<Order> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Order> orders = new ArrayList<>();

            while (resultSet.next()) {
                orders.add(buildOrder(resultSet));
            }

            return orders;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Order findById(int id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return buildOrder(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Order save(Order order) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL)) {

            preparedStatement.setInt(1, order.getClient().getId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(order.getOrderDate()));

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                order.setId(resultSet.getInt("id"));
            }
            return order;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean update(Order order) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

            preparedStatement.setInt(1, order.getClient().getId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(order.getOrderDate()));
            preparedStatement.setInt(3, order.getId());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(int id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {

            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Order buildOrder(ResultSet resultSet) throws SQLException {
        Client client = new Client(
                resultSet.getInt("c_id"),
                resultSet.getString("c_name"),
                resultSet.getString("c_email"),
                resultSet.getString("c_phone")
        );

        return new Order(
                resultSet.getInt("id"),
                client,
                resultSet.getTimestamp("order_date").toLocalDateTime()
        );
    }

}
