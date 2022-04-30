package com.example.todotest.repository;

import com.example.todotest.dto.TodoRequest;
import com.example.todotest.model.Todo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    private Todo todo;

    @BeforeEach
    void setup() {
        todo = Todo.builder()
                .title("test_title")
                .order(1L)
                .complted(false)
                .build();
    }

    @Test
    @DisplayName("Todo 생성")
    void create() {
        // given
        Todo savedTodo = todoRepository.save(todo);
        // when
        // then
        assertThat(savedTodo.getTitle()).isEqualTo("test_title");
    }

    @Test
    @DisplayName("Todo 상세보기")
    void readOne() {
        Todo savedTodo = todoRepository.save(todo);
        Optional<Todo> byId = todoRepository.findById(savedTodo.getId());

        assertThat(byId).isNotNull();
    }

    @Test
    @DisplayName("Todo 전제조회")
    void readAll() {
//        Todo todo1 = Todo.builder()
//                .title("test_title2")
//                .order(1L)
//                .complted(false)
//                .build();
//
//        List<Todo> todoList = new ArrayList<>();
//        todoList.add(todo);
//        todoList.add(todo1);

        IntStream.rangeClosed(1,20).forEach(i -> {
                Todo todo1 = Todo.builder()
                .title("test_title2_"+i)
                .order(1L)
                .complted(false)
                .build();
            todoRepository.save(todo1);
        });

//        todoRepository.saveAll(todoList);

        List<Todo> all = todoRepository.findAll();
        log.info("all: {}", all);

        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(20);
    }

    @Test
    @DisplayName("Todo 수정")
    void updateById() {

        // given
        TodoRequest request = TodoRequest.builder()
                .title("new_title")
                .order(1L)
                .completed(true)
                .build();

        Todo savedTodo = todoRepository.save(todo);

        // when
        Todo findTodo = todoRepository.findById(savedTodo.getId()).get();
        findTodo.update(request);
        Todo updatedTodo = todoRepository.save(findTodo);

        // then
        assertThat(updatedTodo.getTitle()).isEqualTo(request.getTitle());

    }

    @Test
    @DisplayName("Todo 삭제")
    void deleteById() {

        Todo savedTodo = todoRepository.save(todo);

        todoRepository.deleteById(savedTodo.getId());

        log.info("savedTodo: {}", savedTodo);
        Optional<Todo> deletedTodo = todoRepository.findById(savedTodo.getId());

        assertThat(deletedTodo).isEmpty();

    }

    @Test
    @DisplayName("Todo 전체삭제")
    void deleteAll() {

        IntStream.rangeClosed(1,20).forEach(i -> {
            Todo todo1 = Todo.builder()
                    .title("test_title2_"+i)
                    .order(1L)
                    .complted(false)
                    .build();
            todoRepository.save(todo1);
        });

        todoRepository.deleteAll();
        List<Todo> all = todoRepository.findAll();

        assertThat(all).isEmpty();

    }

}