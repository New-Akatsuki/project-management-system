package org.blank.projectmanagementsystem.repository;

import org.blank.projectmanagementsystem.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByName(String name);
    Optional <Client> findByEmail(String email);
   Optional <Client> findByPhoneNumber(String phoneNumber);
   Optional <Client> findById(Long id);
   void deleteById(Long id);
   void deleteByName(String name);
    void deleteByEmail(String email);
    void deleteByPhoneNumber(String phoneNumber);




}
