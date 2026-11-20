package be.vdab.startrek.bestellingen;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class BestellingRepository {
    private final JdbcClient jdbcClient;

    BestellingRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    List<Bestelling> findByWerknemerId(long werknemerId) {
        return jdbcClient.sql("""
                select id, werknemerId, omschrijving, bedrag, moment
                from bestellingen
                where werknemerId = ?
                order by id
                """)
                .param(werknemerId)
                .query(Bestelling.class)
                .list();
    }
}
