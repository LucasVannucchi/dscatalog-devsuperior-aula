package com.devsuperior.dscatalog.projetoFinalModulo01.clients.domain.service;

import com.devsuperior.dscatalog.projetoFinalModulo01.clients.domain.dto.ClientRequestDTO;
import com.devsuperior.dscatalog.projetoFinalModulo01.clients.domain.dto.ClientResponseDTO;
import com.devsuperior.dscatalog.projetoFinalModulo01.clients.domain.entity.Client;
import com.devsuperior.dscatalog.projetoFinalModulo01.clients.domain.exceptions.ResourceExceptions;
import com.devsuperior.dscatalog.projetoFinalModulo01.clients.domain.mapper.ClientMapper;
import com.devsuperior.dscatalog.projetoFinalModulo01.clients.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Transactional(readOnly = true)
    public Page<ClientResponseDTO> findAllPaged(PageRequest pageRequest) {
        Page<Client> clients = clientRepository.findAll(pageRequest);
        return clients.map(clientMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public ClientResponseDTO findById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceExceptions("Product not found with Id: " + id));
        return clientMapper.toDTO(client);
    }

    @Transactional
    public ClientResponseDTO insert(ClientRequestDTO dto) {
        Client client = clientMapper.toEntity(dto);
        client = clientRepository.save(client);
        return clientMapper.toDTO(client);
    }

    @Transactional
    public ClientResponseDTO update(Long id, ClientRequestDTO dto) {
        try {
            Client client = clientRepository.getReferenceById(id);
            client.setName(dto.name());
            client.setCpf(dto.cpf());
            client.setIncome(dto.income());
            client.setBirthDate(dto.birthDate());
            client.setChildren(dto.children());
            client = clientRepository.save(client);
            return clientMapper.toDTO(client);
        } catch (EntityNotFoundException e) {
            throw new ResourceExceptions("Id: " + id + " not found");
        }
    }

    @Transactional
    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceExceptions("Id: " + id + " not found");
        }

        try {
            clientRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceExceptions("Integrity violation");
        }
    }
}
