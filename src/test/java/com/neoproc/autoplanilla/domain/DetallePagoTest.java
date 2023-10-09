package com.neoproc.autoplanilla.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.neoproc.autoplanilla.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetallePagoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetallePago.class);
        DetallePago detallePago1 = new DetallePago();
        detallePago1.setId(1L);
        DetallePago detallePago2 = new DetallePago();
        detallePago2.setId(detallePago1.getId());
        assertThat(detallePago1).isEqualTo(detallePago2);
        detallePago2.setId(2L);
        assertThat(detallePago1).isNotEqualTo(detallePago2);
        detallePago1.setId(null);
        assertThat(detallePago1).isNotEqualTo(detallePago2);
    }
}
