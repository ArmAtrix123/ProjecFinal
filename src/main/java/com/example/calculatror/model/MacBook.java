package com.example.calculatror.model;

import com.example.calculatror.model.onetomany.Adress;
import com.example.calculatror.model.onetomany.Sklad;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class MacBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @NotEmpty(message = "Как вы собираетесь продать MacBook без названия?")
    @Size(message = "MacBook с таким коротким названием еще не придумали" , min = 6, max = 50)
    String title;

    @NotEmpty(message = "Как вы собираетесь продать MacBook без описания?")
    @Size(message = "Расскажите больше о MacBook" , min = 5, max = 50)
    String text;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Color color;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Sklad skladmac;

    @NotNull(message = "MacBook без цены? Что-то новое")
    @Min(message = "Слишком мало цифр, больше делай", value = 5)
    Integer price;

    @NotNull(message = "Как это нет объема памяти?")
    @Min(message = "Слишком маленький", value = 36)
    Integer size;


    public String getNameColor(){
        return color.getName();
    }

    public Sklad getSkladmac() {
        return skladmac;
    }

    public void setSkladmac(Sklad skladmac) {
        this.skladmac = skladmac;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}