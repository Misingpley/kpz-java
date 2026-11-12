package ua.duikt.holoshchuk.lr2.app;

import ua.duikt.holoshchuk.lr2.domain.Order;
import ua.duikt.holoshchuk.lr2.domain.OrderItem;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        System.out.println("=== 1. Створення агрегата Замовлення та додавання позицій двома способами ===");
        Order order = new Order("Голощук Владислав");

        OrderItem keyboard = new OrderItem("Механічна клавіатура Keychron", 3200.00);
        // Спосіб 2: повний конструктор (із заданням кількості)
        OrderItem mouse = new OrderItem("Ігрова миша Logitech", 1500.00, 2);

        order.addItem(keyboard);
        order.addItem(mouse);
        System.out.println(order.formatOrderSummary());

        System.out.println("
=== 2. Контрольована зміна кількості позиції та автоматичне узгодження суми ===");
        boolean qtyChanged = mouse.changeQuantity(3);
        System.out.println("Зміна кількості мишей на 3: " + qtyChanged);
        System.out.println("Оновлена сума замовлення: " + String.format("%.2f грн", order.calculateTotalSum()));

        System.out.println("
=== 3. Спроби порушити стан моделі ззовні ===");
        // тест: встановити від'ємну кількість
        boolean badQty = mouse.changeQuantity(-5);
        System.out.println("Спроба встановити кількість -5 -> результат: " + badQty
                + " (поточна кількість залишилась: " + mouse.getQuantity() + ")");

        // тест: додати позицію з від'ємною ціною або порожньою назвою
        boolean addedNegativePrice = order.addNewItem("Монітор", -100.00, 1);
        boolean addedBlankName = order.addNewItem("   ", 500.00, 1);
        System.out.println("Спроба додати позицію з ціною -100.00 -> прийнято: " + addedNegativePrice);
        System.out.println("Спроба додати позицію з порожньою назвою -> прийнято: " + addedBlankName);

        // тест: напряму модифікувати список позицій, отриманий через getItems()
        List<OrderItem> externalView = order.getItems();
        try {
            externalView.clear();
        } catch (UnsupportedOperationException e) {
            System.out.println("Спроба очистити order.getItems().clear() ззовні заблокована ("
                    + e.getClass().getSimpleName() + ")");
        }

        // тест: викликати службовий метод узгодження attachToOrder з пакета app:
        // keyboard.attachToOrder(999);

        System.out.println("
Підсумковий стан замовлення після всіх спроб втручання:");
        System.out.println(order.formatOrderSummary());
    }
}
