package com.example.calculatror.model;

import com.example.calculatror.model.onetomany.Sklad;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Iphone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @NotEmpty(message = "Как вы собираетесь продать Айфон без названия?")
    @Size(message = "Айфона с таким коротким названием еще не придумали" , min = 5, max = 50)
    String title;

    @NotEmpty(message = "Как вы собираетесь продать Айфон без описания?")
    @Size(message = "Расскажите больше о Айфоне" , min = 5, max = 50)
    String text;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Color coloriphone;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Sklad skladiphone;

    @NotNull(message = "Айфон без цены? Что-то новое")
    @Min(message = "Слишком мало цифр, больше делай", value = 5)
    Integer price;

    @NotNull(message = "А дисплей где?")
    @Max(message = "Слишком много цифр, такие большие еще не придумали", value = 20)
    Integer display;

    @NotNull(message = "А как же люди поймут чем они отличаются?")
    @Min(message = "Да такие страые уже давно не производят", value = 1)
    Integer number;

    public String getNameColor(){
        return coloriphone.getName();
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

    public Color getColoriphone() {
        return coloriphone;
    }

    public void setColoriphone(Color coloriphone) {
        this.coloriphone = coloriphone;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Sklad getSkladiphone() {
        return skladiphone;
    }

    public void setSkladiphone(Sklad skladiphone) {
        this.skladiphone = skladiphone;
    }

}
