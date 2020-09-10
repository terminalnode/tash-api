package se.newton.tash.restapi.rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.newton.tash.restapi.model.WorkOrder;
import se.newton.tash.restapi.repository.WorkOrderRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/work_orders")
public class WorkOrderControllerV1 {

  @Autowired
  WorkOrderRepository workOrderRepository;

  @GetMapping
  public List<WorkOrder> getAllWorkOrders() {
    return workOrderRepository.findAll();
  }

  @GetMapping("/{id}")
  public WorkOrder getWorkOrderById(@PathVariable long id) {
    return workOrderRepository.findById(id).get();
  }

  @PostMapping
  public WorkOrder createNewWorkOrder(@RequestBody WorkOrder workOrder) {
    workOrder.setId(0);
    System.out.println(workOrder.toString());
    WorkOrder newWorkOrder = workOrderRepository.save(workOrder);
    return newWorkOrder;

  }

  @PutMapping
  public WorkOrder updateExistingWorkOrder(@RequestBody WorkOrder workOrder) {
    if (workOrderRepository.existsById(workOrder.getId())) {
      WorkOrder updatedWorkOrder = workOrderRepository.save(workOrder);
      return updatedWorkOrder;
    }
    else {
      throw new IllegalArgumentException("The ID specified does not exist in the workOrder table.");
    }

  }

  @DeleteMapping("/{id}")
  public WorkOrder deleteWorkOrderById(@PathVariable long id) {
    WorkOrder deletedWorkOrder = workOrderRepository.findById(id).get();
    workOrderRepository.deleteById(id);
    return deletedWorkOrder;
  }


}
