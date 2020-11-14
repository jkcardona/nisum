package com.nisum.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.nisum.web.rest.TestUtil;

public class PhoneUserTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhoneUser.class);
        PhoneUser phoneUser1 = new PhoneUser();
        phoneUser1.setId(1L);
        PhoneUser phoneUser2 = new PhoneUser();
        phoneUser2.setId(phoneUser1.getId());
        assertThat(phoneUser1).isEqualTo(phoneUser2);
        phoneUser2.setId(2L);
        assertThat(phoneUser1).isNotEqualTo(phoneUser2);
        phoneUser1.setId(null);
        assertThat(phoneUser1).isNotEqualTo(phoneUser2);
    }
}
