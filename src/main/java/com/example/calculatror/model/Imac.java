package com.example.calculatror.model;

import com.example.calculatror.model.onetomany.Sklad;

import javax.persistence.*;

@Entity
public class Imac {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    private Passport passport;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Country im;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Sklad skladimac;

    public String getNameIm(){
        return im.getName();
    }
    public String getNamePassport(){
        return passport.getSeries();
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

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public Country getIm() {
        return im;
    }

    public void setIm(Country im) {
        this.im = im;
    }

    public Sklad getSkladimac() {
        return skladimac;
    }

    public void setSkladimac(Sklad skladimac) {
        this.skladimac = skladimac;
    }
}
