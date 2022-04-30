package com.example.todotest.service;

import com.example.todotest.dto.TodoResponse;
import com.example.todotest.model.Todo;
import com.example.todotest.dto.TodoRequest;
import com.example.todotest.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock  // 외부 시스템에 의존하지않고 자체 테스트를 실행하기위해, 실제 DB와 연결해서 사용하지않기위해
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoServiceImpl todoService;

    private Todo todo;

    @BeforeEach
    void setup() {
        todo = Todo.builder()
                .id(1L)
                .title("test_title")
                .order(1L)
                .completed(false)
                .build();
    }

    @Test
    void add() {
        when(this.todoRepository.save(any(Todo.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        TodoRequest todoRequest = TodoRequest.builder()
                .title(todo.getTitle())
                .order(todo.getOrder())
                .completed(todo.getCompleted())
                .build();

        TodoResponse todoResponse = todoService.add(todoRequest);
        log.info("todoResponse: {}", todoResponse);

        assertThat(todoResponse).isNotNull();
        assertThat(todoResponse.getTitle()).isEqualTo(todoRequest.getTitle());

    }

    @Test
    public void searchById() {

        given(this.todoRepository.findById(1L))
                .willReturn(Optional.of(todo));

        TodoResponse response = this.todoService.searchById(1L);

        log.info("response: {}", response);

        assertThat(response.getTitle()).isEqualTo("test_title");
        assertThat(response.getOrder()).isEqualTo(1L);
    }

    @Test
    void searchAll() {
        // given - precondition or setup
        Todo todo1 = Todo.builder()
                .id(100L)
                .title("test_dsg")
                .order(1L)
                .completed(true)
                .build();

        given(todoRepository.findAll()).willReturn(List.of(todo, todo1));

        // when - action or the behaviour that we are going test
        List<TodoResponse> todoResponses = todoService.searchAll();
        log.info("todoResponses: {}", todoResponses);

        // then - verify the output
        assertThat(todoResponses).isNotNull();
        assertThat(todoResponses.size()).isEqualTo(2);
    }

    @Test
    public void updateById(){
        // given - precondition or setup
        given(todoRepository.findById(anyLong()))
                .willReturn(Optional.of(todo));
        given(todoRepository.save(todo)).willReturn(todo);      // updateById면 두 상황 모두 만들어져야돼!

        TodoRequest updateRequest = TodoRequest.builder()
                .title("kkk")
                .order(2L)
                .build();

        // when - action or the behaviour that we are going test
        TodoResponse todoResponse = todoService.updateById(todo.getId(), updateRequest);
        log.info("todoResponses: {}", todoResponse);
        // then - verify the output
        assertThat(todoResponse.getTitle()).isEqualTo(updateRequest.getTitle());
        assertThat(todoResponse.getOrder()).isEqualTo(updateRequest.getOrder());

    }

    @Test
    public void deleteById(){
        // given - precondition or setup
        Long todoId = 1L;
        willDoNothing().given(todoRepository).deleteById(todoId);

        // when - action or the behaviour that we are going test
        todoService.deleteById(todoId);
        // then - verify the output
        verify(todoRepository).deleteById(todoId);

    }
}