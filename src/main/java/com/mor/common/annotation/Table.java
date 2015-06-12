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
 * @time 2012-10-22 上午10:49:01
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Table {
	/** 表名 */
	String name();

	/** 表注释 */
	String comment() default "";

	/** 插入语句 */
	String insertStatement();

	/** 模版查询语句 */
	String selectByExampleStatement();

	/** 模版查询语句 */
	String selectOneByExampleStatement() default "";

	/** 模版查询语句 */
	String selectByExampleWithBLOBStatement() default "";

	/** 模版查询语句 */
	String selectOneByExampleWithBLOBStatement() default "";
	
	/** 模版删除语句 */
	String deleteByExampleStatement();

	/** 模版统计语句 */
	String countByExampleStatement();

	/** 模版更新语句 */
	String updateByExampleStatement();

	/** 模版选择性删除语句 */
	String deleteByExampleSelectiveStatement();

	/** 模版选择性更新语句 */
	String updateByExampleSelectiveStatement();

}
