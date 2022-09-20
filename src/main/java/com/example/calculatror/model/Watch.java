package com.example.calculatror.model;

import com.example.calculatror.model.onetomany.Adress;
import com.example.calculatror.model.onetomany.Sklad;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Watch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Вы не ввели название")
    @Size(message = "Слишком маленькое название" , min = 1, max = 50)
    private String name;

    @NotNull(message = "Часы без цены? Что-то новое")
    @Min(message = "Слишком мало цифр, больше делай", value = 5)
    Integer price;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Country co;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Color colorwatch;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Sklad skladwatch;

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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Country getCo() {
        return co;
    }

    public void setCo(Country co) {
        this.co = co;
    }

    public Color getColorwatch() {
        return colorwatch;
    }

    public void setColorwatch(Color colorwatch) {
        this.colorwatch = colorwatch;
    }

    public Sklad getSkladwatch() {
        return skladwatch;
    }

    public void setSkladwatch(Sklad skladwatch) {
        this.skladwatch = skladwatch;
    }
}
