package io.pow.backend.uom;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UOMRepository extends JpaRepository<UOM, Long> {

    boolean existsByCode(String code);
}
