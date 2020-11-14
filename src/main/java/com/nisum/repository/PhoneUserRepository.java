package com.nisum.repository;

import com.nisum.domain.PhoneUser;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the PhoneUser entity.
 */
@Repository
public interface PhoneUserRepository extends JpaRepository<PhoneUser, Long> {

    @Query("select phoneUser from PhoneUser phoneUser where phoneUser.user.login = ?#{principal.username}")
    List<PhoneUser> findByUserIsCurrentUser();

}
