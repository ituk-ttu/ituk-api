package ee.ituk.api;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import ee.ituk.api.repository.UserRepository;
import ee.ituk.tables.pojos.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {

    @Autowired
    private UserRepository userRepository;

    public User getUser(int id) {
        return userRepository.getUser(id);
    }

    public List<User> getUsersbyStatus(Integer statusId) {
        return userRepository.getUsersByStatus(statusId);
    }
    public List<User> getActiveUsers() {
        return userRepository.getActiveUsers();
    }
    public List<User> allUsers() {
        List<User> allUsers = userRepository.getAllUsers();
        return allUsers;
    }
}
