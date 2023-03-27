package com.example.projectCommon.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileHelper {

    private static final Logger logger = LoggerFactory.getLogger(FileHelper.class);

    public void safeDeleteFile(String fileName) {
        try {
            File file = new File(fileName);

            //Check if file exist
            if (!file.exists()) {
                logger.info(fileName + "  not exist, Deletion skip");
            }
            else {
                if (!file.delete()) {
                    logger.warn("File deletion fail");
                }
                else {
                    logger.info(fileName + " is deleted");
                }
            }
        }
        catch (Exception e) {
            logger.warn("Fail to delete file", e);
            throw new RuntimeException(e);
        }

    }
}
