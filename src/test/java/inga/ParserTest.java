package inga;

import inga.model.KtFile;
import inga.model.KtNamedFunction;
import inga.model.PsiElement;
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
        var ktFile = (KtFile) parser.parse(readFile("Class.kt"));
        assertThat(ktFile.getType()).isEqualTo("kotlin.FILE");
        assertThat(ktFile.getPackageName()).isEqualTo("fixtures");

        var ktClass = findChild(ktFile, "CLASS");
        KtNamedFunction ktFun = findChild(ktClass.getChildren().get(0), "FUN");
        assertThat(ktFun.getName()).isEqualTo("method");
    }

    private <T extends PsiElement> T findChild(PsiElement element, String type) {
        return (T) element.getChildren().stream().filter(e -> e.getType().equals(type)).findFirst().get();
    }

    private Path readFile(String path) {
        return Path.of(getClass().getClassLoader().getResource("fixtures/" + path).getFile());
    }
}