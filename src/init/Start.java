package init;

import dao.ProductDao;

import java.util.Scanner;

import static init.ClientMenu.clients;
import static init.OrderMenu.orders;
import static init.ProductMenu.products;
import static init.ReportMenu.reports;

public class Start {

    protected static final Scanner sc = new Scanner(System.in);
    private static final ProductDao clientDao = ProductDao.getInstance();
    ClientMenu clientMenu = new ClientMenu();

    public static void startMenu() {
        System.out.println("=====================");
        System.out.println("1. Клиенты");
        System.out.println("2. Продукты");
        System.out.println("3. Заказы");
        System.out.println("4. Отчеты");
        System.out.println("0. Выход");
        System.out.println("_____________________");
        System.out.print("Выберите пункт: ");

        int input = safeIntInput();

        switch (input) {
            case 1 -> clients();
            case 2 -> products();
            case 3 -> orders();
            case 4 -> reports();
            case 0 -> {
                System.out.println("Выход из программы...");
                System.exit(0);
            }
            default -> {
                cls();
                System.out.println("Некорректный ввод, попробуйте снова!");
                startMenu();
            }
        }
    }


    // === Вспомогательные методы ===
    protected static void pauseAndReturn() {
        System.out.println("\nНажмите Enter, чтобы продолжить...");
        sc.nextLine();
    }

    protected static void cls() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n");
    }

    protected static int safeIntInput() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка ввода. Введите число:");
            }
        }
    }

}
