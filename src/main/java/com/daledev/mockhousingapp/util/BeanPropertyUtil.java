package com.daledev.mockhousingapp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * @author dale.ellis
 * @since 23/08/2017
 */
public class BeanPropertyUtil {
    private static final Logger log = LoggerFactory.getLogger(BeanPropertyUtil.class);

    private BeanPropertyUtil() {
    }

    /**
     * @param bean
     * @param propertyName
     * @return
     */
    private static PropertyDescriptor getPropertyDescriptor(Object bean, String propertyName) {
        return getPropertyDescriptor(bean.getClass(), propertyName);
    }

    /**
     * @param clazz
     * @param propertyName
     * @return
     */
    private static PropertyDescriptor getPropertyDescriptor(Class clazz, String propertyName) {
        try {
            BeanInfo info = Introspector.getBeanInfo(clazz);
            for (PropertyDescriptor propertyDescriptor : info.getPropertyDescriptors()) {
                if (propertyDescriptor.getName().equalsIgnoreCase(propertyName)) {
                    return propertyDescriptor;
                }
            }
        } catch (IntrospectionException e) {
            log.warn("Failed to get property descriptors for class " + clazz, e);
        }
        return null;
    }

    /**
     * @param bean
     * @param propertyName
     * @return
     */
    public static boolean setPropertyValue(Object bean, String propertyName, Object value) {
        log.debug("Attempting to set property '{}' for bean : {}", propertyName, bean);
        PropertyDescriptor propertyDescriptor = getPropertyDescriptor(bean, propertyName);
        return setPropertyValue(bean, propertyDescriptor, value);
    }

    private static boolean setPropertyValue(Object bean, PropertyDescriptor propertyDescriptor, Object value) {
        if (propertyDescriptor != null) {
            Method writeMethod = propertyDescriptor.getWriteMethod();
            return setPropertyValue(bean, writeMethod, value);
        }
        return false;
    }

    private static boolean setPropertyValue(Object bean, Method writeMethod, Object value) {
        if (writeMethod != null) {
            try {
                writeMethod.invoke(bean, value);
                return true;
            } catch (Exception e) {
                log.warn("Failed to set bean(" + bean + ", property write method : " + writeMethod + ") to value " + value, e);
            }
        }
        return false;
    }
}
