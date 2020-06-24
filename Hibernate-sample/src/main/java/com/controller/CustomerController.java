package com.controller;

import com.entities.Customer;
import com.service.CustomerService;
import com.web.json.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @POST
    @Path("/customer")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)

    public JsonResponse register(Customer customer) {

        customerService.addOrder(customer);

        return new JsonResponse()
                .with("status", "OK")
                .done();
    }



}
