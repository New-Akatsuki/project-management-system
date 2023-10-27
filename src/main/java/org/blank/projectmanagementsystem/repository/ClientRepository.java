package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long>{

}
