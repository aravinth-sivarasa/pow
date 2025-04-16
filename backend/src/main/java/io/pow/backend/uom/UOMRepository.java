package io.pow.backend.uom;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;



public interface UOMRepository extends JpaRepository<UoM, UUID> {

    boolean existsByCode(String code);

    Optional<UoM> findByCode(String code);
}
