package com.neoproc.autoplanilla.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.neoproc.autoplanilla.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlanillaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Planilla.class);
        Planilla planilla1 = new Planilla();
        planilla1.setId(1L);
        Planilla planilla2 = new Planilla();
        planilla2.setId(planilla1.getId());
        assertThat(planilla1).isEqualTo(planilla2);
        planilla2.setId(2L);
        assertThat(planilla1).isNotEqualTo(planilla2);
        planilla1.setId(null);
        assertThat(planilla1).isNotEqualTo(planilla2);
    }
}
