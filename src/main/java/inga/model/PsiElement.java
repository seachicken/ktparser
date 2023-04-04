package inga.model;

import com.intellij.openapi.util.TextRange;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PsiElement {
    private String type;
    private int textOffset;
    private TextRange textRange;
    private List<PsiElement> children;
}
