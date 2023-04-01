package inga;

import com.intellij.openapi.util.TextRange;

import java.util.List;

public record PsiElement(
        String type,
        int textOffset,
        TextRange textRange,
        List<PsiElement> children
) {}
