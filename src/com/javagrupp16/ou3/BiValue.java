package com.javagrupp16.ou3;

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

        @Override
        public int hashCode() {
            int result = key.hashCode();
            result = 31 * result + value.hashCode();
            return result;
        }

        @Override
        public boolean equals(Object o) {
            if(o instanceof BiValue){
                BiValue biValue = (BiValue) o;
                return biValue.getKey().equals(getKey()) && biValue.getValue().equals(getValue());
            }
            return false;
        }

}
