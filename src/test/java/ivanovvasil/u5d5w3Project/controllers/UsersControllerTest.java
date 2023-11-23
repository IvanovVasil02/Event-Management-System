package ivanovvasil.u5d5w3Project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import ivanovvasil.u5d5w3Project.payloadsDTO.CreatedUserDTO;
import ivanovvasil.u5d5w3Project.payloadsDTO.UserDTO;
import ivanovvasil.u5d5w3Project.payloadsDTO.UserLoginDTO;
import ivanovvasil.u5d5w3Project.services.UserAuthenticationService;
import ivanovvasil.u5d5w3Project.services.UsersService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.util.Locale;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerTest {
  @Autowired
  UserAuthenticationService userAuthenticationService;
  @Autowired
  private UsersService usersService;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private MockMvc mockMvc;

  private UserDTO userDTO;
  private CreatedUserDTO createdUserDTO;

  private String token;


  @BeforeEach
  public void setUp() throws IOException {
    userDTO = new UserDTO("Franck", "Johnson", "tst0909@gmail.com", "12345678");
    createdUserDTO = userAuthenticationService.registerUser(userDTO);
    UserLoginDTO userLoginDTO = new UserLoginDTO("admin@gmail.com", "1234");
    token = userAuthenticationService.authenticateUser(userLoginDTO);
  }

  @AfterEach
  public void afterAll() throws IOException {
    if (createdUserDTO != null) {
      usersService.findByIdAndDelete(createdUserDTO.id());
    }
  }

  @Test
  public void TestRegisterUserReturn201() throws Exception {
    Faker faker = new Faker(Locale.ITALY);
    userDTO = new UserDTO("Franck", "Johnson", faker.internet().emailAddress(), "12345678");
    String requestBody = objectMapper.writeValueAsString(userDTO);
    mockMvc.perform(MockMvcRequestBuilders.post("/authentication/register")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isCreated()).andDo(print());
  }

  @Test
  public void TestRegisterUserReturn400() throws Exception {
    Faker faker = new Faker(Locale.ITALY);
    userDTO = new UserDTO("Franck", "", faker.internet().emailAddress(), "12345678");
    String requestBody = objectMapper.writeValueAsString(userDTO);
    mockMvc.perform(MockMvcRequestBuilders.post("/authentication/register")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isBadRequest()).andDo(print());
  }

  @Test
  public void TestLoginUserReturn200() throws Exception {
    UserLoginDTO userLoginDTO = new UserLoginDTO("tst0909@gmail.com", "12345678");
    String requestBody = objectMapper.writeValueAsString(userLoginDTO);
    mockMvc.perform(MockMvcRequestBuilders.post("/authentication/login")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
  }

  @Test
  public void TestLoginUserReturn400() throws Exception {
    UserLoginDTO userLoginDTO = new UserLoginDTO("tst0909@gmail.com", "");
    String requestBody = objectMapper.writeValueAsString(userLoginDTO);
    mockMvc.perform(MockMvcRequestBuilders.post("/authentication/login")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isBadRequest()).andDo(print());
  }

  @Test
  public void TestRegisterUserReturn400CauseAlreadyUsedEmail() throws Exception {
    Faker faker = new Faker(Locale.ITALY);
    userDTO = new UserDTO("Franck", "esdsfsd", "tst0909@gmail.com", "12345678");
    String requestBody = objectMapper.writeValueAsString(userDTO);
    mockMvc.perform(MockMvcRequestBuilders.post("/authentication/register")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isBadRequest()).andDo(print());
  }

  @Test
  public void testGetUsersReturn200OK() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/users")
                    .header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
  }

  @Test
  public void testGetUsersReturn401() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/users")
                    .header("Authorization", "Bearer "))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andDo(print());
  }

  @Test
  public void testGetUserReturn200() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/users/" + createdUserDTO.id())
                    .header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
  }

  @Test
  public void testGetUserReturn404() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/users/" + -2L)
                    .header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(print());
  }

  @Test
  public void testGetProfileReturn200() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/users/me")
                    .header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
  }

  @Test
  public void testEditProfileReturn200() throws Exception {
    UserDTO userDTO1 = new UserDTO("admin12", "admin12", "admin@gmail.com", "1234");
    String requestBody = objectMapper.writeValueAsString(userDTO1);
    mockMvc.perform(MockMvcRequestBuilders.get("/users/me")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
  }

  @Test
  public void testEditProfileReturn400() throws Exception {
    UserDTO userDTO1 = new UserDTO("admin12", "", "admin@gmail.com", "1234");
    String requestBody = objectMapper.writeValueAsString(userDTO1);
    mockMvc.perform(MockMvcRequestBuilders.put("/users/me")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isBadRequest()).andDo(print());
  }

  @Test
  public void testEditUserReturn200() throws Exception {
    userDTO = new UserDTO("Franck", "asdas", "st0909@gmail.com", "12345678");
    String requestBody = objectMapper.writeValueAsString(userDTO);
    mockMvc.perform(MockMvcRequestBuilders.put("/users/" + createdUserDTO.id())
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
  }

  @Test
  public void testEditUserReturn400() throws Exception {
    userDTO = new UserDTO("Franck", "", "taaassaest0909@gmail.com", "12345678");
    String requestBody = objectMapper.writeValueAsString(userDTO);
    mockMvc.perform(MockMvcRequestBuilders.put("/users/" + createdUserDTO.id())
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isBadRequest()).andDo(print());
  }

  @Test
  public void testEditUserReturn404() throws Exception {
    userDTO = new UserDTO("Franck", "", "taaassaest0909@gmail.com", "12345678");
    String requestBody = objectMapper.writeValueAsString(userDTO);
    mockMvc.perform(MockMvcRequestBuilders.put("/users/" + -2L)
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isBadRequest()).andDo(print());
  }

  @Test
  public void testEditUserReturn204() throws Exception {
    UserDTO userDTO1 = new UserDTO("name", "surname", "email@gmaill.com", "123456");
    CreatedUserDTO createdUserDTO1 = userAuthenticationService.registerUser(userDTO1);
    mockMvc.perform(MockMvcRequestBuilders.delete("/users/" + createdUserDTO1.id())
                    .header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isNoContent()).andDo(print());
  }


}
