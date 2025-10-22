package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order {
    private int id;
    private Client client;
    private LocalDateTime orderDate;

    public Order(int id, Client client, LocalDateTime orderDate) {
        this.id = id;
        this.client = client;
        this.orderDate = orderDate;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public Order setId(int id) {
        this.id = id;
        return this;
    }

    public Client getClient() {
        return client;
    }

    public Order setClient(Client client) {
        this.client = client;
        return this;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public Order setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // добавляем быструю проверку
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                Objects.equals(client, order.client) &&
                Objects.equals(orderDate, order.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, orderDate);
    }
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", client=" + client +
                ", orderDate=" + orderDate +
                '}';
    }
}
