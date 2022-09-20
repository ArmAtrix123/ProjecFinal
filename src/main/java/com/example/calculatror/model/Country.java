package com.example.calculatror.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Вы не ввели название")
    @Size(message = "Слишком маленькое название" , min = 1, max = 50)
    private String name;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Proizvoditel pro;

    @OneToMany(mappedBy = "co", fetch = FetchType.EAGER)
    private Collection<Watch> tenants;

    @OneToMany(mappedBy = "im", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private Collection<Imac> tenants1;

    public Country(String name, Proizvoditel pro, Collection<Watch> tenants, Collection<Imac> tenants1) {
        this.name = name;
        this.pro = pro;
        this.tenants = tenants;
        this.tenants1 = tenants1;
    }

    public Country() {
    }

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

    public Proizvoditel getPro() {
        return pro;
    }

    public void setPro(Proizvoditel pro) {
        this.pro = pro;
    }

    public Collection<Watch> getTenants() {
        return tenants;
    }

    public void setTenants(Collection<Watch> tenants) {
        this.tenants = tenants;
    }

    public Collection<Imac> getTenants1() {
        return tenants1;
    }

    public void setTenants1(Collection<Imac> tenants1) {
        this.tenants1 = tenants1;
    }
}
