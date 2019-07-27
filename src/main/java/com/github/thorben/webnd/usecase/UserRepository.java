package com.github.thorben.webnd.usecase;

import com.github.thorben.webnd.domain.user.EmailAddress;
import com.github.thorben.webnd.domain.user.User;
import com.github.thorben.webnd.domain.user.Username;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

	Optional<User> findByEmailAddress(EmailAddress emailAddress);

	Optional<User> findByEmailAddressOrUsername(EmailAddress emailAddress, Username username);

	Optional<User> findByUsername(Username username);

	boolean existsByEmailAddress(EmailAddress emailAddress);

	boolean existsByUsername(Username username);

}
