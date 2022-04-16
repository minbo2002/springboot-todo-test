package com.example.todotest.service;

import com.example.todotest.model.TodoEntity;
import com.example.todotest.model.TodoRequest;
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


    public TodoEntity add(TodoRequest request) {

        TodoEntity todoEntity = new TodoEntity();

        todoEntity.setTitle(request.getTitle());
        todoEntity.setOrder(request.getOrder());
        todoEntity.setComplted(request.getCompleted());

    //  TodoEntity entity = this.todoRepository.save(todoEntity);

        return todoRepository.save(todoEntity);   // todoRepository로 data값들이 입력된다.
    }

    public TodoEntity searchById(Long id) {

        return todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<TodoEntity> searchAll() {
        return todoRepository.findAll() ;
    }

    public TodoEntity updateById(Long id, TodoRequest request) {

        TodoEntity todoEntity = this.searchById(id);
                            //  this.searchById(id)  :  위에 있는 public TodoEntity searchById(Long id) 클래스임을 가르킴
        updateTodoEntity(request, todoEntity);

        return this.todoRepository.save(todoEntity);  // .save() 메서드는 실무에서 잘 안씀
                                                      // 더티채킹(변경감지) , merge 하지마라
    }

    private void updateTodoEntity(TodoRequest request, TodoEntity todoEntity) {

        if(request.getTitle() != null) {
            todoEntity.setTitle(request.getTitle());    // .set 만 해도 자동으로 DB에 반영이됨 (변경감지)
                                                        // JPA의 특징임.
        }
        if(request.getOrder() != null) {
            todoEntity.setOrder(request.getOrder());
        }
        if(request.getCompleted() != null) {
            todoEntity.setComplted(request.getCompleted());
        }
    }

    public void deleteById(Long id) {
        this.todoRepository.deleteById(id);
    }

    public void deleteAll() {
        this.todoRepository.deleteAll();
    }
}
