package com.example.demo_project.repository;

import com.example.demo_project.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClientRepository extends JpaRepository<Client, Long> , JpaSpecificationExecutor<Client> {
}
