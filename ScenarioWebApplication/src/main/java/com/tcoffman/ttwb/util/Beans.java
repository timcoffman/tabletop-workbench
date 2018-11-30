package com.tcoffman.ttwb.util;

import static java.util.Arrays.stream;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

public class Beans {

	public interface BeanWrapper {
		BeanPropertyWrapper property(String name);
	}

	public interface BeanPropertyWrapper {
		BeanPropertyWrapper defaultTo(Object value);

		BeanPropertyWrapper property(String name);

		BeanWrapper and();
	}

	private static class BeanWrapperImpl implements BeanWrapper {
		private final Object m_bean;
		private final java.beans.BeanInfo m_beanInfo;

		public BeanWrapperImpl(Object bean) throws IntrospectionException {
			m_bean = bean;
			m_beanInfo = java.beans.Introspector.getBeanInfo(bean.getClass());
		}

		@Override
		public BeanPropertyWrapper property(String name) {
			final PropertyDescriptor propertyDescriptor = stream(m_beanInfo.getPropertyDescriptors()).filter(pd -> name.equals(pd.getName())).findFirst()
					.orElseThrow(RuntimeException::new);
			return new BeanPropertyWrapper() {

				@Override
				public BeanPropertyWrapper defaultTo(Object value) {
					try {
						final Object currentValue = propertyDescriptor.getReadMethod().invoke(m_bean);
						if (null == currentValue || currentValue.toString().isEmpty())
							propertyDescriptor.getWriteMethod().invoke(m_bean, value);
					} catch (final InvocationTargetException | IllegalAccessException | IllegalArgumentException ex) {
						throw new RuntimeException(ex);
					}
					return this;
				}

				@Override
				public BeanPropertyWrapper property(String name) {
					return BeanWrapperImpl.this.property(name);
				}

				@Override
				public BeanWrapper and() {
					return BeanWrapperImpl.this;
				}

			};
		}
	}

	public static BeanWrapper bean(Object bean) {
		try {
			return new BeanWrapperImpl(bean);
		} catch (final IntrospectionException ex) {
			throw new RuntimeException(ex);
		}
	}

}
