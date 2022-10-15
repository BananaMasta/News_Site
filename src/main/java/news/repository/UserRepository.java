package news.repository;


import news.models.Role;
import news.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String login);

    boolean existsByPassword(String password);

    User findByUsername(String username);

    boolean existsByRole(Role role);


}
