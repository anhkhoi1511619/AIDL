// AIDLIf.aidl
package com.example.aidlserver;

// Declare any non-default types here with import statements

interface AIDLIf {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    String getString();
    int getInt();
    List<String> getList();
}