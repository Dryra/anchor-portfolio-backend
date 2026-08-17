package net.dryra.anchorbackend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ReflectionApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listsFictionalCollections() throws Exception {
        mockMvc.perform(get("/api/v1/collections").header("Accept-Language", "en"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("focus"))
                .andExpect(jsonPath("$[0].title").value("Focus"));
    }

    @Test
    void returnsDeterministicDailyReflection() throws Exception {
        mockMvc.perform(get("/api/v1/reflections/today"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.collectionId").isNotEmpty())
                .andExpect(jsonPath("$.text").isNotEmpty());
    }

    @Test
    void returnsCentralizedNotFoundResponse() throws Exception {
        mockMvc.perform(get("/api/v1/collections/missing"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }
}
