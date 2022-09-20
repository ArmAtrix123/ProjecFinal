package com.example.calculatror.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.crypto.Mac;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Вы не ввели название?")
    @Size(message = "Слишком маленькое название" , min = 1, max = 50)
    private String name;

    @OneToMany(mappedBy = "color", fetch = FetchType.EAGER)
    private Collection<MacBook> tenants;

    @OneToMany(mappedBy = "coloriphone", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private Collection<Iphone> tenants1;

    @OneToMany(mappedBy = "colorwatch", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private Collection<Watch> tenants2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<MacBook> getTenants() {
        return tenants;
    }

    public void setTenants(Collection<MacBook> tenants) {
        this.tenants = tenants;
    }

    public Collection<Iphone> getTenants1() {
        return tenants1;
    }

    public void setTenants1(Collection<Iphone> tenants1) {
        this.tenants1 = tenants1;
    }

    public Collection<Watch> getTenants2() {
        return tenants2;
    }

    public void setTenants2(Collection<Watch> tenants2) {
        this.tenants2 = tenants2;
    }
}
