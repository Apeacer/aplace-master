package com.aplace.core.util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Descript: 自动读入配置文件
 *
 * @author ningning.wei
 * @version 17/8/10.
 */
public final class AutoProperty extends Properties{

    private static Logger logger = Logger.getLogger(AutoProperty.class);


    private static AutoProperty autoProperty;

    private AutoProperty(String[] files) throws IOException {
        super();
        load(files);
    }

    /**
     * 读入所有配置文件
     * @param files
     * @throws IOException
     */
    private void load(String[] files) throws IOException {
        for(String filename : files) {
            File file = new File(filename);
            if (!file.exists()) {
                logger.error("配置文件{} 不存在");
            } else {
                load(new FileInputStream(file));
            }
        }
    }
}
