package se.newton.tash.restapi.dbseeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.newton.tash.restapi.model.TashUser;
import se.newton.tash.restapi.service.TashUserService;

@Component
public class TashUserSeeder {
  @Autowired
  TashUserService userService;

  public void seed() {
    userService.createNewUser(userGenerator("Aesop", "Rock", true));
    userService.createNewUser(userGenerator("Britney", "Spears", false));
    userService.createNewUser(userGenerator("Björk", "Guðmundsdóttir", false));
    userService.createNewUser(userGenerator("Marilyn", "Manson", false));
    userService.createNewUser(userGenerator("Taylor", "Swift", false));
  }

  private String urlFriendly(String s) {
    return s.toLowerCase()
        .replaceAll("[öó]", "o")
        .replaceAll("[åä]", "a")
        .replaceAll("ð", "d");
  }
  
  private TashUser userGenerator(String firstName, String lastName, boolean isAdmin) {
    String urlFirstName = urlFriendly(firstName);
    String urlLastName = urlFriendly(lastName);

    double longitude = (double) firstName.length() / 10;
    double latitude = (double) lastName.length() / 10;

    return TashUser.builder()
        .id(0L).admin(isAdmin)
        .longitude(longitude).latitude(latitude)
        .firstName(firstName).lastName(lastName)
        .password(urlFirstName)
        .email(String.format("%s.%s@mycooldomain.xyz", urlFirstName, urlLastName))
        .avatarUrl(String.format("https://www.mycooldomain.xyz/%s_%s.png", urlFirstName, urlLastName))
        .build();
  }
}
