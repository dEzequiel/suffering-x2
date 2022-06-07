package edu.poniperro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import edu.poniperro.dominio.Item;
import edu.poniperro.dominio.Orden;
import edu.poniperro.dominio.Usuaria;

@ApplicationScoped
public class ServiceOlli {


    public Usuaria cargaUsuaria(String usuariaNombre) {
        Optional<Usuaria> oUsuaria = Usuaria.findByIdOptional(usuariaNombre);
        return oUsuaria.isPresent() ? oUsuaria.get() : new Usuaria("");
    }

    public Item cargaItem(String itemNombre) {
        Optional<Item> oItem = Item.findByIdOptional(itemNombre);
        return oItem.isPresent() ? oItem.get() : new Item("");
    }

    public List<Orden> cargaOrden(String usuariaNombre) {
        return Orden.list("ord_user", usuariaNombre);
    }

    private boolean isEntitiesExistence(String usuariaNombre, String itemNombre) {

        Optional<Usuaria> oUsuaria = Usuaria.findByIdOptional(usuariaNombre);
        Optional<Item> oItem = Item.findByIdOptional(itemNombre);

        return oUsuaria.isPresent() && oItem.isPresent() ? true : false;

    }

    private boolean isDestrezaGreaterThanQuality(String usuariaNombre, String itemNombre) {

        Optional<Usuaria> oUsuaria = Usuaria.findByIdOptional(usuariaNombre);
        Optional<Item> oItem = Item.findByIdOptional(itemNombre);

        return oUsuaria.get().getDestreza() >= oItem.get().getQuality() ? true : false;

    }

    public Orden comanda(String usuariaNombre, String itemNombre) {

        Orden orden = null;

        Optional<Usuaria> oUsuaria = Usuaria.findByIdOptional(usuariaNombre);
        Optional<Item> oItem = Item.findByIdOptional(itemNombre);

        if(isEntitiesExistence(usuariaNombre, itemNombre) && isDestrezaGreaterThanQuality(usuariaNombre, itemNombre)) {
            orden = new Orden(oUsuaria.get(), oItem.get());
            orden.persist();
        }

        return orden;
    }

    public List<Orden> comandaMultiple(String usuariaNombre, List<String> items) {
        Optional<Usuaria> oUsuaria = Usuaria.findByIdOptional(usuariaNombre);

        if(oUsuaria.isEmpty()) {
            return Collections.emptyList();
        }

        List<Orden> ordenes = new ArrayList<Orden>();
        Orden orden = null;

        for (String item : items) {
            orden = comanda(oUsuaria.get().getNombre(), item);
            if(orden != null) {
                ordenes.add(orden);
            }
        }



        return ordenes;
    }
}
