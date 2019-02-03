package ee.ituk.api.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import ee.ituk.api.dto.User;
import ee.ituk.api.repository.StatusRepository;
import ee.ituk.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserResolver implements GraphQLResolver<User> {

    @Autowired
    StatusRepository statusRepository;


}
