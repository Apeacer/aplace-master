package com.aplace.core.util;

import org.apache.log4j.Logger;

import java.io.*;

/**
 * 序列化工具
 * @author Apeace
 * @version 2017/5/23
 */
public class SerializeUtil {

    /**
     * LOGGER 对象
     */
    private static Logger LOGGER = Logger.getLogger(SerializeUtil.class);

	/**
	 * 序列化，将对像转为二进制
     *
	 * @param value 被转化对象
	 * @return 二进制byte数组,如果转换失败，长度为0
	 */
    public static byte[] serialize(Object value) {
        if (value == null) {
            throw new NullPointerException("Can't serialize null");
        }
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(value);
            os.close();
            bos.close();
            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info(String.format("Serialize error, object: %s",value));
            return new byte[0];
        } finally {
            close(os);
            close(bos);
        }
    }

    /**
     * 反序列化，二进制转对象
     *
     * @param in 二进制byte数组
     * @return 反序列化后的结果Object
     */
	public static Object deserialize(byte[] in) {
        return deserialize(in, Object.class);
    }
	
	/**
	 * 反序列化，二进制转对象
     *
	 * @param in 二进制数组
	 * @param requiredType 转换类型
	 * @return 返回对应类型的对象，如果失败返回null
	 */
	@SuppressWarnings("Unchecked")
    public static <T> T deserialize(byte[] in, Class<T> requiredType) {
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);

                Object result = is.readObject();
                if(requiredType.isInstance(result)) {
                    return (T) result;
                } else {
                    LOGGER.info("Deserialize error, wrong type");
                    return null;
                }
            }
            return null;// in == null
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(String.format("Deserialize error, object: %s",in.toString()));
            return null;
        } finally {
            close(is);
            close(bis);
        }
    }

    /**
     * 用于关闭资源，简介化代码
     *
     * @param closeable 要关闭的io对象
     */
    private static void close(Closeable closeable) {
        if (closeable != null)
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.error("close stream error");
            }
    }

    // 单元测试
    public static void main(String [] args) {

        String test = "测1测2测3测a试b试!*%{}。";
        System.out.println("before: "+test);
        byte[] serialize = SerializeUtil.serialize(test);

        System.out.print("process: ");
        for(byte a : serialize){
            System.out.print(a+" ");
        }
        System.out.println();

        test = SerializeUtil.deserialize(serialize, String.class);
        System.out.println("after: "+test);


    }

}
