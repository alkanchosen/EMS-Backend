package com.yte.intern.project.manageclients.controller;

import com.yte.intern.project.manageclients.dto.ClientDTO;
import com.yte.intern.project.manageclients.entity.Client;
import com.yte.intern.project.manageclients.mapper.ClientMapper;
import com.yte.intern.project.manageclients.service.ManageClientService;
import com.yte.intern.project.manageenrollment.entity.Enrollment;
import com.yte.intern.project.manageevents.dto.EventDTO;
import com.yte.intern.project.manageevents.entity.Event;
import com.yte.intern.project.manageevents.mapper.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class ManageClientController {

    private final ManageClientService manageClientService;
    private final ClientMapper clientMapper;
    private final EventMapper eventMapper;

    @GetMapping("/clients")
    public List<ClientDTO> listAllClients() {
        List<Client> clients = manageClientService.getAllClients();
        return clientMapper.mapToDto(clients);
    }

    @PostMapping("/clients") // @Valid eklendi :)
    public Client addClient(@Valid @RequestBody ClientDTO clientDTO) {
        Client client = clientMapper.mapToEntity(clientDTO);
        manageClientService.addClient(client);

        return client;
    }

    @GetMapping("/clients/{username}/enrollments")
    public Set<Enrollment> getClientEnrollments(@PathVariable String username) {
        Client client = manageClientService.findByUsername(username).get();
        return client.getEnrollments();
    }

    @GetMapping("/clients/{username}/events")
    public Set<EventDTO> getClientEvents(@PathVariable String username) {
        Set<EventDTO> events = new HashSet<>();

        Client client = manageClientService.findByUsername(username).get();
        Set<Enrollment> enrollments = client.getEnrollments();

        enrollments.forEach( (e) ->events.add(eventMapper.mapToDto(e.getEvent())));

        return events;
    }

    @PutMapping("/clients/{username}") // @Valid eklendi :)
    public ClientDTO updateClient(@Valid @RequestBody ClientDTO clientDTO, @PathVariable String username) {

        Client oldClient = manageClientService.findByUsername(username).get();
        System.out.println(oldClient);

        oldClient.setName(clientDTO.getName());
        oldClient.setSurname(clientDTO.getSurname());
        oldClient.setTcKimlikNo(clientDTO.getTcKimlikNo());
        oldClient.setEmail(clientDTO.getEmail());

        manageClientService.addClient(oldClient);

        ClientDTO newClient = clientMapper.mapToDto(oldClient);

        return newClient;
    }

    @Transactional
    @DeleteMapping("/clients/delete/{tcKimlikNo}")
    public void deleteClient(@PathVariable String tcKimlikNo) {
        manageClientService.deleteByTcKimlikNo(tcKimlikNo);
    }
}
