package se.newton.tash.restapi.dbseeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.newton.tash.restapi.model.Assignment;
import se.newton.tash.restapi.model.TashUser;
import se.newton.tash.restapi.model.WorkOrder;
import se.newton.tash.restapi.service.AssignmentService;
import se.newton.tash.restapi.service.TashUserService;
import se.newton.tash.restapi.service.WorkOrderService;

import java.util.List;
import java.util.Random;

@Component
public class AssignmentSeeder {
  @Autowired
  AssignmentService assignmentService;

  @Autowired
  WorkOrderService workOrderService;

  @Autowired
  TashUserService tashUserService;

  public void seed() {
    Assignment.AssignmentBuilder b = Assignment.builder().id(0L);
    List<WorkOrder> workOrders = workOrderService.getAllWorkOrders();
    List<TashUser> tashUsers = tashUserService.fetchAllUsers();

    for (TashUser user : tashUsers) {
      WorkOrder wo = getRandomFromList(workOrders);
      Assignment assignment = b
          .title(String.format("%s %s should do %s", user.getFirstName(), user.getLastName(), wo.getTitle()))
          .description("Assignment based on work order: " + wo.getTitle())
          .users(user)
          .workOrder(wo)
          .build();
      assignmentService.createNewAssignment(assignment);
    }
  }

  // Random isn't great for seed data, but it will do for now.
  private <T> T getRandomFromList(List<T> list) {
    if (list.size() == 0) {
      return null;
    }
    return list.get(new Random().nextInt(list.size()));
  }
}