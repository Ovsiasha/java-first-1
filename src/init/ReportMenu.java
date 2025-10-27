package init;

import dao.ReportsDao;
import entity.ActiveClients;
import entity.MonthlyIncome;
import entity.PopularProducts;
import entity.ReportSalesPeriod;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static init.ClientMenu.clients;
import static init.Start.*;

public class ReportMenu {
    private static final ReportsDao reportsDao = ReportsDao.getInstance();

    protected static void reports() {
        cls();
        System.out.println("=== Отчеты ===");
        System.out.println("1. Продажи за период");
        System.out.println("2. Самые популярные товары");
        System.out.println("3. Самые активные клиенты");
        System.out.println("4. Доход за месяц");
        System.out.println("5. Детализация по клиенту");
        System.out.println("6. Остатки на складе");
        System.out.println("0. Назад");
        System.out.println("_____________________");
        System.out.print("Выберите пункт: ");


        int input = safeIntInput();

        switch (input) {
            case 1 -> reportSalesForPeriod();
            case 2 -> popularProductsReport();
            case 3 -> activeClientsReport();
            case 4 -> monthlyIncomeReport();
            case 5 -> customerDetailsReport();
            case 6 -> remainingStockReport();
            case 0 -> {
                cls();
                startMenu();
            }
            default -> {
                System.out.println("Некорректный ввод!");
                clients();
            }
        }
    }

    private static void reportSalesForPeriod() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        System.out.println("Введите начальную дату(дд-мм-гг)");
        String startDate = sc.nextLine();

        System.out.println("Введите конечную дату(дд-мм-гг)");
        String endDate = sc.nextLine();

        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);


        System.out.println("=== Продажи с " + startDate + " по " + endDate + " ===");
        System.out.println("_______________________________________________");
        System.out.printf("%-12s | %-18s | %10s%n", "Дата", "Клиент", "Сумма (₽)");

        int sum = 0;
        List<ReportSalesPeriod> result = reportsDao.getSalesReport(start, end);

        for (ReportSalesPeriod reportSalesPeriod : result) {
            System.out.printf("%-12s | %-18s | %10d%n",
                    reportSalesPeriod.getDate().format(formatter),
                    reportSalesPeriod.getClientName(),
                    reportSalesPeriod.getTotalAmount());
            sum += reportSalesPeriod.getTotalAmount();
        }
        System.out.printf("ИТОГО: %d ₽%n", sum);

        pauseAndReturn();
        reports();

    }

    private static void popularProductsReport() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        System.out.println("Введите начальную дату(дд-мм-гг)");
        String startDate = sc.nextLine();

        System.out.println("Введите конечную дату(дд-мм-гг)");
        String endDate = sc.nextLine();

        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        System.out.println("=== Топ товары в период с " + startDate + " по " + endDate + " ===");
        System.out.printf("%-25s | %s%n", "Товар", "Кол-во продаж");
        System.out.println("----------------------------------------------------");

        int total = 0;
        List<PopularProducts> result = reportsDao.getPopularProducts(start, end);

        for(PopularProducts p : result) {
            System.out.printf("%-25s | %3d%n", p.getProductName(), p.getQuantity());
            total += p.getQuantity();
        }
        System.out.println("----------------------------------------------------");
        System.out.printf("ИТОГО продано: %d единиц%n", total);

        pauseAndReturn();
        reports();

    }

    private static void activeClientsReport() {
        System.out.println("=== Топ клиентов по активности ===");
        System.out.printf("%-25s | %-18s | %3s%n", "Клиент", "Сумма", "Сумма (₽)");
        System.out.println("----------------------------------------------------");

        List<ActiveClients> result = reportsDao.getActiveClients();

        for(ActiveClients a : result) {
            System.out.printf("%-25s | %-18s | %3d%n",
                    a.getClientName(), a.getQuantity(), a.getTotal());
        }
        System.out.println("----------------------------------------------------");
        pauseAndReturn();
        reports();
    }

    private static void monthlyIncomeReport() {
        System.out.println("Введите месяц (1-12): ");
        int month = safeIntInput();

        System.out.println("Введите год: ");
        int year = safeIntInput();

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        int total = 0;

        System.out.println("=== Доход за " + month + " " + year + " ===");
        System.out.println("----------------------------------------------------");

        List<MonthlyIncome> result = reportsDao.getMonthlyIncome(start, end);
        for(MonthlyIncome m : result) {
            System.out.printf("%-25s | %-18s | %3d%n",
                    m.getDate(), m.getQuantity(), m.getTotal());
            total += m.getTotal();
        }
        System.out.println("----------------------------------------------------");
        System.out.printf("ИТОГО: %d ₽%n", total);
        pauseAndReturn();
        reports();

    }

    private static void customerDetailsReport() {

    }

    private static void remainingStockReport() {
    }

}
