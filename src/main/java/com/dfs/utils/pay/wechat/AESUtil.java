package com.dfs.utils.pay.wechat;



import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.dfs.utils.Md5;


public class AESUtil
{

    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "AES";

    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS7Padding";
    /**
     * 生成key
     */
    static
    {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * AES加密
     * 
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptData(String data, String key)
        throws Exception
    {
        // 创建密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
        // 初始化
        SecretKeySpec secretKey = new SecretKeySpec(
            Md5.GetMD5Code(key).toLowerCase().getBytes(), ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return Base64Util.encode(cipher.doFinal(data.getBytes()));
    }

    /**
     * AES解密
     * 
     * @param base64Data
     * @return
     * @throws Exception
     */
    public static String decryptData(String base64Data,String key)
        throws Exception
    {
        // 创建密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
        // 初始化
        SecretKeySpec secretKey = new SecretKeySpec(
            Md5.GetMD5Code(key).toLowerCase().getBytes(), ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(Base64Util.decode(base64Data)));
    }

    public static void main(String[] args)
        throws Exception
    {
        // String A 为测试字符串，是微信返回过来的退款通知字符串
        String A = "oSWxqqMF5lk2EF+gdrdt5wPrOru854za5XjHq5cUXs74zF9+jlxGOo7DHQIuntolVF3kQAruoMoNK5lLRsCulgG2hAT+6sNen8f/f3drMxfsTFOj3aBTKkIHs2p3AVJA4fXpGRCpejq3JJplSQnnSwFljzcxvqe7rU3y/H0KpFyBuYUSEf+msbkHEnHnIHQi4p9JDlLPWoKHramM7R65Qd13GdUU41scNybWCkwl+q/cY2Nv6KUt490JXTbTEgZNE6ArJKGg9woRMUdJEimTnv2OSY16yjo8dlIiozEoHcoQsvSFuMA5DHfHmtk5gbn8y6FVLHbt8XmmOIkfl/CVCXGQ+fGJmazxmqpTLBnAxXogFX2c2h8ZFqrWHW0wWZNSqpRX8HnMBw4V5hUMCiN9ASP3AzkpbtxdkDaeJYagVFgpB7oXxNUlQMy7pCqWCqbhoeLlZtzACx3qNqf57cQLn06T8wrYddf3f78oIYceVWMBses6wcJW2uTUdci4hYOQn5G+iVGLRzMuI8xwQSeBtdrWBor842tEsg4/wgFRxiEgjN+Jl+pCbwULjzt870OwC/UKD9mM3bhyay1jxeKNfkqgks0TH9eZXT1mR6IBfIUipgk9nTrGLFQwt4AAAf7/KoW7A3d1eYGY1vo/QkinixiZsxOJhzw95X6wiiARPa8oe0180lCuhLtIrNRlxyVMbbwA8GQVuCCE6w+/yKIF+el+Gcf7Gm2ljQzV7PEwiomW/DsBqUb5mwGfI52NLRa70kJ8vgaXeMN1xhwWYLzg02muvGGwS2P4kgGO0Sg0L5ycpN7Vp421+HnAPdcW6y/pQi03BKAR6fZT5JQYAIoNN4K8K6ZbgfZiuG32q0q4bwVWrg4jBlyPmj8JwHtbikbAgoJ9sUwWYi7P+Btk1ZHCPLW90p+1mIL8eVpneOaon3mSW0R4JDiIJK8oYLD/1n4NTKRTg9c6OMdSHnK8BUnodw==";
        String B = AESUtil.decryptData(A,"1471da051c9b43cb9763b39647a33ba4");
        System.out.println(B);
    }

}