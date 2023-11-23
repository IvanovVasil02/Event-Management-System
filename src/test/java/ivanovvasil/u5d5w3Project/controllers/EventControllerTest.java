package ivanovvasil.u5d5w3Project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ivanovvasil.u5d5w3Project.entities.Event;
import ivanovvasil.u5d5w3Project.entities.User;
import ivanovvasil.u5d5w3Project.payloadsDTO.*;
import ivanovvasil.u5d5w3Project.services.EventsService;
import ivanovvasil.u5d5w3Project.services.PrenotationsService;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {
  @Autowired
  UserAuthenticationService userAuthenticationService;
  @Autowired
  private UsersService usersService;
  @Autowired
  private EventsService eventsService;
  @Autowired
  private PrenotationsService prenotationsService;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private MockMvc mockMvc;


  private UserDTO userDTO;
  private CreatedUserDTO createdUserDTO;
  private Event event;
  private String token;

  public User getManager() {
    return usersService.findByEmail("admin@gmail.com");
  }

  @BeforeEach
  public void setUp() throws IOException {
    userDTO = new UserDTO("Franck", "Johnson", "taaassaest0909@gmail.com", "12345678");
    createdUserDTO = userAuthenticationService.registerUser(userDTO);
    UserLoginDTO userLoginDTO = new UserLoginDTO("admin@gmail.com", "1234");
    token = userAuthenticationService.authenticateUser(userLoginDTO);
    EventResponseDTO savedEvent = eventsService.save(getManager(), new EventDTO("Meeting", "meeting", "2023-11-25", "Rome", 5, "Img1"));
    event = eventsService.findById(savedEvent.event_id());
  }

  @AfterEach
  public void afterAll() throws IOException {
    if (createdUserDTO != null) {
      usersService.findByIdAndDelete(createdUserDTO.id());
    }
  }

  @Test
  public void testPostEventReturn2001() throws Exception {
    EventDTO eventDTO = new EventDTO("Meeting", "meeting", "2023-11-25", "Rome", 5, "Img1");
    String requestBody = objectMapper.writeValueAsString(eventDTO);
    mockMvc.perform(MockMvcRequestBuilders.post("/events")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isCreated()).andDo(print());
  }

  @Test
  public void testPostEventReturn400() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/events")
                    .header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isBadRequest()).andDo(print());
  }

  @Test
  public void testBookEventReturn200() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/events/" + event.getId() + "/bookMe")
                    .header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
  }

  @Test
  public void testBookEventReturn400() throws Exception {
    prenotationsService.bookEvent(getManager(), event.getId());
    mockMvc.perform(MockMvcRequestBuilders.post("/events/" + event.getId() + "/bookMe")
                    .header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isBadRequest()).andDo(print());
  }

  @Test
  public void testBookEventReturn404() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/events/" + -2L + "/bookMe")
                    .header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(print());
  }

  @Test
  public void testBookEventReturn401() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/events/" + event.getId() + "/bookMe"))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andDo(print());
  }


  @Test
  public void testGetEventsReturn200OK() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/events")
                    .header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
  }

  @Test
  public void testGetEventReturn200() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/events/" + event.getId())
                    .header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
  }

  @Test
  public void testGetEventReturn404() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/events/" + -2L)
                    .header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(print());
  }

  @Test
  public void testEditEventReturn200() throws Exception {
    EventDTO eventDTO = new EventDTO("Meeting", "meeting", "2023-11-25", "Milan", 5, "Img1");
    String requestBody = objectMapper.writeValueAsString(eventDTO);
    mockMvc.perform(MockMvcRequestBuilders.put("/events/" + event.getId())
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
  }

  @Test
  public void testEditEventReturn400() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.put("/users/" + createdUserDTO.id())
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest()).andDo(print());
  }

  @Test
  public void testEditEventReturn404() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.put("/events/" + -2L)
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest()).andDo(print());
  }

  @Test
  public void testEditEventReturn401() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.put("/events/" + event.getId()))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andDo(print());
  }

  @Test
  public void testDeleteUserReturn204() throws Exception {
    EventDTO eventDTO = new EventDTO("Meeting", "meeting", "2023-11-25", "Milan", 5, "Img1");
    EventResponseDTO eventResponseDTO = eventsService.save(getManager(), eventDTO);
    mockMvc.perform(MockMvcRequestBuilders.delete("/users/" + eventResponseDTO.event_id())
                    .header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isNoContent()).andDo(print());
  }


}
