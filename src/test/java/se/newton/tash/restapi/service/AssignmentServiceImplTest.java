package se.newton.tash.restapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import se.newton.tash.restapi.model.Assignment;
import se.newton.tash.restapi.repository.AssignmentRepository;
import se.newton.tash.restapi.rest.exceptions.assignmentexceptions.AssignmentIdNotFoundException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;


@SpringBootTest
public class AssignmentServiceImplTest {

    @InjectMocks
    AssignmentServiceImpl assignmentService;

    @Mock
    AssignmentRepository assignmentRepository;

    Assignment testAssignment1, testAssignment2, testAssignment3;
    List<Assignment> testListOfAllAssignments;

    @BeforeEach
    public void setUp()  {
        Assignment.AssignmentBuilder assignmentBuilder = Assignment.builder()
                .confirmedAt(new Timestamp(1L))
                .completedAt(new Timestamp(2L));



        testAssignment1 = assignmentBuilder.id(1L).title("TestAssignment1").description("The assignment").build();
        testAssignment2 = assignmentBuilder.id(2L).title("TestAssignment2").description("The assignment").build();
        testAssignment3 = assignmentBuilder.id(3L).title("TestAssignment3").description("The assignment").build();

        testListOfAllAssignments = new ArrayList<>();
        testListOfAllAssignments.add(testAssignment1);
        testListOfAllAssignments.add(testAssignment2);
        testListOfAllAssignments.add(testAssignment3);


        when(assignmentRepository.findAll()).thenReturn(testListOfAllAssignments);
        when(assignmentRepository.findById(1L)).thenReturn(Optional.of(testAssignment1));
        when(assignmentRepository.findById(2L)).thenReturn(Optional.of(testAssignment2));
        when(assignmentRepository.findById(3L)).thenReturn(Optional.of(testAssignment3));
        when(assignmentRepository.existsById(1L)).thenReturn(true);
        when(assignmentRepository.existsById(2L)).thenReturn(true);
        when(assignmentRepository.existsById(3L)).thenReturn(true);





    }

    @Test
    public void testGetAllAssignment() {
        List<Assignment> testResults = assignmentService.getAllAssignments();
        Assertions.assertEquals(3, testResults.size());
        assertThat(testResults)
                .contains(testAssignment1)
                .contains(testAssignment2)
                .contains(testAssignment3);
    }

    @Test
    public void testGetAssignmentById1() {
        Assignment testResult = assignmentService.getAssignmentById(1L);

        Assertions.assertEquals(testAssignment1, testResult);

    }

    @Test
    public void testGetAssignmentById2() {
        Assignment testResult = assignmentService.getAssignmentById(2L);

        Assertions.assertEquals(testAssignment2, testResult);

    }

    @Test
    public void testCreateNewAssignment() {

        Assignment.AssignmentBuilder assignmentBuilder = Assignment.builder()
                .id(-1L)
                .title("TestAssignment4")
                .description("A new test Assignment created to be saved in a test.")
                .confirmedAt(new Timestamp(4L))
                .completedAt(new Timestamp(5L));

        Assignment testAssignment4 = assignmentBuilder.build();
        Assignment notTestAssignment4 = assignmentBuilder.build();

        assignmentService.createNewAssignment(testAssignment4);

        ArgumentCaptor<Assignment> assignmentCaptor = ArgumentCaptor.forClass(Assignment.class);
        verify(assignmentRepository, times(1))
                .save(assignmentCaptor.capture());
        Assignment savedAssignment = assignmentCaptor.getValue();


        assertThat(savedAssignment).isNotEqualTo(notTestAssignment4);
        assertThat(savedAssignment.getId()).isGreaterThanOrEqualTo(0);
        notTestAssignment4.setId(savedAssignment.getId());
        assertThat(savedAssignment).isEqualTo(notTestAssignment4);

    }

    @Test
    public void testUpdateExistingAssignmentWithValidID() {

        Assignment.AssignmentBuilder assignmentBuilder = Assignment.builder()
                .id(3L)
                .title("NewTestWorkOrder3")
                .description("An updated version of the  previous work order.")
                .completedAt(new Timestamp(4L))
                .completedAt(new Timestamp(5L));

        Assignment assignmentUpdater = assignmentBuilder.build();

        assignmentService.updateExistingAssignment(assignmentUpdater);

        ArgumentCaptor<Assignment> assignmentCaptor = ArgumentCaptor.forClass(Assignment.class);
        verify(assignmentRepository, times(1))
                .save(assignmentCaptor.capture());
        Assignment updatedAssignment = assignmentCaptor.getValue();

        assertThat(assignmentUpdater).isEqualTo(updatedAssignment);

    }

    @Test
    public void testUpdateExistingAssignmentWithInvalidID() {

        Assignment.AssignmentBuilder assignmentBuilder = Assignment.builder()
                .id(-1337L)
                .title("NewTestWorkOrder3")
                .description("An updated version of the  previous work order.")
                .completedAt(new Timestamp(4L))
                .completedAt(new Timestamp(5L));

        Assignment assignmentUpdater = assignmentBuilder.build();
        Assertions.assertThrows(
                AssignmentIdNotFoundException.class,
                () -> assignmentService.updateExistingAssignment(assignmentUpdater)
        );
    }

    @Test
    public void testDeleteWorkOrderWithValidID() {
        assignmentService.deleteAssignmentById(testAssignment1.getId());

        ArgumentCaptor<Assignment> assignmentCaptor = ArgumentCaptor.forClass(Assignment.class);
        verify(assignmentRepository, times(1))
                .delete(assignmentCaptor.capture());
        Assignment deletedAssignment = assignmentCaptor.getValue();

        assertThat(deletedAssignment).isEqualTo(testAssignment1);


    }

    @Test
    public void testDeleteAssignmentWithInvalidId() {
        Assertions.assertThrows(
                AssignmentIdNotFoundException.class,
                () -> assignmentService.deleteAssignmentById(-1L)
        );
    }

}
