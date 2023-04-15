package inga;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.util.Disposer;
import inga.model.KtFile;
import inga.model.KtNamedFunction;
import inga.model.KtProperty;
import inga.model.PsiElement;
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.config.CompilerConfiguration;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Parser implements AutoCloseable {
    private final KotlinCoreEnvironment environment;
    private final Disposable disposable;

    public Parser() {
        var configuration = new CompilerConfiguration();
        disposable = Disposer.newDisposable();
        environment = KotlinCoreEnvironment.createForProduction(disposable, configuration, EnvironmentConfigFiles.JVM_CONFIG_FILES);
    }

    public PsiElement parse(Path path) {
        environment.addKotlinSourceRoots(List.of(path.toFile()));
        var element = environment.getSourceFiles().stream()
                .filter(f -> f.getVirtualFilePath().equals(path.toString()))
                .findFirst()
                .orElseThrow()
                .getOriginalElement();
        return parse(element);
    }

    private PsiElement parse(com.intellij.psi.PsiElement element) {
        if (element instanceof org.jetbrains.kotlin.psi.KtFile file) {
            return new KtFile(
                    element.getNode().getElementType().toString(),
                    element.getTextOffset(),
                    element.getTextRange(),
                    Arrays.stream(element.getChildren()).map(this::parse).toList(),
                    file.getPackageFqName().asString());
        } else if (element instanceof org.jetbrains.kotlin.psi.KtProperty property) {
            return new KtProperty(
                    element.getNode().getElementType().toString(),
                    element.getTextOffset(),
                    element.getTextRange(),
                    Arrays.stream(element.getChildren()).map(this::parse).toList(),
                    property.getName(),
                    property.getFqName().asString());
        } else if (element instanceof org.jetbrains.kotlin.psi.KtNamedFunction namedFunction) {
            return new KtNamedFunction(
                    element.getNode().getElementType().toString(),
                    element.getTextOffset(),
                    element.getTextRange(),
                    Arrays.stream(element.getChildren()).map(this::parse).toList(),
                    namedFunction.getName(),
                    namedFunction.getFqName().asString());
        } else {
            return new PsiElement(
                    element.getNode().getElementType().toString(),
                    element.getTextOffset(),
                    element.getTextRange(),
                    Arrays.stream(element.getChildren()).map(this::parse).toList());
        }
    }

    @Override
    public void close() {
        disposable.dispose();
    }
}
