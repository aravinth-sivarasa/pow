package io.pow.backend.uom;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



public interface UOMRepository extends JpaRepository<UOM, Long> {

    boolean existsByCode(String code);

    Optional<UOM> findByCode(String code);
}
