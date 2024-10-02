package io.github.llnancy.jingtian.javase.test;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineNode;

import java.io.File;
import java.io.IOException;

/**
 * test class
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK8 2019/12/5
 */
public class Test {

    public static void main(String[] args) throws Exception {
        String filePath = "/Users/llnancy/workspace/pdf/10万字总结.pdf";
        try (PDDocument document = Loader.loadPDF(new File(filePath))) {
            PDDocumentOutline outline = document.getDocumentCatalog().getDocumentOutline();
            if (outline != null) {
                readOutline(outline);
            }
        }
    }

    public static void readOutline(PDOutlineNode node) throws IOException {
        for (PDOutlineItem child : node.children()) {
            System.out.println(child.getTitle());
            if (child.children().iterator().hasNext()) {
                readOutline(child);
            }
        }
    }
}
