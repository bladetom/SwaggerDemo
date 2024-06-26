package org.test;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.http.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;


import java.io.*;
//import java.net.http.HttpClient;
import java.util.*;

/**
 * @author tomtian
 * @create 2022-10-07 2:33
 * @Description
 */
public class HttpClient3 {
    public static String doGet(String url) {
        // 输入流
        InputStream is = null;
        BufferedReader br = null;
        String result = null;
        // 创建httpClient实例
        HttpClient httpClient = new HttpClient();
        // 设置http连接主机服务超时时间：15000毫秒
        // 先获取连接管理器对象，再获取参数对象,再进行参数的赋值
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
        // 创建一个Get方法实例对象
        GetMethod getMethod = new GetMethod(url);
        // 设置get请求超时为60000毫秒
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        // 设置请求重试机制，默认重试次数：3次，参数设置为true，重试机制可用，false相反
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, true));
        try {
            // 执行Get方法
            int statusCode = httpClient.executeMethod(getMethod);
            // 判断返回码
            if (statusCode != 200) {
                // 如果状态码返回的不是ok,说明失败了,打印错误信息
                System.err.println("Method faild: " + getMethod.getStatusLine());
            } else {
                // 通过getMethod实例，获取远程的一个输入流
                is = getMethod.getResponseBodyAsStream();
                // 包装输入流
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuffer sbf = new StringBuffer();
                // 读取封装的输入流
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp).append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 释放连接
            getMethod.releaseConnection();
        }
        return result;
    }
    //为什么没有设置请求头的东西
    public static String doPost(String url, Map<String, Object> paramMap) {
        // 获取输入流
        InputStream is = null;
        BufferedReader br = null;
        String result = null;
        // 创建httpClient实例对象
        HttpClient httpClient = new HttpClient();

//        HttpClient http = new DefaultHttpClient();
        // 设置httpClient连接主机服务器超时时间：15000毫秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
        // 创建post请求方法实例对象
        PostMethod postMethod = new PostMethod(url);
        Header header =new Header();

        postMethod.setRequestHeader(header);
        // 设置post请求超时时间
        postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        NameValuePair[] nvp = null;
        // 判断参数map集合paramMap是否为空
        if (null != paramMap && paramMap.size() > 0) {// 不为空
            // 创建键值参数对象数组，大小为参数的个数
            nvp = new NameValuePair[paramMap.size()];
            // 循环遍历参数集合map
            Set<Map.Entry<String, Object>> entrySet = paramMap.entrySet();
            // 获取迭代器
            Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
            int index = 0;
            while (iterator.hasNext()) {
                Map.Entry<String, Object> mapEntry = iterator.next();
                // 从mapEntry中获取key和value创建键值对象存放到数组中
                try {
                    nvp[index] = new NameValuePair(mapEntry.getKey(),
                            new String(mapEntry.getValue().toString().getBytes("UTF-8"), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                index++;
            }
        }
        // 判断nvp数组是否为空
        if (null != nvp && nvp.length > 0) {
            // 将参数存放到requestBody对象中
            postMethod.setRequestBody(nvp);
        }
        // 执行POST方法
        try {
            int statusCode = httpClient.executeMethod(postMethod);
            // 判断是否成功
            if (statusCode != 200) {
                System.err.println("Method faild: " + postMethod.getStatusLine());
            }
            // 获取远程返回的数据
            is = postMethod.getResponseBodyAsStream();
            // 封装输入流
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuffer sbf = new StringBuffer();
            String temp = null;
            while ((temp = br.readLine()) != null) {
                sbf.append(temp).append("\r\n");
            }
            result = sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 释放连接
            postMethod.releaseConnection();
        }
        return result;
    }

    public void match(String str,String needed){

    }

    //还要有一个random生产的password
    public static void main(String[] args) {
        //写错了这里是请求体
        Map<String,Object> requestBody = new HashMap<>(){{
            put("action","fastlogin");
            put("task","login");
            put("ref","");
            put("formhash","1febb2a9");
            put("username","jeanf");
        }};
        requestBody.put("password","aa8423");
        String str = doPost("https://rosefile.net/account.php",requestBody);
//        System.out.println(str);
//        Set<String> set = new HashSet<>();
//        for (int i = 0; i <100000 ; i++) {
//            set.add(getRandomString(2));
//        }
//        System.out.println(set);
//        System.out.println(set.size());
        List<Cookie> list =new ArrayList<>();
//        for (int i = 0; i <100000 ; i++) {
//            list.add(getRandom4digitals());
//        }
//        System.out.println(list);

    }


    //生成随机字符
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(52);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    //生成随机4位数
    public static String getRandom4digitals(){
        Random random =new Random();
        int i = random.nextInt(10000);
        StringBuffer sb =new StringBuffer();
        if (i<10){
            sb.append("000").append(i);
        }else if (i<100){
            sb.append("00").append(i);
        }else if (i<1000){
            sb.append("0").append(i);
        }else {
            sb.append(i);
        }
        return sb.toString();
    }
}
