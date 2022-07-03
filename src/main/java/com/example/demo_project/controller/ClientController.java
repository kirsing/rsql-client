package com.example.demo_project.controller;


import com.example.demo_project.model.Client;
import com.example.demo_project.model.CriteriaParam;
import com.example.demo_project.model.CustomRsqlVisitor;
import com.example.demo_project.repository.ClientRepository;
import com.example.demo_project.service.CriteriaService;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/criteria")
public class ClientController {
    CriteriaService criteriaService;
    ClientRepository clientRepository;

    @GetMapping("/byParam")
    public List<Client> getClientsByParam(@RequestParam(value = "name") String name, @RequestParam(value = "value")
            String value) {
        return criteriaService.getClientByParam(name, value);
    }
    @GetMapping()
    public List<Client> getClients() {
        return criteriaService.getClients();
    }

    @PostMapping
    public Client saveClient(@RequestBody Client client) {
        return criteriaService.saveClient(client);
    }

    @PostMapping("/criteriaparam")
    public List<Client> getClients(@RequestBody List<CriteriaParam> list) {
        return criteriaService.clientsListAnd(list);
    }

    @PostMapping("/criteriaparamor")
    public List<Client> getClientsOr(@RequestBody List<CriteriaParam> list) {
        return criteriaService.clientsListOr(list);
    }
    @GetMapping("/rsql")
    public List<Client> findAllByRsql(@RequestParam(value = "search") String search) {
//        search=firstName==jo*;age<25
        Node rootNode = new RSQLParser().parse(search);
        Specification<Client> spec = rootNode.accept(new CustomRsqlVisitor<Client>());
        return clientRepository.findAll(spec);
    }
}
