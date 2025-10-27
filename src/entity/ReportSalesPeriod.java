package entity;

import java.time.LocalDate;

public class ReportSalesPeriod {
    private LocalDate date;
    private String clientName;
    private int totalAmount;

    public ReportSalesPeriod(LocalDate date, String clientName, int totalAmount) {
        this.date = date;
        this.clientName = clientName;
        this.totalAmount = totalAmount;
    }

    public ReportSalesPeriod() {
    }

    public LocalDate getDate() {
        return date;
    }

    public ReportSalesPeriod setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public String getClientName() {
        return clientName;
    }

    public ReportSalesPeriod setClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public ReportSalesPeriod setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    @Override
    public String toString() {
        return date.toString() + " |" + clientName + "   |" + totalAmount;
    }
}
