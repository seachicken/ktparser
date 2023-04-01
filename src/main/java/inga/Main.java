package inga;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.config.CompilerConfiguration;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        var configuration = new CompilerConfiguration();
        var disposable = Disposer.newDisposable();
        var mapper = new ObjectMapper();
        try {
            var environment = KotlinCoreEnvironment.createForProduction(disposable, configuration, EnvironmentConfigFiles.JVM_CONFIG_FILES);

            while (scanner.hasNextLine()) {
                String path = scanner.nextLine();
                environment.addKotlinSourceRoots(List.of(Path.of(path).toFile()));
                var file = environment.getSourceFiles().stream()
                        .filter(f -> f.getVirtualFilePath().equals(path))
                        .findFirst()
                        .orElseThrow();
                var element = parse(file.getOriginalElement());
                String json = mapper.writeValueAsString(element);
                System.out.println(json);
            }
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        } finally {
            disposable.dispose();
        }
    }

    private static PsiElement parse(com.intellij.psi.PsiElement element) {
        return new PsiElement(
                element.getNode().getElementType().toString(),
                element.getTextOffset(),
                element.getTextRange(),
                Arrays.stream(element.getChildren()).map(Main::parse).toList());
    }
}
