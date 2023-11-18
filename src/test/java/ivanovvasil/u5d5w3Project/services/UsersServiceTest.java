package ivanovvasil.u5d5w3Project.services;

import com.github.javafaker.Faker;
import ivanovvasil.u5d5w3Project.entities.User;
import ivanovvasil.u5d5w3Project.exceptions.BadRequestException;
import ivanovvasil.u5d5w3Project.exceptions.NotFoundException;
import ivanovvasil.u5d5w3Project.payloadsDTO.CreatedUserDTO;
import ivanovvasil.u5d5w3Project.payloadsDTO.UserDTO;
import org.junit.jupiter.api.Assertions;
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
  UserAuthenticationService userAuthenticationService;

  Faker faker = new Faker(Locale.ITALY);
  UserDTO user = new UserDTO("Franck", "Johnson", faker.internet().emailAddress(), "pic1");

  @Test
  public void TestSaveUserNotNull() throws IOException {
    CreatedUserDTO savedUser = userAuthenticationService.registerUser(user);
    assertNotNull(savedUser);
  }

  @Test
  public void TestUserRegistrationReturnEmailAlreadyIsUsed() throws IOException {
    UserDTO user1 = new UserDTO("Franck", "Johnson", "vsasa@gmail.com", "pic1");
    UserDTO newuser = new UserDTO("Franck2", "Johnson2", "vsasa@gmail.com", "pic1");
    Assertions.assertThrows(BadRequestException.class, () -> {
      userAuthenticationService.registerUser(user1);
      userAuthenticationService.registerUser(newuser);
    });
  }

  @Test
  public void TestFindUserByIdReturnCorrectUser() throws IOException {
    CreatedUserDTO savedUser = userAuthenticationService.registerUser(user);
    User found = usersService.findById(savedUser.id());
    User createdUser = User.builder()
            .id(savedUser.id())
            .name(savedUser.name())
            .surname(savedUser.surname())
            .email(savedUser.email())
            .build();
    Assertions.assertEquals(createdUser, found);
  }

  @Test
  public void TestFindUserByIdThrowsNotFoundException() throws IOException {
    CreatedUserDTO savedUser = userAuthenticationService.registerUser(user);
    Assertions.assertThrows(NotFoundException.class, () -> {
      User found = usersService.findById(400L);
    });
  }

  @Test
  public void TestDeleteUserReturnNotFoundAfterFindById() throws IOException {
    CreatedUserDTO savedUser = userAuthenticationService.registerUser(user);
    usersService.findByIdAndDelete(savedUser.id());
    Assertions.assertThrows(NotFoundException.class, () -> {
      User found = usersService.findById(savedUser.id());
    });
  }

  @Test
  public void testFindByIdAndUpdateUserReturnUpdatedUser() throws IOException {
    CreatedUserDTO savedUser = userAuthenticationService.registerUser(user);
    UserDTO updatedUser = new UserDTO("Franck223", "Johnson2", faker.internet().emailAddress(), "pic1");
    User updateUser = usersService.findByIdAndUpdate(savedUser.id(), updatedUser);
    Assertions.assertNotNull(updatedUser);
    User savedUserConverted = User.builder()
            .id(savedUser.id())
            .name(savedUser.name())
            .surname(savedUser.surname())
            .email(savedUser.email())
            .build();
    Assertions.assertNotEquals(savedUserConverted, updateUser);
  }

  @Test
  public void testFindByIdAndUpdateUserReturnNotFound() throws IOException {
    CreatedUserDTO savedUser = userAuthenticationService.registerUser(user);
    UserDTO updatedUser = new UserDTO("Franck223", "Johnson2", faker.internet().emailAddress(), "pic1");
    Assertions.assertThrows(NotFoundException.class, () -> {
      User updatedEmployee = usersService.findByIdAndUpdate(32000L, updatedUser);
    });
  }

}
