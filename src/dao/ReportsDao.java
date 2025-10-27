package dao;

import entity.*;
import exception.DaoException;
import util.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportsDao {
    private static final ReportsDao INSTANCE = new ReportsDao();

    private static final String GENERAL_STATISTICS_SQL = """           
            SELECT
               COUNT(DISTINCT c.id) AS client_num,
               COUNT(DISTINCT o.id) AS order_num,
               SUM(oi.quantity) AS products_num,
               SUM(oi.total) AS sales_num,
               (SUM(oi.total) / COUNT(DISTINCT o.id)) AS average_bill
           FROM clients c
           JOIN orders o ON c.id = o.client_id
           JOIN order_items oi ON o.id = oi.order_id;
           """;
    private static final String SALES_FOR_PERIOD_SQL = """
            SELECT orders.order_date, name, total
            FROM orders
            JOIN clients ON orders.client_id = clients.id
            JOIN order_items ON orders.id = order_items.order_id
            WHERE order_date between ? and ?
            """;

    private static final String POPULAR_PRODUCTS_SQL = """
            SELECT p.name, SUM(oi.quantity) AS total
            FROM products p
            JOIN order_items oi ON p.id = oi.product_id
            JOIN orders o ON oi.order_id = o.id
            WHERE o.order_date BETWEEN ? AND ?
            GROUP BY p.name
            ORDER BY total DESC
            LIMIT 3
            """;

    private static final String ACTIVE_CLIENTS_SQL = """
            SELECT c.name, SUM(oi.quantity) AS quantity, SUM(oi.total) AS total
            FROM clients c
            JOIN orders o ON c.id = o.client_id
            JOIN order_items oi ON c.id = oi.order_id
            GROUP BY c.name
            ORDER BY total DESC
            """;

    private static final String MONTHLY_INCOME_SQL = """
            SELECT order_date, sum(id) as orders, sum(total) as total
            FROM orders o
            JOIN order_items oi ON o.id = oi.order_id
            WHERE o.order_date BETWEEN ? AND ?
            GROUP BY order_date
            ORDER BY order_date DESC
            """;

    public GeneralStatistics getGeneralStatistics() {
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GENERAL_STATISTICS_SQL)){

            GeneralStatistics result = null;
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                result = (new GeneralStatistics(
                        resultSet.getInt("client_num"),
                        resultSet.getInt("order_num"),
                        resultSet.getInt("products_num"),
                        resultSet.getInt("sales_num"),
                        resultSet.getDouble("average_bill")
                ));
            }
            return result;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public List<ReportSalesPeriod> getSalesReport(LocalDate start, LocalDate end) {
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SALES_FOR_PERIOD_SQL)){

            preparedStatement.setDate(1, Date.valueOf(start));
            preparedStatement.setDate(2, Date.valueOf(end));

            List<ReportSalesPeriod> result = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(new ReportSalesPeriod(
                        resultSet.getDate("order_date").toLocalDate(),
                        resultSet.getString("name"),
                        resultSet.getInt("total")
                        ));
            }

            return result;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public List<PopularProducts> getPopularProducts(LocalDate start, LocalDate end) {
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(POPULAR_PRODUCTS_SQL)){

            preparedStatement.setDate(1, Date.valueOf(start));
            preparedStatement.setDate(2, Date.valueOf(end));

            List<PopularProducts> result = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(new PopularProducts(
                        resultSet.getString("name"),
                        resultSet.getInt("total")
                ));
            }

            return result;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public List<ActiveClients> getActiveClients() {
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ACTIVE_CLIENTS_SQL)){


            List<ActiveClients> result = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(new ActiveClients(
                        resultSet.getString("name"),
                        resultSet.getInt("quantity"),
                        resultSet.getInt("total")
                ));
            }
            return result;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public List<MonthlyIncome> getMonthlyIncome(LocalDate start, LocalDate end) {
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(MONTHLY_INCOME_SQL)){

            preparedStatement.setDate(1, Date.valueOf(start));
            preparedStatement.setDate(2, Date.valueOf(end));

            List<MonthlyIncome> result = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(new MonthlyIncome(
                        resultSet.getDate("order_date").toLocalDate(),
                        resultSet.getInt("orders"),
                        resultSet.getInt("total")
                ));
            }
            return result;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }



    public static ReportsDao getInstance() {
        return INSTANCE;
    }


}
