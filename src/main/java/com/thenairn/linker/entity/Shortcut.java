/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenairn.linker.entity;

import java.io.Serializable;

/**
 *
 * @author Tom
 */
public class Shortcut implements Serializable {

    private final String name;
    private KeyData data;
    private final KeyData defaultData;

    public Shortcut(String name, KeyData data, KeyData defaultData) {
        this.name = name;
        this.data = data;
        this.defaultData = defaultData;
    }

    public String getName() {
        return name;
    }

    public KeyData getData() {
        return data;
    }

    public void setData(KeyData data) {
        this.data = data;
    }

    public KeyData getDefaultData() {
        return defaultData;
    }

    public KeyData getPreferredData() {
        return data == null ? defaultData : data;
    }
}
