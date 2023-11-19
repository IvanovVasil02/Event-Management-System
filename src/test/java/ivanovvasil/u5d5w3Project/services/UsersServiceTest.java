package ivanovvasil.u5d5w3Project.services;

import com.github.javafaker.Faker;
import ivanovvasil.u5d5w3Project.entities.User;
import ivanovvasil.u5d5w3Project.enums.Role;
import ivanovvasil.u5d5w3Project.exceptions.BadRequestException;
import ivanovvasil.u5d5w3Project.exceptions.NotFoundException;
import ivanovvasil.u5d5w3Project.payloadsDTO.CreatedUserDTO;
import ivanovvasil.u5d5w3Project.payloadsDTO.UserDTO;
import ivanovvasil.u5d5w3Project.repositories.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UsersServiceTest {
  @Autowired
  UsersService usersService;
  @Autowired
  UsersRepository usersRepository;
  @Autowired
  UserAuthenticationService userAuthenticationService;

  Faker faker = new Faker(Locale.ITALY);
  private UserDTO userDTO;
  private CreatedUserDTO createdUserDTO;
  private User user;

  @BeforeEach
  public void setup() throws IOException {
    userDTO = new UserDTO("Franck", "Johnson", "test897@gmail.com", "pic1");
    createdUserDTO = userAuthenticationService.registerUser(userDTO);
    user = new User(createdUserDTO.id(), createdUserDTO.name(),
            createdUserDTO.surname(), createdUserDTO.email(), Role.USER);
  }

  @AfterEach
  public void afterAllTests() {
    usersRepository.delete(user);
  }

  @Test
  public void TestSaveUserNotNull() throws IOException {
    assertNotNull(createdUserDTO);
  }

  @Test
  public void TestUserRegistrationReturnEmailAlreadyIsUsed() throws IOException {
    Assertions.assertThrows(BadRequestException.class, () -> {
      userAuthenticationService.registerUser(userDTO);
      userAuthenticationService.registerUser(userDTO);
    });
  }

  @Test
  public void TestFindUserByIdReturnCorrectUser() throws IOException {
    User found = usersService.findById(createdUserDTO.id());
    Assertions.assertEquals(user, found);
  }

  @Test
  public void TestFindUserByIdThrowsNotFoundException() throws IOException {
    Assertions.assertThrows(NotFoundException.class, () -> {
      User found = usersService.findById(4000L);
    });
  }

  @Test
  public void TestDeleteUserReturnNotFoundAfterFindById() throws IOException {
    usersService.findByIdAndDelete(createdUserDTO.id());
    Assertions.assertThrows(NotFoundException.class, () -> {
      usersService.findById(user.getId());
    });
  }

  @Test
  public void testFindByIdAndUpdateUserReturnUpdatedUser() throws IOException {
    UserDTO updatedUser = new UserDTO("Franck223", "Johnson2", faker.internet().emailAddress(), "pic1");
    User updateUser = usersService.findByIdAndUpdate(createdUserDTO.id(), updatedUser);
    Assertions.assertNotEquals(user, updateUser);
  }

  @Test
  public void testFindByIdAndUpdateUserReturnNotFound() throws IOException {
    UserDTO updatedUser = new UserDTO("Franck223", "Johnson2", faker.internet().emailAddress(), "pic1");
    Assertions.assertThrows(NotFoundException.class, () -> {
      User updatedEmployee = usersService.findByIdAndUpdate(32000L, updatedUser);
    });
  }

}
