package dao;

import entity.Product;
import exception.DaoException;
import util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private static final ProductDao INSTANCE = new ProductDao();

    private static final String FIND_BY_SQL = """
            SELECT id, name, price, quantity
            FROM products
            WHERE id = ?;
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, name, price, quantity
            FROM products;
            """;

    private static final String SAVE_SQL = """
            INSERT INTO products (name, price, quantity)
            VALUES (?, ?, ?);
            """;

    private static final String DELETE_SQL = """
            DELETE FROM products
            WHERE id = ?;
            """;

    private static final String UPDATE_SQL = """
            UPDATE products
            SET name = ?, price = ?, quantity = ?
            WHERE id = ?;
            """;

    private ProductDao() {
    }

    public Product saveProduct(Product product) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());

            int rows = preparedStatement.executeUpdate();
            if (rows == 0) {
                throw new DaoException("Ошибка при сохранение продукта", new SQLException("Failed to save product"));
            }

            try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                if (keys.next()) {
                    product.setId(keys.getInt(1));
                }
            }

            return product;

        } catch (SQLException e) {
            throw new DaoException("Ошибка при сохранении продукта", e);
        }
    }

    public Product findByIdProduct(int id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_SQL)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return buildProduct(resultSet);
                }
            }

            return null;
        } catch (SQLException e) {
            throw new DaoException("Ошибка при поиске продукта по ID", e);
        }
    }

    public List<Product> findAllProducts() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            List<Product> products = new ArrayList<>();

            while (resultSet.next()) {
                products.add(buildProduct(resultSet));
            }

            return products;

        } catch (SQLException e) {
            throw new DaoException("Ошибка при получении списка продуктов", e);
        }
    }

    public boolean updateProduct(int id, Product product) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setInt(4, id);

            int rows = preparedStatement.executeUpdate();
            return rows > 0; // true, если обновилась хотя бы одна строка

        } catch (SQLException e) {
            throw new DaoException("Ошибка при обновлении продукта", e);
        }
    }

    public boolean deleteByIdProduct(int id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {

            preparedStatement.setInt(1, id);

            int rows = preparedStatement.executeUpdate();
            return rows > 0; // true, если удалена хотя бы одна строка

        } catch (SQLException e) {
            throw new DaoException("Ошибка при удалении продукта по ID", e);
        }
    }

    private Product buildProduct(ResultSet resultSet) throws SQLException {
        return new Product()
                .setId(resultSet.getInt("id"))
                .setName(resultSet.getString("name"))
                .setPrice(resultSet.getInt("price"))
                .setQuantity(resultSet.getInt("quantity"));
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }
}
