package inga;

import inga.model.KtFile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class ParserTest {
    private Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Parser();
    }

    @AfterEach
    void tearDown() {
        parser.close();
    }

    @Test
    void parseClass() {
        var actual = (KtFile) parser.parse(readFile("Class.kt"));
        assertThat(actual.getType()).isEqualTo("kotlin.FILE");
        assertThat(actual.getPackageName()).isEqualTo("fixtures");
    }

    private Path readFile(String path) {
        return Path.of(getClass().getClassLoader().getResource("fixtures/" + path).getFile());
    }
}