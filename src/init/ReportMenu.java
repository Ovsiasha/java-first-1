package init;

import dao.ReportDao;
import entity.SalesReport;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static init.ClientMenu.clients;
import static init.Start.*;

public class ReportMenu {
    private static final ReportDao reportDao = new ReportDao();

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
        System.out.println("Дата      |Клиент     |Сумма (₽)");

        int sum = 0;
        List<SalesReport> result = reportDao.getSalesReport(start, end);

        for (SalesReport salesReport : result) {
            System.out.println( salesReport);
            sum += salesReport.getTotalAmount();
        }
        System.out.println("ИТОГО: "+ sum + "₽");
        pauseAndReturn();
        reports();

    }

    private static void popularProductsReport() {

    }

    private static void activeClientsReport() {

    }

    private static void monthlyIncomeReport() {

    }

    private static void customerDetailsReport() {

    }

    private static void remainingStockReport() {
    }

}
