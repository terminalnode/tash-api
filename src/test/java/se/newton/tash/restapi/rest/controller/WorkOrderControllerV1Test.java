package se.newton.tash.restapi.rest.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import se.newton.tash.restapi.model.WorkOrder;
import se.newton.tash.restapi.service.WorkOrderServiceImpl;

import java.util.Date;

import static org.mockito.Mockito.*;


@SpringBootTest
public class WorkOrderControllerV1Test {

  @InjectMocks
  WorkOrderControllerV1 workOrderController;

  @Mock
  WorkOrderServiceImpl workOrderService;

  WorkOrder.WorkOrderBuilder newWorkOrderBuilder;

  @BeforeEach
  public void setUp() {
    newWorkOrderBuilder = WorkOrder.builder()
        .id(-1L)
        .title("Test Work Order")
        .description("Test Description of test work order.")
        .longitude(0.1)
        .latitude(0.9)
        .createdAt(new Date(1L))
        .completedAt(new Date(2L));

  }

  @Test
  public void testGetAllWorkOrders() {
    workOrderController.getAllWorkOrders();
    verify(workOrderService, times(1))
        .getAllWorkOrders();
  }

  @Test
  public void testGetWorkOrderById() {
    workOrderController.getWorkOrderById(-1337L);
    verify(workOrderService, times(1))
        .getWorkOrderById(-1337L);
  }

  @Test
  public void testCreateNewUser() {
    WorkOrder newWorkOrder  = newWorkOrderBuilder.build();
    workOrderController.createNewWorkOrder(newWorkOrder);
    verify(workOrderService, times(1))
        .createNewWorkOrder(newWorkOrder);
  }

  @Test
  public void testUpdateExistingUse() {
    WorkOrder newWorkOrder = newWorkOrderBuilder.id(666L).build();
    workOrderController.updateExistingWorkOrder(newWorkOrder);
    verify(workOrderService, times(1))
        .updateExistingWorkOrder(newWorkOrder);
  }

  @Test
  public void testDeleteUserById() {
    workOrderController.deleteWorkOrderById(616L);
    verify(workOrderService, times(1))
        .deleteWorkOrderById(616L);
  }







}
