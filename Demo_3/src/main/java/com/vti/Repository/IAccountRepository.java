package com.vti.Repository;

import com.vti.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Integer>, JpaSpecificationExecutor<Account> {
    Account findByUserName(String userName);

    boolean existsByEmail(String email);

    boolean existsByUserName(String userName);
    Account findById(int id);

    Account findByEmail(String email);

    Account findByToken(String token);
}
