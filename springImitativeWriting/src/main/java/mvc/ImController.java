package mvc;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)//@Retention描述注解的生命周期；取值（RetentionPoicy）RUNTIME在运行时有效（即运行时保留）
@Target(ElementType.TYPE)//@Target用于描述注解的使用范围（即 注解是描述：包、类、字段、方法、参数、接口等）,ElementType.TYPE用来描述类
public @interface ImController {
	public String value() default "";//注解元素必须有确定的值，要么在定义注解的默认值中指定，要么在使用注解时指定，非基本类型的注解元素的值不可为null。因此, 使用空字符串或0作为默认值是一种常用的做法。
}
