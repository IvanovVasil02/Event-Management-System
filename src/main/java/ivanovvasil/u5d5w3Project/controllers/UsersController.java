package ivanovvasil.u5d5w3Project.controllers;

import ivanovvasil.u5d5w3Project.entities.User;
import ivanovvasil.u5d5w3Project.exceptions.BadRequestException;
import ivanovvasil.u5d5w3Project.payloadsDTO.UserDTO;
import ivanovvasil.u5d5w3Project.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UsersController {
  @Autowired
  private UsersService usersService;

  @GetMapping("")
  public Page<User> getUsers(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "15") int size,
                             @RequestParam(defaultValue = "id") String orderBy) {
    return usersService.findAll(page, size, orderBy);
  }

  @GetMapping("/{id}")
  public User getUser(@PathVariable Long id) {
    return usersService.findById(id);
  }

  @PutMapping("/{id}")
  public User editUser(@PathVariable Long id, @RequestBody @Validated UserDTO body, BindingResult validation) {
    if (validation.hasErrors()) {
      throw new BadRequestException("Empty or not respected fields", validation.getAllErrors());
    } else {
      try {
        return usersService.findByIdAndUpdate(id, body);
      } catch (MethodArgumentTypeMismatchException e) {
        throw new BadRequestException("Entered id is invalid");
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteUser(@PathVariable Long id) {
    usersService.findByIdAndDelete(id);
  }

}
