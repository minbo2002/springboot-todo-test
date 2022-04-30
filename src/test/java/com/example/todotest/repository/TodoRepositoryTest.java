package com.example.todotest.repository;

import com.example.todotest.model.Todo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

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
    void create() {
        // given
        Todo savedTodo = todoRepository.save(todo);
        // when
        // then
        assertThat(savedTodo.getTitle()).isEqualTo("test_title");
    }


}