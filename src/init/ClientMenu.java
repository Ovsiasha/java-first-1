package init;

import dao.ClientDao;
import entity.Client;
import java.util.List;
import static init.Start.*;

public class ClientMenu {
    private static final ClientDao clientDao =ClientDao.getInstance();

    protected static void clients() {
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
                startMenu();
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

        boolean updatedOk = clientDao.update(id, updated);
        if (updatedOk) {
            System.out.println("Данные клиента обновлены!");
        } else {
            System.out.println("Ошибка: клиент не был обновлён.");
        }

        pauseAndReturn();
        clients();
    }

    private static void deleteClient() {
        System.out.print("Введите ID клиента для удаления: ");
        int id = safeIntInput();

        boolean deleted = clientDao.deleteById(id);
        if (deleted) {
            System.out.println("Клиент успешно удалён!");
        } else {
            System.out.println("Клиент с таким ID не найден.");
        }

        pauseAndReturn();
        clients();
    }
}
