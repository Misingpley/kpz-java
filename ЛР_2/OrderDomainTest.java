package ua.duikt.holoshchuk.lr2;

import org.junit.jupiter.api.Test;
import ua.duikt.holoshchuk.lr2.domain.Order;
import ua.duikt.holoshchuk.lr2.domain.OrderItem;

import static org.junit.jupiter.api.Assertions.*;

class OrderDomainTest {

    @Test
    void shouldKeepTotalSumConsistentAndProtectInternalState() {
        Order order = new Order("Тестовий клієнт");
        OrderItem i1 = new OrderItem("Ноутбук", 30000.0);
        OrderItem i2 = new OrderItem("Кабель USB-C", 400.0, 2);

        assertTrue(order.addItem(i1));
        assertTrue(order.addItem(i2));
        assertEquals(30800.0, order.calculateTotalSum(), 0.001);

        assertFalse(order.addNewItem("Брак", -50.0, 1));
        assertFalse(i2.changeQuantity(0));
        assertEquals(30800.0, order.calculateTotalSum(), 0.001);

        assertThrows(UnsupportedOperationException.class, () -> order.getItems().remove(0));
    }
}
