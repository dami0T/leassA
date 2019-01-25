package com.leass.leass.service.client;

import com.leass.leass.model.Client;
import com.leass.leass.model.User;

import java.util.List;

public interface ClientService {

    void save(Client client);

    void saveDto(ClientDto clientDto);

    List<Client> findAll();

    List<ClientDto> findAllDto();

    ClientDto getClientById(Long id);

    void bindingClient(ClientDto clientDto, ClientDto oldClientDto);
}
