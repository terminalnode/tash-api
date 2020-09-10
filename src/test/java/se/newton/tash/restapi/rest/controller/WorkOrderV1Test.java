package se.newton.tash.restapi.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.hibernate.jdbc.Work;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import se.newton.tash.restapi.model.User;
import se.newton.tash.restapi.model.WorkOrder;
import se.newton.tash.restapi.repository.UserRepository;
import se.newton.tash.restapi.repository.WorkOrderRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class WorkOrderV1Test {

  @InjectMocks
  WorkOrderControllerV1 workOrderController;

  @Mock
  WorkOrderRepository workOrderRepository;

  WorkOrder testWorkOrder1, testWorkOrder2, testWorkOrder3, testWorkOrder4;
  List<WorkOrder> testListOfAllWorkOrders;

  @BeforeEach
  public void setUp()  {
    testWorkOrder1 = new WorkOrder(1L, "TestWorkOrder1", "The description of work order 1.", 33.6773, -106.4754, new Date(1L), new Date(2L));
    testWorkOrder2 = new WorkOrder(2L, "TestWorkOrder2", "The description of work order 2.", 33.6773, -106.4754, new Date(2L), new Date(3L));
    testWorkOrder3 = new WorkOrder(3L, "TestWorkOrder3", "The description of work order 3.", 33.6773, -106.4754, new Date(3L), new Date(4L));

    testWorkOrder4 = new WorkOrder(4L, "TestWorkOrder4", "The description of work order 4, which is added to the list later possibly by magic.", 33.6773, -106.4754, new Date(4L), new Date(5L));

    testListOfAllWorkOrders = new ArrayList<>();
    testListOfAllWorkOrders.add(testWorkOrder1);
    testListOfAllWorkOrders.add(testWorkOrder2);
    testListOfAllWorkOrders.add(testWorkOrder3);


    when(workOrderRepository.findAll()).thenReturn(testListOfAllWorkOrders);
    when(workOrderRepository.findById(1L)).thenReturn(Optional.of(testWorkOrder1));
    when(workOrderRepository.findById(2L)).thenReturn(Optional.of(testWorkOrder2));
    when(workOrderRepository.findById(3L)).thenReturn(Optional.of(testWorkOrder3));



  }

  @Test
  public void testGetAllWorkOrders() {
    List<WorkOrder> testResults = workOrderController.getAllWorkOrders();
    Assertions.assertEquals(3, testResults.size());
    assertThat(testResults)
        .contains(testWorkOrder1)
        .contains(testWorkOrder2)
        .contains(testWorkOrder3);
  }

  @Test
  public void testGetWorkOrderById1() {
    WorkOrder testResult = workOrderController.getWorkOrderById(1L);

    Assertions.assertEquals(testWorkOrder1, testResult);

  }

  @Test
  public void testGetWorkOrderById2() {
    WorkOrder testResult = workOrderController.getWorkOrderById(2L);

    Assertions.assertEquals(testWorkOrder2, testResult);

  }

  @Test
  public void testCreateNewWorkOrder() {





  }


}
