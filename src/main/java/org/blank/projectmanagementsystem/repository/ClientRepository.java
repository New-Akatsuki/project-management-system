package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {



   void deleteById(Long id);





}
