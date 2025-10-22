package entity;

import java.util.Objects;

public class Client {
    private int id;
    private String name;
    private String email;
    private String phone;

    public Client(int id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Client() {
    }

    public int getId() {
        return id;
    }

    public Client setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Client setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Client setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Client setPhone(String phone) {
        this.phone = phone;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Client clients = (Client) o;
        return id == clients.id && Objects.equals(name, clients.name) && Objects.equals(email, clients.email) && Objects.equals(phone, clients.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, phone);
    }

    @Override
    public String toString() {
        return id + "\t" + name + "\t" + email + "\t" + phone;
    }
}
