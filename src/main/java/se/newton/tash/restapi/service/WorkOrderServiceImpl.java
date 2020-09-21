package se.newton.tash.restapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.newton.tash.restapi.model.WorkOrder;
import se.newton.tash.restapi.repository.WorkOrderRepository;
import se.newton.tash.restapi.rest.exceptions.workorderexceptions.WorkOrderIdNotFoundException;

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
    if (workOrderRepository.existsById(id)) {
      return workOrderRepository.findById(id).get();
    }
    else {
      throw new WorkOrderIdNotFoundException(
          String.format("Work order with id %d not found",
              id)
      );
    }
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
      throw new WorkOrderIdNotFoundException(
          String.format("Work order with id %d not found",
              workOrder.getId())
      );
    }

  }

  public WorkOrder deleteWorkOrderById(long id) {
    Optional<WorkOrder> workOrder = workOrderRepository.findById(id);

    if (workOrder.isPresent()) {
      workOrderRepository.delete(workOrder.get());
      return workOrder.get();
    } else {
      throw new WorkOrderIdNotFoundException(
          String.format("Work order with id %d not found",
              id)
      );
    }
  }

}
