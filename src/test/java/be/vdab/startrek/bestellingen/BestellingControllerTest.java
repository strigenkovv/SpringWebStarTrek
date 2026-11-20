package be.vdab.startrek.bestellingen;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BestellingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcClient jdbcClient;

    @Test
    void createVermindertBudgetEnVoegtBestellingToe() throws Exception {
        long werknemerId = jdbcClient.sql("""
                select id from werknemers
                where budget >= 1
                limit 1
                """)
                .query(Long.class)
                .single();

        var budgetVoor = jdbcClient.sql("""
                select budget from werknemers where id = ?
                """)
                .param(werknemerId)
                .query(java.math.BigDecimal.class)
                .single();

        mockMvc.perform(post("/werknemers/{id}/bestellingen", werknemerId)
                        .contentType("application/json")
                        .content("""
                                {
                                    "omschrijving": "Testbestelling",
                                    "bedrag": 1
                                }
                                """))
                .andExpect(status().isOk());

        var budgetNa = jdbcClient.sql("""
                select budget from werknemers where id = ?
                """)
                .param(werknemerId)
                .query(java.math.BigDecimal.class)
                .single();

        assertThat(budgetNa)
                .isEqualByComparingTo(budgetVoor.subtract(
                        java.math.BigDecimal.ONE));

        var aantal = jdbcClient.sql("""
                select count(*) from bestellingen
                where werknemerId = ?
                and omschrijving = 'Testbestelling'
                """)
                .param(werknemerId)
                .query(Long.class)
                .single();

        assertThat(aantal).isGreaterThan(0);
    }

    @Test
    void createMetOnvoldoendeBudgetGeeftConflict() throws Exception {
        long werknemerId = jdbcClient.sql("""
                select id from werknemers
                limit 1
                """)
                .query(Long.class)
                .single();

        var aantalVoor = jdbcClient.sql("""
                select count(*) from bestellingen
                where werknemerId = ?
                """)
                .param(werknemerId)
                .query(Long.class)
                .single();

        var budgetVoor = jdbcClient.sql("""
                select budget from werknemers where id = ?
                """)
                .param(werknemerId)
                .query(java.math.BigDecimal.class)
                .single();

        var teHoogBedrag = budgetVoor.add(java.math.BigDecimal.ONE);

        mockMvc.perform(post("/werknemers/{id}/bestellingen", werknemerId)
                        .contentType("application/json")
                        .content("""
                                {
                                    "omschrijving": "Te dure bestelling",
                                    "bedrag": %s
                                }
                                """.formatted(teHoogBedrag)))
                .andExpect(status().isConflict());

        var budgetNa = jdbcClient.sql("""
                select budget from werknemers where id = ?
                """)
                .param(werknemerId)
                .query(java.math.BigDecimal.class)
                .single();

        assertThat(budgetNa).isEqualByComparingTo(budgetVoor);

        var aantalNa = jdbcClient.sql("""
                select count(*) from bestellingen
                where werknemerId = ?
                """)
                .param(werknemerId)
                .query(Long.class)
                .single();

        assertThat(aantalNa).isEqualTo(aantalVoor);
    }

    @Test
    void createVoorOnbestaandeWerknemerGeeftNotFound() throws Exception {
        long werknemerId = -1;

        mockMvc.perform(post("/werknemers/{id}/bestellingen", werknemerId)
                        .contentType("application/json")
                        .content("""
                                {
                                    "omschrijving": "Nieuwe computer",
                                    "bedrag": 100
                                }
                                """))
                .andExpect(status().isNotFound());
    }
}
