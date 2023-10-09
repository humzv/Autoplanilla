package com.neoproc.autoplanilla.repository;

import com.neoproc.autoplanilla.domain.DetallePago;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DetallePago entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetallePagoRepository extends JpaRepository<DetallePago, Long> {}
