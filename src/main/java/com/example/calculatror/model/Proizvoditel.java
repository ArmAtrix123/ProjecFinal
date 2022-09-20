package com.example.calculatror.model;

import com.example.calculatror.model.onetomany.Sklad;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
public class Proizvoditel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Вы не ввели город?")
    @Size(message = "Слишком маленькое название" , min = 1, max = 50)
    private String name;

    @OneToMany(mappedBy = "pro", fetch = FetchType.EAGER)
    private Collection<Country> tenants;

    public Proizvoditel(String name, Collection<Country> tenants) {
        this.name = name;
        this.tenants = tenants;
    }

    public Proizvoditel() {
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

    public Collection<Country> getTenants() {
        return tenants;
    }

    public void setTenants(Collection<Country> tenants) {
        this.tenants = tenants;
    }
}

