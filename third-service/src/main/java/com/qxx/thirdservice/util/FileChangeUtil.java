package com.qxx.thirdservice.util;

import com.aspose.words.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ts on 2017/1/10.
 */
@Slf4j
public class FileChangeUtil {

    public static void changeDocxToPdf(File wordFilePath, File pdfFilePath) throws Exception {

        if (!AsposeLicenseUtil.getLicense()) {
            return;
        }

        // Apply the new set of font sources to use.
        FontSettings.getDefaultInstance().setFontsSources(updateFontSettings());

        Document doc = new Document(wordFilePath.getAbsolutePath());
        doc.save(pdfFilePath.getAbsolutePath(), SaveFormat.PDF);
    }

    private static FontSourceBase[] updateFontSettings() throws IOException {
        List<FontSourceBase> fontSources = new ArrayList<>(Arrays.asList(FontSettings.getDefaultInstance().getFontsSources()));

        ClassPathResource classPathResource = new ClassPathResource("/asposeWordFonts/SourceHanSerifCN-Regular.ttf");
        try (InputStream inputStream = classPathResource.getInputStream()) {
            MemoryFontSource memoryFontSource = new MemoryFontSource(inputStream.readAllBytes());
            fontSources.add(memoryFontSource);
        }

        return fontSources.toArray(new FontSourceBase[0]);
    }
}
