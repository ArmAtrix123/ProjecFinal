package com.example.calculatror.model.onetomany;

import com.example.calculatror.model.Imac;
import com.example.calculatror.model.Iphone;
import com.example.calculatror.model.MacBook;
import com.example.calculatror.model.Watch;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
public class Sklad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Вы не ввели название")
    @Size(message = "Слишком маленькое название" , min = 1, max = 50)
    private String name;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Adress address;

    @OneToMany(mappedBy = "skladiphone", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private Collection<Iphone> tenants;

    @OneToMany(mappedBy = "skladmac", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private Collection<MacBook> tenants1;

    @OneToMany(mappedBy = "skladimac", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private Collection<Imac> tenants2;

    @OneToMany(mappedBy = "skladwatch", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private Collection<Watch> tenants3;

    public String getNameAdress(){
        return address.getStreet();
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

    public Adress getAddress() {
        return address;
    }

    public void setAddress(Adress address) {
        this.address = address;
    }

    public Collection<Imac> getTenants2() {
        return tenants2;
    }

    public void setTenants2(Collection<Imac> tenants2) {
        this.tenants2 = tenants2;
    }

    public Sklad(String name, Adress address) {
        this.name = name;
        this.address = address;
    }

    public Sklad() {
    }

    public Collection<MacBook> getTenants1() {
        return tenants1;
    }

    public void setTenants1(Collection<MacBook> tenants1) {
        this.tenants1 = tenants1;
    }

    public Collection<Iphone> getTenants() {
        return tenants;
    }

    public void setTenants(Collection<Iphone> tenants) {
        this.tenants = tenants;
    }

    public Collection<Watch> getTenants3() {
        return tenants3;
    }

    public void setTenants3(Collection<Watch> tenants3) {
        this.tenants3 = tenants3;
    }
}
