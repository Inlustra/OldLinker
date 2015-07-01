/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenairn.linker.config;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Tom
 */
public class Libraries {

    private static final String LIB_BIN = "/lib/";
    private final static String INTELLITYPE = "JIntellitype";
    private final static String INTELLITYPE_64 = "JIntellitype64";
    private static final Logger logger = Logger.getLogger(Libraries.class.getName());

    private static String getName() {
        return "64".equals(System.getProperty("sun.arch.data.model")) ? INTELLITYPE_64 : INTELLITYPE;
    }

    public static void load() {
        try {
            logger.info("Loading DLL");
            System.loadLibrary(getName());
            logger.info("DLL is loaded from memory");
        } catch (UnsatisfiedLinkError e) {
            loadFromJar();
        }
    }

    /**
     * When packaged into JAR extracts DLLs, places these into
     */
    private static void loadFromJar() {
        // we need to put both DLLs to temp dir
        String path = "AC_" + new Date().getTime();
        loadLib(path, getName());
    }

    /**
     * Puts library to temp dir and loads to memory
     */
    private static void loadLib(String path, String name) {
        name = name + ".dll";
        try {
            // have to use a stream
            InputStream in = Libraries.class.getResourceAsStream(LIB_BIN + name);
            // always write to different location
            File fileOut = new File(System.getProperty("java.io.tmpdir") + "/" + path + LIB_BIN + name);
            logger.info("Writing dll to: " + fileOut.getAbsolutePath());
            OutputStream out = FileUtils.openOutputStream(fileOut);
            IOUtils.copy(in, out);
            in.close();
            out.close();
            System.load(fileOut.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // blah-blah - more stuff
}
