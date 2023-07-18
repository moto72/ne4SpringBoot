package com.tmsps.ne4springboot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: UpdateIgnore
 * @Description: 更新忽略
 * @author: zhangwei(Mr.z).396033084@qq.com @date： 2023/07/18
 * @Copyright: 行歌信息
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface UpdateIgnore {

}
