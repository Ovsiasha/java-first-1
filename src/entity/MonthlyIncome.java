package entity;

import java.time.LocalDate;

public class MonthlyIncome {
    private LocalDate date;
    private int quantity;
    private int total;

    public MonthlyIncome() {
    }

    public MonthlyIncome(LocalDate date, int quantity, int total) {
        this.date = date;
        this.quantity = quantity;
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public MonthlyIncome setTotal(int total) {
        this.total = total;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public MonthlyIncome setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public MonthlyIncome setDate(LocalDate date) {
        this.date = date;
        return this;
    }

}
