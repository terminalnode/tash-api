package se.newton.tash.restapi.rest.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import se.newton.tash.restapi.model.Assignment;
import se.newton.tash.restapi.service.AssignmentServiceImpl;

import java.sql.Timestamp;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@SpringBootTest
public class AssignmentControllerV1Test {

    @InjectMocks
    AssignmentControllerV1 assignmentController;

    @Mock
    AssignmentServiceImpl assignmentService;

    Assignment.AssignmentBuilder newAssignmentBuilder;

    @BeforeEach
    public void setUp() {
        newAssignmentBuilder = Assignment.builder()
                .id(-1L)
                .title("Test Assignment")
                .description("Test Description of Assignment.")
                .completedAt(new Timestamp(1L))
                .completedAt(new Timestamp(2L));

    }

    @Test
    public void testGetAllAssignments() {
        assignmentController.getAllAssignments();
        verify(assignmentService, times(1))
                .getAllAssignments();
    }

    @Test
    public void testGetAssignmentById() {
        assignmentController.getAssignmentById(-137L);
        verify(assignmentService, times(1))
                .getAssignmentById(-137L);
    }

    @Test
    public void testCreateNewUser() {
        Assignment newAssignment  = newAssignmentBuilder.build();
        assignmentController.createNewAssignment(newAssignment);
        verify(assignmentService, times(1))
                .createNewAssignment(newAssignment);
    }

    @Test
    public void testUpdateExistingAssignment() {
        Assignment newAssignment = newAssignmentBuilder.id(666L).build();
        assignmentController.updateExistingAssignment(newAssignment);
        verify(assignmentService, times(1))
                .updateExistingAssignment(newAssignment);
    }

    @Test
    public void testDeleteUserById() {
        assignmentController.deleteAssignmentById(616L);
        verify(assignmentService, times(1))
                .deleteAssignmentById(616L);
    }







}
