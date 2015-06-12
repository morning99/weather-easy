package com.mor.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ghost
 * @email other@mail.com
 * @time 2012-10-22 ����10:49:01
 */
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Column {
	/** ���� */
	String name();

	/** �г��� */
	int length() default 0;

	/** �����ֵ */
	boolean nullable() default true;

	/** ������ */
	String type();

	/** С��㾫�� */
	int scale() default 0;

	/** ��ע�� */
	String comment() default "";

	/** �Ƿ����� */
	boolean isPK() default false;
}
