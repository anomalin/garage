package se.yrgo.garage.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Vehicle {
    @Id
    @GeneratedValue
    private Long id;
    private String registrationNumber;
    private String brand;
    private String model;
    private String productionYear;
    @ManyToOne
    private Customer owner;

    public Vehicle(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(String productionYear) {
        this.productionYear = productionYear;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(id, vehicle.id) && Objects.equals(registrationNumber, vehicle.registrationNumber) && Objects.equals(brand, vehicle.brand) && Objects.equals(model, vehicle.model) && Objects.equals(productionYear, vehicle.productionYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, registrationNumber, brand, model, productionYear);
    }

    @Override
    public String toString() {
        return "Registration number: " + registrationNumber +
                ", brand: " + brand +
                ", model: " + model +
                ", production year: " + productionYear;
    }
}
