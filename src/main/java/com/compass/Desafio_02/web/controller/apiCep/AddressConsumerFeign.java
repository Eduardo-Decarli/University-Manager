package com.compass.Desafio_02.web.controller.apiCep;

import com.compass.Desafio_02.entities.api.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "address-consumer", url = "https://viacep.com.br/ws")
public interface AddressConsumerFeign {
    @GetMapping("/{cep}/json")
    public Address getAddresByCep(@PathVariable("cep") String cep);
}
