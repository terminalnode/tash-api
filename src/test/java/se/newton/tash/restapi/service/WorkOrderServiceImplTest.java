package se.newton.tash.restapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import se.newton.tash.restapi.model.WorkOrder;
import se.newton.tash.restapi.repository.WorkOrderRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class WorkOrderServiceImplTest {

    @InjectMocks
    WorkOrderServiceImpl workOrderService;

    @Mock
    WorkOrderRepository workOrderRepository;

    WorkOrder testWorkOrder1, testWorkOrder2, testWorkOrder3;
    List<WorkOrder> testListOfAllWorkOrders;

    @BeforeEach
    public void setUp()  {
      WorkOrder.WorkOrderBuilder workOrderBuilder = WorkOrder.builder()
          .longitude(33.6773)
          .latitude(-106.4754)
          .createdAt(new Date(1L))
          .completedAt(new Date(2L));



      testWorkOrder1 = workOrderBuilder.id(1L).title("TestWorkOrder1").description("The description of work order 1.").build();
      testWorkOrder2 = workOrderBuilder.id(2L).title("TestWorkOrder2").description("The description of work order 2.").build();
      testWorkOrder3 = workOrderBuilder.id(3L).title("TestWorkOrder3").description("The description of work order 3.").build();

      testListOfAllWorkOrders = new ArrayList<>();
      testListOfAllWorkOrders.add(testWorkOrder1);
      testListOfAllWorkOrders.add(testWorkOrder2);
      testListOfAllWorkOrders.add(testWorkOrder3);


      when(workOrderRepository.findAll()).thenReturn(testListOfAllWorkOrders);
      when(workOrderRepository.findById(1L)).thenReturn(Optional.of(testWorkOrder1));
      when(workOrderRepository.findById(2L)).thenReturn(Optional.of(testWorkOrder2));
      when(workOrderRepository.findById(3L)).thenReturn(Optional.of(testWorkOrder3));
      when(workOrderRepository.existsById(3L)).thenReturn(true);



    }


    @Test
    public void testGetAllWorkOrders() {
      List<WorkOrder> testResults = workOrderService.getAllWorkOrders();
      Assertions.assertEquals(3, testResults.size());
      assertThat(testResults)
          .contains(testWorkOrder1)
          .contains(testWorkOrder2)
          .contains(testWorkOrder3);
    }

    @Test
    public void testGetWorkOrderById1() {
      WorkOrder testResult = workOrderService.getWorkOrderById(1L);

      Assertions.assertEquals(testWorkOrder1, testResult);

    }

    @Test
    public void testGetWorkOrderById2() {
      WorkOrder testResult = workOrderService.getWorkOrderById(2L);

      Assertions.assertEquals(testWorkOrder2, testResult);

    }

    @Test
    public void testCreateNewWorkOrder() {

      WorkOrder.WorkOrderBuilder workOrderBuilder = WorkOrder.builder()
          .id(-1L)
          .title("TestWorkOrder4")
          .description("A new test work order created to be saved in a test.")
          .latitude(33.6773)
          .longitude(-106.4754)
          .createdAt(new Date(4L))
          .completedAt(new Date(5L));

      WorkOrder testWorkOrder4 = workOrderBuilder.build();
      WorkOrder notTestWorkOrder4 = workOrderBuilder.build();

      workOrderService.createNewWorkOrder(testWorkOrder4);

      //Confirm that one (and only one) new workOrder was created
      //and save the argument used to save it.
      ArgumentCaptor<WorkOrder> workOrderCaptor = ArgumentCaptor.forClass(WorkOrder.class);
      verify(workOrderRepository, times(1))
          .save(workOrderCaptor.capture());
      WorkOrder savedWorkOrder = workOrderCaptor.getValue();

      // Verify that work order was saved with identical information in all fields except for ID.
      // Any ID >= 0 is accepted.
      assertThat(savedWorkOrder).isNotEqualTo(notTestWorkOrder4);
      assertThat(savedWorkOrder.getId()).isGreaterThanOrEqualTo(0);
      notTestWorkOrder4.setId(savedWorkOrder.getId());
      assertThat(savedWorkOrder).isEqualTo(notTestWorkOrder4);

    }

    @Test
    public void testUpdateExistingWorkOrderWithValidID() {

      WorkOrder.WorkOrderBuilder workOrderBuilder = WorkOrder.builder()
          .id(3L)
          .title("NewTestWorkOrder3")
          .description("An updated version of the  previous work order.")
          .latitude(33.6773)
          .longitude(-106.4754)
          .createdAt(new Date(4L))
          .completedAt(new Date(5L));

      WorkOrder workOrderUpdater = workOrderBuilder.build();

      workOrderService.updateExistingWorkOrder(workOrderUpdater);

      //Verify that the update function was called once, and save the argument used.
      ArgumentCaptor<WorkOrder> workOrderCaptor = ArgumentCaptor.forClass(WorkOrder.class);
      verify(workOrderRepository, times(1))
          .save(workOrderCaptor.capture());
      WorkOrder updatedWorkOrder = workOrderCaptor.getValue();

      // Verify that the argument used to update is equal to the starting data.
      assertThat(workOrderUpdater).isEqualTo(updatedWorkOrder);

    }

    @Test
    public void testUpdateExistingWorkOrderWithInvalidID() {

      WorkOrder.WorkOrderBuilder workOrderBuilder = WorkOrder.builder()
          .id(-1337L)
          .title("NewTestWorkOrder3")
          .description("An updated version of the  previous work order.")
          .latitude(33.6773)
          .longitude(-106.4754)
          .createdAt(new Date(4L))
          .completedAt(new Date(5L));

      WorkOrder workOrderUpdater = workOrderBuilder.build();
      Assertions.assertThrows(
          IllegalArgumentException.class,
          () -> workOrderService.updateExistingWorkOrder(workOrderUpdater)
      );
    }

    @Test
    public void testDeleteWorkOrderWithValidID() {
      // Try deleting testWorkOrder1 by id
      workOrderService.deleteWorkOrderById(testWorkOrder1.getId());

      ArgumentCaptor<WorkOrder> workOrderCaptor = ArgumentCaptor.forClass(WorkOrder.class);
      verify(workOrderRepository, times(1))
          .delete(workOrderCaptor.capture());
      WorkOrder deletedWorkOrder = workOrderCaptor.getValue();

      // Confirm that the right work order was deleted
      assertThat(deletedWorkOrder).isEqualTo(testWorkOrder1);


    }

    @Test
    public void testDeleteWorkOrderWithInvalidId() {
      Assertions.assertThrows(
          IllegalArgumentException.class,
          () -> workOrderService.deleteWorkOrderById(-1L)
      );
    }
}
