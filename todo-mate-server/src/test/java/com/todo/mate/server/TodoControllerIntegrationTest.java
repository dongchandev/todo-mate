package com.todo.mate.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.mate.server.application.CreateTodoCommand;
import com.todo.mate.server.application.CreateTodoUseCase;
import com.todo.mate.server.controller.request.CreateTodoRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        // given
        CreateTodoRequest request = new CreateTodoRequest("TDD 공부", LocalDate.now());

        // when + then
        mockMvc.perform(post("/todos")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.value").exists());
    }

    @Test
    void todo_toggle_API() throws Exception {
        Long id = createTodoUseCase.handle(CreateTodoCommand.of("공부하기", LocalDate.now()));
        mockMvc.perform(patch("/todos/{id}/toggle", id))
                .andExpect(status().isOk());
    }
}
