package com.example.adars.gotchya.Core;

import java.util.HashMap;

/**
 * Created by Adam Bachorz on 10.11.2018.
 */
public class ExtendedMap<C, K, V> {

    private HashMap<C, HashMap<K, V>> baseMap;

    public ExtendedMap() {
        baseMap = new HashMap<>();
    }

    public V get(C c, K key) {
        HashMap<K,V> subMap = baseMap.get(c);
        if (subMap == null || subMap.isEmpty()) return null;
        return subMap.get(key);
    }


    public void put(C c, Object[] keys, Object[] values) {
        if (keys.length != values.length) {
            try {
                throw new Exception("Ilość kluczy i wartości musi być taka sama");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        HashMap<K,V> subMap = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            subMap.put((K)keys[i], (V)values[i]);
        }
        baseMap.put(c, subMap);
    }

    public void put(C c, Object key, Object value) {
        HashMap<K,V> subMap = baseMap.get(c);
        subMap.put((K)key, (V)value);
        baseMap.put(c, subMap);
    }
}
