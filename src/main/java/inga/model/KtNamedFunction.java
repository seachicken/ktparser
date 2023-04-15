package inga.model;

import com.intellij.openapi.util.TextRange;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class KtNamedFunction extends PsiElement {
    private String name;
    private String fqName;

    public KtNamedFunction(String type, int textOffset, TextRange textRange, List<PsiElement> children,
                           String name, String fqName) {
        super(type, textOffset, textRange, children);
        this.name = name;
        this.fqName = fqName;
    }
}
