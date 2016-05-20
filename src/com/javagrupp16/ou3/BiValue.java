package com.javagrupp16.ou3;

import java.util.Map;

/**
 * Created by Mirrepirre on 2016-05-19.
 */
public class BiValue<K,V> {
        private K key;
        private V value;

        public BiValue(K key, V value){
            assert(key != null);
            assert(value != null);

            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

}
