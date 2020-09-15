package se.newton.tash.restapi.service;

import se.newton.tash.restapi.model.WorkOrder;

import java.util.List;

public interface WorkOrderService {

  public List<WorkOrder> getAllWorkOrders();
  public WorkOrder getWorkOrderById(long id);
  public WorkOrder createNewWorkOrder(WorkOrder workOrder);
  public WorkOrder updateExistingWorkOrder(WorkOrder workOrder);
  public WorkOrder deleteWorkOrderById(long id);


}
