package com.todo.mate.server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.mate.server.application.CreateTodoCommand;
import com.todo.mate.server.application.CreateTodoUseCase;
import com.todo.mate.server.controller.response.TodoResponse;
import com.todo.mate.server.controller.request.CreateTodoRequest;
import com.todo.mate.server.controller.request.UpdateTodoRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TodoControllerIntegrationTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired CreateTodoUseCase createTodoUseCase;

    @Test
    void todo_생성_API() throws Exception {
        CreateTodoRequest request = new CreateTodoRequest("TDD 공부", LocalDate.now(), "TDD로 숫자야구 만들기");

        mockMvc.perform(post("/todos")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void todo_toggle_API() throws Exception {
        TodoResponse res = createTodoUseCase.handle(CreateTodoCommand.of("공부하기", LocalDate.now()));
        mockMvc.perform(patch("/todos/{id}/toggle", res.id()))
                .andExpect(status().isOk());
    }

    @Test
    void todo_delete_API() throws Exception {
        TodoResponse res = createTodoUseCase.handle(CreateTodoCommand.of("공부하기", LocalDate.now()));
        mockMvc.perform(delete("/todos/{id}", res.id()))
                .andExpect(status().isOk());
    }

    @Test
    void todo_update_API() throws Exception {
        UpdateTodoRequest request = new UpdateTodoRequest("TDD 공부", "TDD로 숫자야구 만들기");

        TodoResponse res = createTodoUseCase.handle(CreateTodoCommand.of("공부하기", LocalDate.now()));
        mockMvc.perform(patch("/todos/{id}", res.id())
                        .contentType("application/json")
                        .accept("application/json")
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());
    }

    @Test
    void get_month_remaining_todo_API() throws Exception {
        TodoResponse res = createTodoUseCase.handle(CreateTodoCommand.of("공부하기", LocalDate.now()));
        mockMvc.perform(get("/todos/remaining?year={date}&month={month}", res.date().getYear(), res.date().getMonthValue()))
                .andExpect(status().isOk());
    }

    @Test
    void get_month_done_count_todo_API() throws Exception {
        TodoResponse res = createTodoUseCase.handle(CreateTodoCommand.of("공부하기", LocalDate.now()));
        mockMvc.perform(get("/todos/done-count?year={date}&month={month}", res.date().getYear(), res.date().getMonthValue()))
                .andExpect(status().isOk());
    }
}
