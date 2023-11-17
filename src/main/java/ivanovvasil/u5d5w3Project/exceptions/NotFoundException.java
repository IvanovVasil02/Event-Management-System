package ivanovvasil.u5d5w3Project.exceptions;

public class NotFoundException extends RuntimeException {
  public NotFoundException(Long id) {
    super("There is no item with this id");
  }

  public NotFoundException(String message) {
    super(message);
  }


}
