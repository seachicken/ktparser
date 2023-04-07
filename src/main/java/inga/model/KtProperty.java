package inga.model;

import com.intellij.openapi.util.TextRange;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class KtProperty extends PsiElement {
    private String name;

    public KtProperty(String type, int textOffset, TextRange textRange, List<PsiElement> children,
                      String name) {
        super(type, textOffset, textRange, children);
        this.name = name;
    }
}
