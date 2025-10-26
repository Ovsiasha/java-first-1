package dao;

import entity.SalesReport;
import exception.DaoException;
import util.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReportDao {
    private static final ReportDao INSTANCE = new ReportDao();

    private static final String SALES_FOR_PERIOD_SQL = """
            SELECT orders.order_date, name, total
            FROM orders
            JOIN clients ON orders.client_id = clients.id
            JOIN order_items ON orders.id = order_items.order_id
            WHERE order_date between ? and ?
            """;

    public List<SalesReport> getSalesReport(LocalDate start, LocalDate end) {
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SALES_FOR_PERIOD_SQL)){


            preparedStatement.setDate(1, Date.valueOf(start));
            preparedStatement.setDate(2, Date.valueOf(end));

            List<SalesReport> result = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(buildSalesReport(resultSet));
            }

            return result;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    private SalesReport buildSalesReport(ResultSet resultSet) throws SQLException {
        return new SalesReport()
                .setClientName(resultSet.getString("name"))
                .setDate(resultSet.getDate("order_date").toLocalDate())
                .setTotalAmount(resultSet.getInt("total"));
    }

    public ReportDao getInstance() {
        return INSTANCE;
    }
}
