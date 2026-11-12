package ua.duikt.holoshchuk.lr2.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    private static int nextOrderNumber = 1;

    private final int orderNumber;
    private final String customerName;
    private final List<OrderItem> items = new ArrayList<>();

    public Order(String customerName) {
        if (customerName == null || customerName.isBlank()) {
            throw new IllegalArgumentException("Ім'я клієнта не може бути порожнім");
        }
        this.orderNumber = nextOrderNumber++;
        this.customerName = customerName.trim();
    }

    public boolean addItem(OrderItem item) {
        if (item == null || item.getUnitPrice() <= 0 || item.getQuantity() <= 0) {
            return false;
        }
        item.attachToOrder(this.orderNumber);
        items.add(item);
        return true;
    }

    public boolean addNewItem(String productName, double unitPrice, int quantity) {
        if (productName == null || productName.isBlank() || unitPrice <= 0.0 || quantity <= 0) {
            return false;
        }
        OrderItem item = new OrderItem(productName, unitPrice, quantity);
        item.attachToOrder(this.orderNumber);
        items.add(item);
        return true;
    }

    public double calculateTotalSum() {
        double sum = 0.0;
        for (OrderItem item : items) {
            sum += item.lineTotal();
        }
        return sum;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String formatOrderSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Замовлення #%d (клієнт: %s)%n", orderNumber, customerName));
        for (OrderItem item : items) {
            sb.append("  + ").append(item).append("
");
        }
        sb.append(String.format("Разом до сплати: %.2f грн", calculateTotalSum()));
        return sb.toString();
    }
}
