package co.edu.uan.software.tasky.payroll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import co.edu.uan.software.tasky.entities.TagEntity;
import co.edu.uan.software.tasky.repositories.TagRepository;

public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(TagRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new TagEntity("Tag 1", "blue")));
            log.info("Preloading " + repository.save(new TagEntity("Tag 1", "yellow")));
        };
    }
}
