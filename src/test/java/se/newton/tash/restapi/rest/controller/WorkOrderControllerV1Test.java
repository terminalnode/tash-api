package se.newton.tash.restapi.rest.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import se.newton.tash.restapi.model.WorkOrder;
import se.newton.tash.restapi.repository.WorkOrderRepository;
import se.newton.tash.restapi.service.WorkOrderServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@SpringBootTest
public class WorkOrderControllerV1Test {

  @InjectMocks
  WorkOrderControllerV1 workOrderController;

  @Mock
  WorkOrderServiceImpl workOrderService;

  @Test
  public void testGetAllWorkOrders() {
    workOrderController.getAllWorkOrders();
    verify(workOrderService, times(1))
        .getAllWorkOrders();
  }

}
