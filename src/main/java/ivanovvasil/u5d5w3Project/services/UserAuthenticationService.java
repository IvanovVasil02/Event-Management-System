package ivanovvasil.u5d5w3Project.services;

import ivanovvasil.u5d5w3Project.entities.User;
import ivanovvasil.u5d5w3Project.enums.Role;
import ivanovvasil.u5d5w3Project.exceptions.BadRequestException;
import ivanovvasil.u5d5w3Project.exceptions.NotFoundException;
import ivanovvasil.u5d5w3Project.exceptions.UnauthorizedException;
import ivanovvasil.u5d5w3Project.payloadsDTO.CreatedUserDTO;
import ivanovvasil.u5d5w3Project.payloadsDTO.UserDTO;
import ivanovvasil.u5d5w3Project.payloadsDTO.UserLoginDTO;
import ivanovvasil.u5d5w3Project.repositories.UsersRepository;
import ivanovvasil.u5d5w3Project.security.JWTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserAuthenticationService {
  @Autowired
  private UsersRepository usersRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private JWTools jwTools;

  public CreatedUserDTO registerUser(UserDTO body) throws IOException {
    usersRepository.findByEmail(body.email()).ifPresent(author -> {
      throw new BadRequestException("The email  " + author.getEmail() + " is already used!");
    });
    User user = new User();
    user.setName(body.name());
    user.setSurname(body.surname());
    user.setEmail(body.email());
    user.setPassword(passwordEncoder.encode(body.password()));
    user.setRole(Role.USER);
    usersRepository.save(user);
    String token = jwTools.createToken(user);
    return new CreatedUserDTO(user.getId(), user.getName(), user.getSurname(), user.getEmail(), token);
  }

  public String authenticateUser(UserLoginDTO body) {
    User foundUser = usersRepository.findByEmail(body.email()).orElseThrow(() -> new NotFoundException(body.email()));
    if (passwordEncoder.matches(body.password(), foundUser.getPassword())) {
      return jwTools.createToken(foundUser);
    } else {
      throw new UnauthorizedException("Invalid credentials");
    }
  }

}
