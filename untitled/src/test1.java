import java.math.BigDecimal;
import java.math.BigInteger;

public class test1 {

    public static void main(String []args){

        int x = 1;
        Integer x1 = new Integer(1);
        /**
         * 做运算只能采用封装的方法
         */
        BigInteger bigInteger = new BigInteger("123123");

        //简单转化成long，如果超出范围不会抛出错误,会返回0或-1
        System.out.println(bigInteger.longValue());
        //转化成long，如果超过范围就会抛出错误
        System.out.println(bigInteger.longValueExact());

        BigDecimal bigDecimal = new BigDecimal("1231241241");

    }

}
