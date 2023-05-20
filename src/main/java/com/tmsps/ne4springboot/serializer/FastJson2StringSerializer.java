package com.tmsps.ne4springboot.serializer;

import java.lang.reflect.Type;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.writer.ObjectWriter;

/**
 *	@ClassName: FastJson2StringSerializer
 *	@Description: 
 *	@author: zhangwei(Mr.z).396033084@qq.com
 *	@date： 2023/05/20
 *	@Copyright: 行歌信息	
 */
public class FastJson2StringSerializer implements ObjectWriter<Object> {

	@Override
	public void write(JSONWriter jsonWriter, Object object, Object fieldName, Type fieldType, long features) {
		if (null == object) {
			jsonWriter.writeNull();
		} else {
			jsonWriter.writeString(object.toString());
		}
	}

}
