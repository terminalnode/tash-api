package se.newton.tash.restapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.newton.tash.restapi.model.WorkOrder;
import se.newton.tash.restapi.repository.WorkOrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkOrderServiceImpl implements WorkOrderService {

  @Autowired
  WorkOrderRepository workOrderRepository;

  public List<WorkOrder> getAllWorkOrders() {
    return workOrderRepository.findAll();
  }

  public WorkOrder getWorkOrderById(long id) {
    return workOrderRepository.findById(id).get();
  }

  public WorkOrder createNewWorkOrder(WorkOrder workOrder) {
    workOrder.setId(0);
    System.out.println(workOrder.toString());
    WorkOrder newWorkOrder = workOrderRepository.save(workOrder);
    return newWorkOrder;

  }

  public WorkOrder updateExistingWorkOrder(WorkOrder workOrder) {
    if (workOrderRepository.existsById(workOrder.getId())) {
      WorkOrder updatedWorkOrder = workOrderRepository.save(workOrder);
      return updatedWorkOrder;
    }
    else {
      throw new IllegalArgumentException("The ID specified does not exist in the workOrder table.");
    }

  }

  public WorkOrder deleteWorkOrderById(long id) {
    Optional<WorkOrder> workOrder = workOrderRepository.findById(id);

    if (workOrder.isPresent()) {
      workOrderRepository.delete(workOrder.get());
      return workOrder.get();
    } else {
      throw new IllegalArgumentException("The requested work order does not exist.");
    }
  }

}
