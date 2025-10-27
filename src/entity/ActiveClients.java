package entity;

public class ActiveClients {
    private String clientName;
    private int quantity;
    private int total;

    public ActiveClients() {
    }

    public ActiveClients(String clientName, int quantity, int total) {
        this.clientName = clientName;
        this.quantity = quantity;
        this.total = total;
    }

    public String getClientName() {
        return clientName;
    }

    public ActiveClients setClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public ActiveClients setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public int getTotal() {
        return total;
    }

    public ActiveClients setTotal(int total) {
        this.total = total;
        return this;
    }
}
