package se.newton.tash.restapi.dbseeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.newton.tash.restapi.model.Customer;
import se.newton.tash.restapi.model.WorkOrder;
import se.newton.tash.restapi.repository.CustomerRepository;
import se.newton.tash.restapi.service.CustomerService;
import se.newton.tash.restapi.service.WorkOrderService;

import java.util.Date;

@Component
public class WorkOrderSeeder {
  @Autowired
  WorkOrderService workOrderService;

  @Autowired
  CustomerService customerService;

  @Autowired
  CustomerRepository customerRepository;

  public void seed() {
    WorkOrder.WorkOrderBuilder b = WorkOrder.builder()
        .id(0L).createdAt(new Date()).latitude(0.1).longitude(0.5);

    for (Customer c : customerRepository.findAll()) {
      WorkOrder wo = b.title("Implementing something for " + c.getName())
          .description("This is a top secret mission, don't let them down!")
          .customer(c)
          .build();
      workOrderService.createNewWorkOrder(wo);
    }
  }
}
