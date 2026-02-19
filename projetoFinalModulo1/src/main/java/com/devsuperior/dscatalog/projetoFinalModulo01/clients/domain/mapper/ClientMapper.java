package com.devsuperior.dscatalog.projetoFinalModulo01.clients.domain.mapper;

import com.devsuperior.dscatalog.projetoFinalModulo01.clients.domain.dto.ClientRequestDTO;
import com.devsuperior.dscatalog.projetoFinalModulo01.clients.domain.dto.ClientResponseDTO;
import com.devsuperior.dscatalog.projetoFinalModulo01.clients.domain.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public Client toEntity(ClientRequestDTO dto){
        if (dto == null){
            return null;
        }

        Client client = new Client();
        client.setName(dto.name());
        client.setCpf(dto.cpf());
        client.setIncome(dto.income());
        client.setBirthDate(dto.birthDate());
        client.setChildren(dto.children());
        return client;
    }

    public ClientResponseDTO toDTO(Client client){
        if (client == null){
            return null;
        }

        return new ClientResponseDTO(
                client.getId(),
                client.getName(),
                client.getCpf(),
                client.getIncome(),
                client.getBirthDate(),
                client.getChildren(),
                client.getCreatedAt(),
                client.getUpdatedAt()
        );
    }
}
