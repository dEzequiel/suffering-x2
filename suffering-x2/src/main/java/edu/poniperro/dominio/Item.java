package edu.poniperro.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "t_items")
public class Item extends PanacheEntityBase {

    @NotNull
    @Id
    @Column(name = "item_nom")
    private String nombre;

    @Column(name = "item_prop")
    private int quality;

    @Column(name = "item_tipo")
    private String tipo;

    public Item(){}
    public Item(String nombre) {
        this.nombre = nombre;
        this.quality = 0;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getQuality() {
        return this.quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }






}
