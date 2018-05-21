/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru.helper;

import java.lang.reflect.*;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author lars
 */
public class Saver {

    public final static Class<?>[] use_toString = new Class<?>[]{Integer.class, Boolean.class, Float.class, Double.class, Short.class, Long.class, Byte.class};
    public final static String[] use_toString_types = new String[]{"int", "bool", "float", "double", "short", "long", "byte"};

    public static boolean checkContained(Class<?> c) {
        for (Class<?> c2 : use_toString) {
            if (c2.equals(c)) {
                return true;
            }
        }
        return false;
    }

    public static Field[] concat(Field[] a1, Field[] a2) {
        Field[] r = new Field[a1.length + a2.length];
        System.arraycopy(a1, 0, r, 0, a1.length);
        System.arraycopy(a2, 0, r, a1.length, a2.length);
        return r;
    }

    public static String toString(Object o) throws IllegalArgumentException, IllegalAccessException {
        if (o != null) {
            Class<?> o_class;
            o_class = o.getClass();
            String res = o_class.getCanonicalName();
            res += ":{";
            if (o instanceof HashMap) {
                HashMap h=(HashMap) o;
                Set entries=h.entrySet();
                int size=entries.size();
                Iterator it=entries.iterator();
                int i=0;
                while (it.hasNext()) {
                    Entry e=(Entry) it.next();
                    res+=Saver.toString(e.getKey());
                    res+="-";
                    res+=Saver.toString(e.getValue());
                    i++;
                    if (i < size) {
                        res+=", ";
                    }
                    it.remove();
                }
               
                
            } else if (o instanceof List) {
                List list = (List) o;
                for (int i = 0; i < list.size(); i++) {
                    res += Saver.toString(list.get(i));
                    if (i < list.size() - 1) {
                        res += "}";
                    }
                }
            } else if (o instanceof Object[]) {
                Object[] list = (Object[]) o;
                for (int i = 0; i < list.length; i++) {
                    res += Saver.toString(list[i]);
                    if (i < list.length - 1) {
                        res += ",";
                    }
                }
            } else if (o instanceof java.nio.Buffer) {
                //FloatBuffer b=(FloatBuffer)o;
                //b.
            } else if (o instanceof String) {
                res += (String) o;
            } else if (checkContained(o_class)) {
                res += o.toString();
            } else {
                Field[] o_classes_fields = o_class.getDeclaredFields();
                Class<?> o_superclass = o_class.getSuperclass();
                while (o_superclass != Object.class) {
                    o_classes_fields = concat(o_classes_fields, o_superclass.getDeclaredFields());
                    o_superclass = o_class.getSuperclass();
                }
                for (int i = 0; i < o_classes_fields.length; i++) {
                    Object v = o_classes_fields[i].get(o);
                    if (v == null) {
                        res += o_classes_fields[i].getType().getName();
                        res += ":";
                        res += o_classes_fields[i].getName();
                        res += "=";
                        res += "null";
                    } else {
                        res += o_classes_fields[i].getName();
                        res += "=";
                        res += Saver.toString(v);
                    }
                    if (i < o_classes_fields.length - 1) {
                        res += ", ";
                    }
                }
            }
            res += "}";
            return res;
        } else {
            return null;
        }
    }
}
