/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenairn.linker.config;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.IntellitypeListener;
import com.melloware.jintellitype.JIntellitype;
import com.thenairn.linker.entity.KeyData;
import com.thenairn.linker.entity.Shortcut;
import com.thenairn.linker.entity.ShortcutListener;
import java.awt.event.KeyEvent;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tom
 */
public class LinkerConfig {

    private static final String FILE_CONFIG = "Linker.config";

    public static final String LAUNCH_OVERLAY = "Launch Overlay";
    public static final String UPLOAD_CLIPBOARD = "Upload Clipboard";
    public static final String COPY_SELECTION = "Copy Selection";
    public static final String UPLOAD_SELECTION = "Upload Selection";

    private static Map<String, Shortcut> cache;

    private static Map<Integer, Entry<Shortcut, ShortcutListener>> registeredShortcuts = new HashMap<>();

    public static Shortcut getShortcut(String id) {
        return getShortcuts().get(id);
    }

    public static Map<String, Shortcut> getShortcuts() {
        if (cache != null) {
            return cache;
        }
        File file = new File(getUserDataDirectory(), FILE_CONFIG);
        if (file.exists()) {
            return cache = readFile(file);
        }
        return cache = getDefaults();
    }

    private static Map<String, Shortcut> getDefaults() {
        Map<String, Shortcut> shortcuts = new HashMap<>();
        shortcuts.put(LAUNCH_OVERLAY, new Shortcut(LAUNCH_OVERLAY, null, new KeyData(KeyEvent.CTRL_MASK, KeyEvent.VK_BACK_QUOTE)));
        shortcuts.put(UPLOAD_CLIPBOARD, new Shortcut(UPLOAD_CLIPBOARD, null, new KeyData(KeyEvent.CTRL_MASK + KeyEvent.ALT_MASK, KeyEvent.VK_C)));
        shortcuts.put(COPY_SELECTION, new Shortcut(COPY_SELECTION, null, new KeyData(KeyEvent.CTRL_MASK, KeyEvent.VK_C)));
        shortcuts.put(UPLOAD_SELECTION, new Shortcut(UPLOAD_SELECTION, null, new KeyData(KeyEvent.CTRL_MASK, KeyEvent.VK_V)));
        return shortcuts;
    }

    public static void saveShortcuts(Shortcut[] shortcuts) {
        Map<String, Shortcut> map = new HashMap<>();
        for (Shortcut shortcut : shortcuts) {
            map.put(shortcut.getName(), shortcut);
        }
        saveShortcuts(map);
    }

    public static void saveShortcuts(Map<String, Shortcut> shortcuts) {
        cache = shortcuts;
        saveFile(shortcuts, new File(getUserDataDirectory(), FILE_CONFIG));
    }

    public static void init() {
        Libraries.load();
        System.out.println("Can run? "+JIntellitype.isJIntellitypeSupported());
        
        if (JIntellitype.checkInstanceAlreadyRunning("Linker")) {
            System.exit(1);
        }
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

            @Override
            public void run() {
                JIntellitype.getInstance().cleanUp();
            }
        }));
        JIntellitype.getInstance().addIntellitypeListener(new IntellitypeListener() {

            @Override
            public void onIntellitype(int i) {
                System.out.println("intelliType called: " + i);
                Entry<Shortcut, ShortcutListener> e = registeredShortcuts.get(i);
                e.getValue().onShortcut(e.getKey());
            }
        });
        JIntellitype.getInstance().addHotKeyListener(new HotkeyListener() {

            @Override
            public void onHotKey(int i) {
                System.out.println("Hotkey called: " + i);
                Entry<Shortcut, ShortcutListener> e = registeredShortcuts.get(i);
                e.getValue().onShortcut(e.getKey());
            }
        });
    }

    private static int id = 0;

    public static void registerGlobalShortcut(Shortcut shortcut, ShortcutListener listener) {
        JIntellitype.getInstance().registerSwingHotKey(id, shortcut.getPreferredData().getModifiers(), shortcut.getPreferredData().getKeyCode());
        System.out.println("Registered shortcut: " + shortcut.getName() + " " + id);
        registeredShortcuts.put(id, new AbstractMap.SimpleEntry(shortcut, listener));
        id++;

    }

    public static void registerGlobalShortcut(String shortcut, ShortcutListener listener) {
        Shortcut s = getShortcut(shortcut);
        registerGlobalShortcut(s, listener);
    }

    public static void unregisterGlobalShortcut(Shortcut shortcut) {
        for (Entry<Integer, Entry<Shortcut, ShortcutListener>> entry : registeredShortcuts.entrySet()) {
            if (entry.getValue().getKey() == shortcut) {
                registeredShortcuts.remove(entry.getKey());
                return;
            }
        }
    }

    public static void saveFile(Object object, File file) {
        file.getParentFile().mkdirs();
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            OutputStream os = new FileOutputStream(file);
            OutputStream buffer = new BufferedOutputStream(os);
            ObjectOutput output = new ObjectOutputStream(buffer);
            output.writeObject(object);
        } catch (IOException ex) {
            Logger.getLogger(LinkerConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static Map<String, Shortcut> readFile(File file) {
        FileInputStream inputFileStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            inputFileStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(inputFileStream);
            return (Map<String, Shortcut>) objectInputStream.readObject();
        } catch (Exception ex) {
            Logger.getLogger(LinkerConfig.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                objectInputStream.close();
                inputFileStream.close();
            } catch (Exception ex) {
                Logger.getLogger(LinkerConfig.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static String getUserDataDirectory() {
        return System.getProperty("user.home") + File.separator + ".jstock" + File.separator + "Linker" + File.separator;
    }

}
