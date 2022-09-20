package com.example.calculatror.model.onetomany;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
public class Adress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Вы не ввели город?")
    @Size(message = "Слишком маленькое название" , min = 1, max = 50)
    private String city;

    @NotEmpty(message = "Вы не ввели улицу?")
    @Size(message = "Слишком маленькое название" , min = 1, max = 50)
    private String street;

    @NotEmpty(message = "Вы не ввели строение?")
    @Size(message = "Слишком маленькое название" , min = 1, max = 50)
    private String building;

    @OneToMany(mappedBy = "address", fetch = FetchType.EAGER)
    private Collection<Sklad> tenants;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public Collection<Sklad> getTenants() {
        return tenants;
    }

    public void setTenants(Collection<Sklad> tenants) {
        this.tenants = tenants;
    }
}
