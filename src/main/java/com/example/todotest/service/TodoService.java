package com.example.todotest.service;

import com.example.todotest.model.Todo;
import com.example.todotest.dto.TodoRequest;
import com.example.todotest.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoService {  // Service 용도 : 우리가 구현할 구체적인 기능을 포함.

    private final TodoRepository todoRepository;


    public Todo add(TodoRequest request) {

        Todo todo = new Todo();

        todo.setTitle(request.getTitle());
        todo.setOrder(request.getOrder());
        todo.setComplted(request.getCompleted());

    //  TodoEntity entity = this.todoRepository.save(todo);

        return todoRepository.save(todo);   // todoRepository로 data값들이 입력된다.
    }

    public Todo searchById(Long id) {

        return todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Todo> searchAll() {
        return todoRepository.findAll() ;
    }

    public Todo updateById(Long id, TodoRequest request) {

        Todo todo = this.searchById(id);
                            //  this.searchById(id)  :  위에 있는 public TodoEntity searchById(Long id) 클래스임을 가르킴
        updateTodoEntity(request, todo);

        return this.todoRepository.save(todo);  // .save() 메서드는 실무에서 잘 안씀
                                                      // 더티채킹(변경감지) , merge 하지마라
    }

    private void updateTodoEntity(TodoRequest request, Todo todo) {

        if(request.getTitle() != null) {
            todo.setTitle(request.getTitle());    // .set 만 해도 자동으로 DB에 반영이됨 (변경감지)
                                                        // JPA의 특징임.
        }
        if(request.getOrder() != null) {
            todo.setOrder(request.getOrder());
        }
        if(request.getCompleted() != null) {
            todo.setComplted(request.getCompleted());
        }
    }

    public void deleteById(Long id) {
        this.todoRepository.deleteById(id);
    }

    public void deleteAll() {
        this.todoRepository.deleteAll();
    }
}
