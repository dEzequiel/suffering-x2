package edu.poniperro;

import javax.ws.rs.Produces;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.poniperro.dominio.Item;
import edu.poniperro.dominio.Orden;
import edu.poniperro.dominio.Usuaria;

@Path("/")
public class ResourcesOlli {

    @Inject
    ServiceOlli service;

    @Path("/wellcome")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String wellcome() {
        return "Wellcome Ollivanders!";
    }

    @Path("/usuaria/{nombre}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersona(@PathParam("nombre") String nombre) {
        Optional<Usuaria> oUsuaria = Usuaria.findByIdOptional(nombre);
        if(oUsuaria.isPresent()) {
            return Response.status(Response.Status.OK).entity(service.cargaUsuaria(oUsuaria.get().getNombre())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Path("/ordena")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response makeOrder(@Valid Orden orden) {

        Orden pedido = service.comanda(orden.getUser().getNombre(), orden.getItem().getNombre());

        return pedido != null ? Response.status(Response.Status.CREATED).entity(orden).build() : Response.status(Response.Status.NOT_FOUND).build();

    }

    @Path("/pedidos/{usuaria}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Orden> getPedidos(@PathParam("usuaria") String nombre) {
        return service.cargaOrden(nombre);
    }

    @Path("/item/{nombre}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItem(@PathParam("nombre") String nombre) {
        Optional<Item> oItem = Item.findByIdOptional(nombre);

        if(oItem.isPresent()) {
            return Response.status(Response.Status.OK).entity(service.cargaItem(oItem.get().getNombre())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
