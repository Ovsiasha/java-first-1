package entity;

import java.time.LocalDate;

public class SalesReport {
    private LocalDate date;
    private String clientName;
    private int totalAmount;

    public SalesReport(LocalDate date, String clientName, int totalAmount) {
        this.date = date;
        this.clientName = clientName;
        this.totalAmount = totalAmount;
    }

    public SalesReport() {
    }

    public LocalDate getDate() {
        return date;
    }

    public SalesReport setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public String getClientName() {
        return clientName;
    }

    public SalesReport setClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public SalesReport setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    @Override
    public String toString() {
        return date.toString() + " |" + clientName + "   |" + totalAmount;
    }
}
