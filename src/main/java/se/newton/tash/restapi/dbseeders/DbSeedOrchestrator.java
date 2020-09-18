package se.newton.tash.restapi.dbseeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DbSeedOrchestrator {
  @Autowired
  TashUserSeeder tashUserSeeder;

  /**
   * Method for triggering all other seeders in the right order.
   * @param event The ContextRefreshedEvent that this method listens for.
   */
  @EventListener
  public void seed(ContextRefreshedEvent event) {
    tashUserSeeder.seed();
    // TODO Add token seed
    // TODO Add customer seed
    // TODO Add work order seed
    // TODO Add assignment seed
  }
}
