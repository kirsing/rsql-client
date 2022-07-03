package com.example.demo_project.service;


import com.example.demo_project.model.Client;
import com.example.demo_project.model.CriteriaParam;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Repository
@AllArgsConstructor

public class CriteriaService {
    @PersistenceContext
    EntityManager entityManager;


//    public List<Contract> findByfield1(List<SpecificationItem> specificationItems){
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Contract> criteriaQuery = criteriaBuilder.createQuery(Contract.class);
//        Root<Contract> root = criteriaQuery.from(Contract.class);
//        Predicate basicPredicate = criteriaBuilder.or(specificationItems
//                .stream()
//                .map(s -> criteriaBuilder.equal(root.get(s.getField()),s.getValue())) // из специфакиции в STEAM Predicate [до этого стрим спецификаций]
//                .toArray(Predicate[]::new));  // сделали массив предикатов
//        criteriaQuery.where(basicPredicate);
//        return entityManager.createQuery(criteriaQuery).getResultList();

    public List<Client> getClients() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> root = criteriaQuery.from(Client.class);

        criteriaQuery.select(root);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<Client> getClientByParam(String name, String value) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> root = criteriaQuery.from(Client.class);
        Predicate predicate = criteriaBuilder.equal(root.get(name), name.equals("dateTimeAppointment") || name.equals("offsetDateTimeAppointment") ? LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd,HH:mm")) : value);

        criteriaQuery.where(predicate);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<Client> clientsListAnd(List<CriteriaParam> list) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> root = criteriaQuery.from(Client.class);
        Predicate basicPredicate = criteriaBuilder.and(list
                .stream()
                .map(s -> criteriaBuilder.equal(root.get(s.getName()), s.getName().equals("dateTimeAppointment") ? LocalDateTime.parse((String)s.getValue(), DateTimeFormatter.ofPattern("yyyy-MM-dd,HH:mm")) : s.getValue()))
                .toArray(Predicate[]::new));
        criteriaQuery.where(basicPredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<Client> clientsListOr(List<CriteriaParam> list) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> root = criteriaQuery.from(Client.class);
        Predicate basicPredicate = criteriaBuilder.or(list
                .stream()
                .map(s -> criteriaBuilder.equal(root.get(s.getName()), s.getName().equals("dateTimeAppointment") ? LocalDateTime.parse((String)s.getValue(), DateTimeFormatter.ofPattern("yyyy-MM-dd,HH:mm")) : s.getValue()))
                .toArray(Predicate[]::new));
        criteriaQuery.where(basicPredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

        @Transactional
        public Client saveClient (Client client){
            return entityManager.merge(client);
        }
    }

