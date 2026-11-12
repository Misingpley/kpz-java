package ua.duikt.holoshchuk.lr2.domain;

public class OrderItem {
    private static int nextId = 1;

    private final int id;
    private final String productName;
    private final double unitPrice;
    private int quantity;
    private int assignedOrderId;

    public OrderItem(String productName, double unitPrice) {
        this(productName, unitPrice, 1);
    }

    public OrderItem(String productName, double unitPrice, int quantity) {
        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("Назва товару не може бути порожньою");
        }
        if (unitPrice <= 0.0) {
            throw new IllegalArgumentException("Ціна товару має бути більшою за 0: " + unitPrice);
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Кількість одиниць має бути більшою за 0: " + quantity);
        }
        this.id = nextId++;
        this.productName = productName.trim();
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    void attachToOrder(int orderId) {
        this.assignedOrderId = orderId;
    }

    // Контрольована зміна кількості
    public boolean changeQuantity(int newQuantity) {
        if (newQuantity <= 0) {
            return false;
        }
        this.quantity = newQuantity;
        return true;
    }

    public int getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getAssignedOrderId() {
        return assignedOrderId;
    }

    public double lineTotal() {
        return unitPrice * quantity;
    }

    @Override
    public String toString() {
        return String.format("Позиція #%d: %s | %.2f грн x %d = %.2f грн",
                id, productName, unitPrice, quantity, lineTotal());
    }
}
