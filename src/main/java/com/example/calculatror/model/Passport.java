package com.example.calculatror.model;

import com.example.calculatror.model.Imac;
import com.example.calculatror.model.Iphone;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Вы не ввели серию?")
    @Size(message = "Слишком маленькое название" , min = 1, max = 50)
    private String series;

    @NotEmpty(message = "Вы не ввели номер?")
    @Size(message = "Слишком маленькое название" , min = 1, max = 50)
    private String number;

    @OneToOne(optional = true, mappedBy = "passport")
    private Imac owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Imac getOwner() {
        return owner;
    }

    public void setOwner(Imac owner) {
        this.owner = owner;
    }
}
