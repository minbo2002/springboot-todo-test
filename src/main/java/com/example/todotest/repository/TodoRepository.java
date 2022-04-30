package com.example.todotest.repository;

import com.example.todotest.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

//  @Repository   // Repository 용도 : DB랑 Data를 주고받기위한 인터페이스를 정의한 부분
//  SimpleJpaRepository<T, ID> 클래스에 이미 @Repository 정의되어있어서 굳이 또 정의 안해도됨

public interface TodoRepository extends JpaRepository<Todo, Long> {
    // JpaRepository <DB와 연결될 Entity 클래스이름, PK의 type>


}
