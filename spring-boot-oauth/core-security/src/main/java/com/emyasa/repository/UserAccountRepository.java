package com.emyasa.repository;

import com.emyasa.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {

    @Query("SELECT userAccount FROM UserAccount userAccount " +
            "LEFT JOIN FETCH userAccount.roles roles " +
            "WHERE userAccount.username = :username")
    UserAccount findByUsername(String username);

}
