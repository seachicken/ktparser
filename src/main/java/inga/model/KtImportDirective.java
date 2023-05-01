package inga.model;

import com.intellij.openapi.util.TextRange;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class KtImportDirective extends PsiElement {
    private String fqName;

    public KtImportDirective(String type, int textOffset, TextRange textRange, List<PsiElement> children,
                             String fqName) {
        super(type, textOffset, textRange, children);
        this.fqName = fqName;
    }
}
