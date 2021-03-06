package com.revolut.web.controller;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.revolut.core.dto.AccountCreateDto;
import com.revolut.core.dto.AccountDto;
import com.revolut.core.service.AccountService;
import com.revolut.core.service.impl.DefaultAccountService;

import static javax.ws.rs.core.Response.Status.OK;

@Path("/accounts")
public class AccountController {

    private final Gson json = new Gson();

    private AccountService accountService = DefaultAccountService.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        List<AccountDto> accounts = accountService.getAll();
        return Response.status(OK)
                .entity(json.toJson(accounts))
                .build();
    }

    @GET
    @Path("/{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccount(@PathParam("accountId") String accountId) {
        AccountDto account = accountService.getById(accountId);
        return Response.status(OK)
                .entity(json.toJson(account))
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccount(String body) {
        AccountCreateDto accountCreateDto = json.fromJson(body, AccountCreateDto.class);
        AccountDto newAccount = accountService.createAccount(accountCreateDto);
        return Response.status(OK)
                .entity(json.toJson(newAccount))
                .build();
    }

}
