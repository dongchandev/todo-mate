package com.todo.mate.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.mate.server.application.CreateTodoUseCase;
import com.todo.mate.server.controller.TodoController;
import com.todo.mate.server.controller.request.CreateTodoRequest;
import com.todo.mate.server.domain.todo.TodoId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
@Import(TodoControllerTest.StubConfig.class)
class TodoControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @TestConfiguration
    static class StubConfig {
        @Bean
        CreateTodoUseCase createTodoUseCase() {
            var mock = org.mockito.Mockito.mock(CreateTodoUseCase.class);
            org.mockito.Mockito.when(mock.handle(any()))
                    .thenReturn(TodoId.of(1L));
            return mock;
        }
    }

    @Test
    void todo_생성_API는_TodoId_JSON을_반환한다() throws Exception {
        // given
        var request = new CreateTodoRequest("TDD 공부", LocalDate.now().plusDays(1));

        // when + then
        mockMvc.perform(post("/todos")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
//                .andExpect(content().json("{\"value\":1}"));
    }
}
