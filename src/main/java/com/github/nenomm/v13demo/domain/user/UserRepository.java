package com.github.nenomm.v13demo.domain.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    public User findByUsername(String username);

}
