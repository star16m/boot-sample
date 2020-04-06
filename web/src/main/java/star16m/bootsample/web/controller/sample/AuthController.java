package star16m.bootsample.web.controller.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import star16m.bootsample.web.common.SimpleResponse;
import star16m.bootsample.web.config.security.resource.User;
import star16m.bootsample.web.config.security.resource.UserRepository;
import star16m.bootsample.web.config.security.resource.dto.CreateAuthenticationUser;
import star16m.bootsample.web.controller.annotations.SimpleRestMethod;
import star16m.bootsample.web.controller.annotations.SimpleRestMethodMapping;

@RestController
public class AuthController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @ApiOperation("유저 생성")
    @PostMapping(value="/register")
    @SimpleRestMethodMapping(SimpleRestMethod.CREATE)
    public final SimpleResponse<User> createUser(@RequestBody CreateAuthenticationUser user) {
        User createdUser = this.modelMapper.map(user, User.class);
        createdUser = this.userRepository.save(createdUser);
        return SimpleResponse.of(createdUser);
    }
}
