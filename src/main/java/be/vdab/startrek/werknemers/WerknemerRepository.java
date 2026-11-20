package be.vdab.startrek.werknemers;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
class WerknemerRepository {
    private final JdbcClient jdbcClient;

    WerknemerRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    List<Werknemer> findAll() {
        return jdbcClient.sql("""
                select id, voornaam, familienaam, budget
                from werknemers
                order by voornaam
                """)
                .query(Werknemer.class)
                .list();
    }

    Optional<Werknemer> findById(long id) {
        return jdbcClient.sql("""
                select id, voornaam, familienaam, budget
                from werknemers
                where id = ?
                """)
                .param(id)
                .query(Werknemer.class)
                .optional();
    }
}
