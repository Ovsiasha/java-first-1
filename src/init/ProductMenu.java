package init;

import dao.ProductDao;
import entity.Product;
import java.util.List;
import static init.Start.*;

public class ProductMenu {
    private static final ProductDao productDao = ProductDao.getInstance();

    protected static void products() {

        cls();
        System.out.println("=== Продукты ===");
        System.out.println("1. Добавить продукт");
        System.out.println("2. Показать все продукты");
        System.out.println("3. Найти продукт по ID");
        System.out.println("4. Изменить продукт");
        System.out.println("5. Удалить продукт");
        System.out.println("0. Назад");
        System.out.println("_____________________");
        System.out.print("Выберите пункт: ");

        int input = safeIntInput();

        switch (input) {
            case 1 -> addProduct();
            case 2 -> showAllProducts();
            case 3 -> findProductById();
            case 4 -> updateProduct();
            case 5 -> deleteProduct();
            case 0 -> {
                cls();
                startMenu();
            }
            default -> {
                System.out.println("Некорректный ввод!");
                products();
            }
        }
    }

    private static void addProduct() {
        System.out.println("Введите название продукта:");
        String name = sc.nextLine();

        System.out.println("Введите цену продукта:");
        int price = safeIntInput();

        System.out.println("Введите количество на складе:");
        int quantity = safeIntInput();

        Product product = new Product()
                .setName(name)
                .setPrice(price)
                .setQuantity(quantity);

        productDao.saveProduct(product);
        System.out.println("Продукт успешно добавлен!");
        pauseAndReturn();
        products();
    }

    private static void showAllProducts() {
        List<Product> products = productDao.findAllProducts();
        if (products.isEmpty()) {
            System.out.println("Нет продуктов в базе.");
        } else {
            System.out.println("ID\tНазвание\tЦена\tКоличество");
            System.out.println("--------------------------------------");
            for (Product p : products) {
                System.out.println(p.getId() + "\t" + p.getName() + "\t" + p.getPrice() + "\t" + p.getQuantity());
            }
        }
        pauseAndReturn();
        products();
    }

    private static void findProductById() {
        System.out.print("Введите ID продукта: ");
        int id = safeIntInput();

        Product product = productDao.findByIdProduct(id);
        if (product != null) {
            System.out.println("Найден продукт:");
            System.out.println("ID: " + product.getId());
            System.out.println("Название: " + product.getName());
            System.out.println("Цена: " + product.getPrice());
            System.out.println("Количество: " + product.getQuantity());
        } else {
            System.out.println("Продукт с таким ID не найден.");
        }
        pauseAndReturn();
        products();
    }

    private static void updateProduct() {
        System.out.print("Введите ID продукта для изменения: ");
        int id = safeIntInput();

        Product existing = productDao.findByIdProduct(id);
        if (existing == null) {
            System.out.println("Продукт с таким ID не найден.");
            pauseAndReturn();
            products();
            return;
        }

        System.out.println("Введите новое название (старое: " + existing.getName() + "): ");
        String name = sc.nextLine();

        System.out.println("Введите новую цену (старая: " + existing.getPrice() + "): ");
        String priceInput = sc.nextLine();

        System.out.println("Введите новое количество (старое: " + existing.getQuantity() + "): ");
        String quantityInput = sc.nextLine();

        Product updated = new Product()
                .setName(name.isEmpty() ? existing.getName() : name)
                .setPrice(priceInput.isEmpty() ? existing.getPrice() : Integer.parseInt(priceInput))
                .setQuantity(quantityInput.isEmpty() ? existing.getQuantity() : Integer.parseInt(quantityInput));

        boolean updatedOk = productDao.updateProduct(id, updated);
        if (updatedOk) {
            System.out.println("Продукт успешно обновлён!");
        } else {
            System.out.println("Ошибка при обновлении продукта.");
        }

        pauseAndReturn();
        products();
    }

    private static void deleteProduct() {
        System.out.print("Введите ID продукта для удаления: ");
        int id = safeIntInput();

        boolean deleted = productDao.deleteByIdProduct(id);
        if (deleted) {
            System.out.println("Продукт успешно удалён!");
        } else {
            System.out.println("Продукт с таким ID не найден.");
        }

        pauseAndReturn();
        products();
    }
}
