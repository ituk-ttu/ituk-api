package ee.ituk.api.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import ee.ituk.api.dto.User;
import ee.ituk.api.dto.UserInput;
import ee.ituk.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMutations implements GraphQLMutationResolver {

    @Autowired
    UserRepository userRepository;

    public User newUser(UserInput userInput) {
        return userRepository.addUser(userInput);
    }

    public User changeStatus(int id, int statusId) {
        return userRepository.changeStatus(id, statusId);
    }
}
