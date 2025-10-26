package init;

import dao.ClientDao;
import dao.OrderDao;
import entity.Client;
import entity.Order;

import java.time.LocalDateTime;
import java.util.List;

import static init.Start.*;

public class OrderMenu {
    private static final OrderDao orderDao = OrderDao.getInstance();
    private static final ClientDao clientDao = ClientDao.getInstance();

    protected static void orders() {
        cls();
        System.out.println("=== Заказы ===");
        System.out.println("1. Добавить заказ");
        System.out.println("2. Показать все заказы");
        System.out.println("3. Найти заказ по ID");
        System.out.println("4. Изменить заказ");
        System.out.println("5. Удалить заказ");
        System.out.println("0. Назад");
        System.out.println("_____________________");
        System.out.print("Выберите пункт: ");

        int input = safeIntInput();

        switch (input) {
            case 1 -> addOrder();
            case 2 -> showAllOrders();
            case 3 -> findOrderById();
            case 4 -> updateOrder();
            case 5 -> deleteOrder();
            case 0 -> {
                cls();
                startMenu();
            }
            default -> {
                System.out.println("Некорректный ввод!");
                orders();
            }
        }
    }

    // ✅ Добавить заказ
    private static void addOrder() {
        System.out.println("Введите ID клиента для создания заказа:");
        int clientId = safeIntInput();

        Client client = clientDao.findByIdClient(clientId);
        if (client == null) {
            System.out.println("Клиент с таким ID не найден!");
            pauseAndReturn();
            orders();
            return;
        }

        Order newOrder = new Order()
                .setClient(client)
                .setOrderDate(LocalDateTime.now());

        orderDao.save(newOrder);
        System.out.println("Заказ успешно создан!");
        pauseAndReturn();
        orders();
    }

    // ✅ Показать все заказы
    private static void showAllOrders() {
        List<Order> orders = orderDao.findAll();
        if (orders.isEmpty()) {
            System.out.println("Нет заказов в базе.");
        } else {
            System.out.println("=== Список заказов ===");
            for (Order o : orders) {
                System.out.println("ID: " + o.getId() +
                        " | Клиент: " + o.getClient().getName() +
                        " | Дата: " + o.getOrderDate());
            }
        }
        pauseAndReturn();
        orders();
    }

    // ✅ Найти заказ по ID
    private static void findOrderById() {
        System.out.print("Введите ID заказа: ");
        int id = safeIntInput();

        Order order = orderDao.findById(id);
        if (order != null) {
            System.out.println("Найден заказ:");
            System.out.println("ID: " + order.getId());
            System.out.println("Клиент: " + order.getClient().getName());
            System.out.println("Дата заказа: " + order.getOrderDate());
        } else {
            System.out.println("Заказ с таким ID не найден.");
        }

        pauseAndReturn();
        orders();
    }

    // ✅ Изменить заказ (сменить клиента или дату)
    private static void updateOrder() {
        System.out.print("Введите ID заказа для изменения: ");
        int id = safeIntInput();

        Order existing = orderDao.findById(id);
        if (existing == null) {
            System.out.println("Заказ с таким ID не найден.");
            pauseAndReturn();
            orders();
            return;
        }

        System.out.println("Введите новый ID клиента (старый: " + existing.getClient().getId() + "): ");
        String inputClient = sc.nextLine();

        Client newClient = existing.getClient();
        if (!inputClient.isEmpty()) {
            int newClientId = Integer.parseInt(inputClient);
            Client found = clientDao.findByIdClient(newClientId);
            if (found != null) {
                newClient = found;
            } else {
                System.out.println("Клиент с таким ID не найден. Оставлен старый клиент.");
            }
        }

        System.out.println("Изменить дату на текущее время? (y/n): ");
        String changeDate = sc.nextLine();

        LocalDateTime newDate = existing.getOrderDate();
        if (changeDate.equalsIgnoreCase("y")) {
            newDate = LocalDateTime.now();
        }

        existing.setClient(newClient);
        existing.setOrderDate(newDate);

        boolean updated = orderDao.update(existing);
        if (updated) {
            System.out.println("Заказ успешно обновлён!");
        } else {
            System.out.println("Ошибка при обновлении заказа.");
        }

        pauseAndReturn();
        orders();
    }

    // ✅ Удалить заказ
    private static void deleteOrder() {
        System.out.print("Введите ID заказа для удаления: ");
        int id = safeIntInput();

        boolean deleted = orderDao.delete(id);
        if (deleted) {
            System.out.println("Заказ успешно удалён!");
        } else {
            System.out.println("Заказ с таким ID не найден.");
        }

        pauseAndReturn();
        orders();
    }
}
