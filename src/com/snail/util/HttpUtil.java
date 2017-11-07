package com.snail.util;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

public class HttpUtil {
	
	public static Logger logger = Logger.getLogger(HttpUtil.class);
	
    private static CloseableHttpClient httpClient;

    static {
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(8 * 1000)  //读超时时间（等待数据超时时间）
                .setConnectTimeout(8 * 1000) //连接超时时间
                .setConnectionRequestTimeout(1 * 1000) //从池中获取连接超时时间
                .setStaleConnectionCheckEnabled(true)  //检查是否为陈旧的连接，默认为true，类似testOnBorrow
//                .setProxy(new HttpHost("10.243.246.11", 1024))//代理
                .build();
        /**
         * 重试处理 默认是重试3次，修改为不重发
         */
        HttpRequestRetryHandler requestRetryHandler = new DefaultHttpRequestRetryHandler(0, false);

        httpClient = HttpClients.custom()
                .setDefaultRequestConfig(defaultRequestConfig)
                .setMaxConnTotal(200)  //最大连接数
                .setMaxConnPerRoute(100)  //默认的每个路由的最大连接数
                .setRetryHandler(requestRetryHandler)
                .build();
    }

    /**
     * HTTP GET
     *
     * @param baseUrl
     * @param params
     * @return
     */
    public static String httpGet(String baseUrl, Map<String, String> params) {
    	logger.info("HTTP请求URL：{"+baseUrl+"}, params:{"+JSON.toJSONString(params)+"}");

        HttpGet get = new HttpGet(baseUrl);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(get);
            int status = response.getStatusLine().getStatusCode();
            logger.info("HTTP请求返回状态为：{"+status+"}");
            if (status == 200) {
                String result = EntityUtils.toString(response.getEntity());
                logger.info("HTTP请求返回：{"+result+"}");
                return result;
            } else {
                logger.error("请求失败。返回：{"+EntityUtils.toString(response.getEntity())+"}");
                return null;
            }

        } catch (IOException e) {
            logger.error("请求失败。URL：" + baseUrl, e);
        } finally {
            closeResponse(response);
        }
        return null;
    }
    
    private static void closeResponse(CloseableHttpResponse response) {
        if (null != response) {
            try {
                response.close();
            } catch (IOException e) {

            }
        }
    }

}
