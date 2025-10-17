package init;

import java.util.Scanner;

public class Start {

    public static void init(){
        System.out.println("=====================");
        System.out.println("1. Клиенты");
        System.out.println("2. Продукты");
        System.out.println("3. Заказы");
        System.out.println("4. Отчеты");
        System.out.println("0. Выход");
        System.out.println("_____________________");
        System.out.println("Выберите пункт: ");

        Scanner sc = new Scanner(System.in);
        int input = 0;
        try{
            input = sc.nextInt();
            checkInput(input);
        }   catch(Exception e){
         cls();
         System.out.println("Вводите только числа!");
         init();
        }

        switch (input){
            case 1 -> clients();
            case 2 -> products();
            case 3 -> orders();
            case 4 -> reports();
            case 0 -> System.exit(0);
        }
    }

    private static void reports() {

    }

    private static void orders() {

    }

    private static void products() {
        System.out.println("=== Продукты ===");
        System.out.println("1. Добавить продукт");
        System.out.println("2. Показать все продукты");
        System.out.println("3. Найти продукт по ID");
        System.out.println("4. Изменить данные продукта");
        System.out.println("5. Удалить продукт");
        System.out.println("0. Назад");
        System.out.println("_____________________");
        System.out.println("Выберите пункт: ");
    }

    private static void clients() {

    }


    public static void checkInput(int input){
        if(input < 0 || input > 4){
            cls();
            System.out.println("Вводите только существующие номера!");
            init();
        }
    }

    public static void cls(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
    }

}
