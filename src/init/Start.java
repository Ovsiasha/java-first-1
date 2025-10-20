package init;

import dao.ClientDao;
import entity.Client;

import java.util.List;
import java.util.Scanner;

public class Start {

    private static final Scanner sc = new Scanner(System.in);
    private static final ClientDao clientDao = ClientDao.getInstance();

    public static void init() {
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
                init();
            }
        }
    }

    // === Меню для клиентов ===
    private static void clients() {
        cls();
        System.out.println("=== Клиенты ===");
        System.out.println("1. Добавить клиента");
        System.out.println("2. Показать всех клиентов");
        System.out.println("3. Найти клиента по ID");
        System.out.println("4. Изменить данные клиента");
        System.out.println("5. Удалить клиента");
        System.out.println("0. Назад");
        System.out.println("_____________________");
        System.out.print("Выберите пункт: ");

        int input = safeIntInput();

        switch (input) {
            case 1 -> addClient();
            case 2 -> showAllClients();
            case 3 -> findClientById();
            case 4 -> updateClient();
            case 5 -> deleteClient();
            case 0 -> {
                cls();
                init();
            }
            default -> {
                System.out.println("Некорректный ввод!");
                clients();
            }
        }
    }

    private static void addClient() {
        System.out.println("Введите имя клиента:");
        String name = sc.nextLine();

        System.out.println("Введите email:");
        String email = sc.nextLine();

        System.out.println("Введите телефон:");
        String phone = sc.nextLine();

        Client newClient = new Client()
                .setName(name)
                .setEmail(email)
                .setPhone(phone);

        clientDao.save(newClient);
        System.out.println("Клиент успешно добавлен!");
        pauseAndReturn();
        clients();
    }

    private static void showAllClients() {
        List<Client> clients = clientDao.findAll();
        if (clients.isEmpty()) {
            System.out.println("Нет клиентов в базе.");
        } else {
            System.out.println("ID\tИмя\tEmail\tТелефон");
            System.out.println("----------------------------");
            for (Client c : clients) {
                System.out.println(c);
            }
        }
        pauseAndReturn();
        clients();
    }

    private static void findClientById() {
        System.out.print("Введите ID клиента: ");
        int id = safeIntInput();

        Client client = clientDao.findById(id);
        if (client != null) {
            System.out.println("Найден клиент:");
            System.out.println(client);
        } else {
            System.out.println("Клиент с таким ID не найден.");
        }
        pauseAndReturn();
        clients();
    }

    private static void updateClient() {
        System.out.print("Введите ID клиента для изменения: ");
        int id = safeIntInput();

        Client existing = clientDao.findById(id);
        if (existing == null) {
            System.out.println("Клиент с таким ID не найден.");
            pauseAndReturn();
            clients();
            return;
        }

        System.out.println("Введите новое имя (старое: " + existing.getName() + "): ");
        String name = sc.nextLine();

        System.out.println("Введите новый email (старый: " + existing.getEmail() + "): ");
        String email = sc.nextLine();

        System.out.println("Введите новый телефон (старый: " + existing.getPhone() + "): ");
        String phone = sc.nextLine();

        Client updated = new Client()
                .setName(name.isEmpty() ? existing.getName() : name)
                .setEmail(email.isEmpty() ? existing.getEmail() : email)
                .setPhone(phone.isEmpty() ? existing.getPhone() : phone);

        clientDao.updateClient(id, updated);
        System.out.println("Данные клиента обновлены!");
        pauseAndReturn();
        clients();
    }

    private static void deleteClient() {
        System.out.print("Введите ID клиента для удаления: ");
        int id = safeIntInput();

        Client deleted = clientDao.deleteById(id);
        if (deleted != null) {
            System.out.println("Клиент удалён: " + deleted);
        } else {
            System.out.println("Клиент с таким ID не найден.");
        }
        pauseAndReturn();
        clients();
    }

    // === Остальные разделы (заглушки) ===
    private static void reports() {
        System.out.println("Раздел 'Отчеты' пока в разработке...");
        pauseAndReturn();
        init();
    }

    private static void orders() {
        System.out.println("Раздел 'Заказы' пока в разработке...");
        pauseAndReturn();
        init();
    }

    private static void products() {
        System.out.println("Раздел 'Продукты' пока в разработке...");
        pauseAndReturn();
        init();
    }

    // === Вспомогательные методы ===
    private static void pauseAndReturn() {
        System.out.println("\nНажмите Enter, чтобы продолжить...");
        sc.nextLine();
    }

    private static void cls() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n");
    }

    private static int safeIntInput() {
        while (true) {
            try {
                int num = Integer.parseInt(sc.nextLine());
                return num;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка ввода. Введите число:");
            }
        }
    }
}
