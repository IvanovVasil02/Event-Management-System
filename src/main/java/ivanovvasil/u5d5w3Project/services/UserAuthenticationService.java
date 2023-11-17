package ivanovvasil.u5d5w3Project.services;

import ivanovvasil.u5d5w3Project.entities.User;
import ivanovvasil.u5d5w3Project.enums.Role;
import ivanovvasil.u5d5w3Project.exceptions.BadRequestException;
import ivanovvasil.u5d5w3Project.payloadsDTO.UserDTO;
import ivanovvasil.u5d5w3Project.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserAuthenticationService {
  @Autowired
  private UsersRepository usersRepository;


  public User registerUser(UserDTO body) throws IOException {
    usersRepository.findByEmail(body.email()).ifPresent(author -> {
      throw new BadRequestException("The email  " + author.getEmail() + " is already used!");
    });
    User newUser = new User();
    newUser.setName(body.name());
    newUser.setSurname(body.surname());
    newUser.setEmail(body.email());
    newUser.setPassword(body.password());
    newUser.setRole(Role.USER);
    return usersRepository.save(newUser);
  }

}
