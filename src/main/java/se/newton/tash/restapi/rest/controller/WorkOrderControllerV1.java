package se.newton.tash.restapi.rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.newton.tash.restapi.model.WorkOrder;
import se.newton.tash.restapi.service.WorkOrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/work_orders")
public class WorkOrderControllerV1 {

  @Autowired
  WorkOrderService workOrderService;

  @GetMapping
  public List<WorkOrder> getAllWorkOrders() {
    return workOrderService.getAllWorkOrders();
  }

  @GetMapping("/{id}")
  public WorkOrder getWorkOrderById(@PathVariable long id) {
    return workOrderService.getWorkOrderById(id);
  }

  @PostMapping
  public WorkOrder createNewWorkOrder(@RequestBody WorkOrder workOrder) {
    workOrder.setId(0);
    System.out.println(workOrder.toString());
    WorkOrder newWorkOrder = workOrderService.createNewWorkOrder(workOrder);
    return newWorkOrder;

  }

  @PutMapping
  public WorkOrder updateExistingWorkOrder(@RequestBody WorkOrder workOrder) {
      WorkOrder updatedWorkOrder = workOrderService.updateExistingWorkOrder(workOrder);
      return updatedWorkOrder;

  }

  @DeleteMapping("/{id}")
  public WorkOrder deleteWorkOrderById(@PathVariable long id) {
    WorkOrder workOrder = workOrderService.deleteWorkOrderById(id);
    return workOrder;
  }


}
