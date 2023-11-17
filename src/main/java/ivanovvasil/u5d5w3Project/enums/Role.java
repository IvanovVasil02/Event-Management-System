package ivanovvasil.u5d5w3Project.enums;

import java.util.Random;

public enum Role {
  USER, MANAGER;

  public static Role getRandomRole() {
    Role[] roles = values();
    return roles[new Random().nextInt(0, roles.length)];
  }
}
