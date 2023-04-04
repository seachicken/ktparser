package inga.model;

import com.intellij.openapi.util.TextRange;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class KtFile extends PsiElement {
    private String packageName;

    public KtFile(String type, int textOffset, TextRange textRange, List<PsiElement> children,
                  String packageName) {
        super(type, textOffset, textRange, children);
        this.packageName = packageName;
    }
}
