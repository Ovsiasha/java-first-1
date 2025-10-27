package entity;

public class GeneralStatistics {
    private int totalClients;
    private int totalOrders;
    private int totalProducts;
    private int totalSales;
    private double averageBill;

    public GeneralStatistics(int totalClients, int totalOrders, int totalProducts, int totalSales, double averageBill) {
        this.totalClients = totalClients;
        this.totalOrders = totalOrders;
        this.totalProducts = totalProducts;
        this.totalSales = totalSales;
        this.averageBill = averageBill;
    }

    public GeneralStatistics() {
    }

    public int getTotalClients() {
        return totalClients;
    }

    public GeneralStatistics setTotalClients(int totalClients) {
        this.totalClients = totalClients;
        return this;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public GeneralStatistics setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
        return this;
    }

    public int getTotalProducts() {
        return totalProducts;
    }

    public GeneralStatistics setTotalProducts(int totalProducts) {
        this.totalProducts = totalProducts;
        return this;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public GeneralStatistics setTotalSales(int totalSales) {
        this.totalSales = totalSales;
        return this;
    }

    public double getAverageBill() {
        return averageBill;
    }

    public GeneralStatistics setAverageBill(double averageBill) {
        this.averageBill = averageBill;
        return this;
    }
}
