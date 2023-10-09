package com.neoproc.autoplanilla.repository;

import com.neoproc.autoplanilla.domain.Pago;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Pago entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {}
