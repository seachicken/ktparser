package inga;

import inga.model.*;
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

        var importList = findChild(ktFile, "IMPORT_LIST");
        assertThat(importList.getChildren())
                .extracting("fqName")
                .containsExactly("fixtures.a.ClassA");

        var ktClass = findChild(ktFile, "CLASS");
        KtProperty ktProperty = findChild(ktClass.getChildren().get(0), "PROPERTY");
        assertThat(ktProperty.getName()).isEqualTo("field");
        assertThat(ktProperty.getFqName()).isEqualTo("fixtures.Class.field");

        KtNamedFunction ktFun = findChild(ktClass.getChildren().get(0), "FUN");
        assertThat(ktFun.getName()).isEqualTo("method");
        assertThat(ktFun.getFqName()).isEqualTo("fixtures.Class.method");
    }

    private <T extends PsiElement> T findChild(PsiElement element, String type) {
        return (T) element.getChildren().stream().filter(e -> e.getType().equals(type)).findFirst().get();
    }

    private Path readFile(String path) {
        return Path.of(getClass().getClassLoader().getResource("fixtures/" + path).getFile());
    }
}