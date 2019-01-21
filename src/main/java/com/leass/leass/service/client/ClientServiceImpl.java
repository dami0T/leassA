package com.leass.leass.service.client;

import com.leass.leass.model.Client;
import com.leass.leass.repository.ClientRepository;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    ClientRepository clientRepository;


    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void save(Client client) {
        clientRepository.save(client);
    }

    @Override
    public void saveDto(ClientDto clientDto) {
        Client client = convertClientToEntity(clientDto);
        save(client);
    }

    @Override
    public List<Client> findAll() {
        return new ArrayList<>(clientRepository.findAll());
    }


    @Override
    public List<ClientDto> findAllDto() {
        List<ClientDto> clientDtos = new ArrayList<>();
        List<Client> clients = clientRepository.findAll();
        for (Client client : clients) {
            clientDtos.add(convertToClientDto(client));
        }
        return clientDtos;
    }

    @Override
    public ClientDto getClientById(Long id){
        Optional<Client> clients = clientRepository.findById(id);
        return convertToClientDto(clients.get());
    }

    @Override
    public void bindingClient(ClientDto clientDto, ClientDto oldClientDto) {
        BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
        try {
            beanUtilsBean.copyProperties(clientDto, oldClientDto);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private ClientDto convertToClientDto(Client client) {
        ClientDto clientDto = modelMapper.map(client, ClientDto.class);
        return clientDto;
    }

    private Client convertClientToEntity(ClientDto clientDto){
        Client client = modelMapper.map(clientDto, Client.class);
        return client;
    }
}
