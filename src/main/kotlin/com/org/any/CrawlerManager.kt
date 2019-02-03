package com.org.any

import org.apache.http.client.ClientProtocolException
import org.apache.http.client.ResponseHandler
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.parser.Parser
import java.util.regex.Pattern

/**
 * 爬虫
 */
object CrawlerManager {

    fun getDataFromUrl(url: String): String {

        var httpClient = HttpClients.createDefault()
        try {
            var httGet = HttpGet(url)
            // Create a custom response handler
            val responseHandler = ResponseHandler<String> { response ->
                val status = response.statusLine.statusCode
                val ret = if (status in 200..299) {
                    val entity = response.entity
                    if (entity != null) EntityUtils.toString(entity!!, "UTF-8") else null
                } else {
                    throw ClientProtocolException("Unexpected response status: $status")
                }
                ret
            }
            return httpClient.execute(httGet, responseHandler)
        } finally {
            httpClient.close()
        }
    }


    /**
     * 处理ul
     */
    fun handlerUl(htmlText:String):List<String>{
        val regEx ="<ul\\s+class='news-list cur'.*?>.*?</ul>"
        return handlerHtmlByRegex(htmlText,regEx)
    }


    /**
     * 处理div
     */
    fun handlerDiv(tagText:String):List<String>{
        var regEx="<div class='clear grey normal'>.*?</div></div>"
        return handlerHtmlByRegex(tagText,regEx)
    }


    /***
     *
        <div class='clear grey normal'>
            <div class='news-img'>
                <a href='http://hb.sina.com.cn/news/j/2019-02-03/detail-ihrfqzka3363685.shtml' target='_blank'><img src='http://n.sinaimg.cn/hb/transform/266/w640h426/20190203/NMNk-hsmkfyn9704328.jpg' alt='中国年夜饭地图来了 湖北人最爱藕、珍珠圆子和鱼糕' />
                </a>
            </div>
            <div class='news-text '>
                <h2><a href='http://hb.sina.com.cn/news/j/2019-02-03/detail-ihrfqzka3363685.shtml' target='_blank'>中国年夜饭地图来了 湖北人最爱藕、珍珠圆子和鱼糕</a>
                </h2>
                <p><span class='fl'>02-03 09:08</span>&nbsp;&nbsp;武汉晚报<span class='fr'><a href='#' class='grey'><b class='mcom_num' data-comment='comos-hrfqzka3363685'></b></a></span>
                </p>
            </div>
        </div>
     *
     */
    fun convertNewsBean(tagText:String):NewsBean{
//        println(tagText)
        //多个正则表达式联写//|(?:(src='(?<imgUrl>.*?)'))|(?:(<h2><a\s+.*?>(?<title>.*?)</a></h2>))|(?:(an>(?<source>.*?)<span))|(?:(<span\s+class='fl'>(?<time>.*?)</sp))
        val regEx = "(?:(<div class='news-img'><a\\s+href='(?<linkA>.*?)'\\s+target='_blank'>))|(?:(<img\\s+src='(?<imgUrl>.*?)'\\s+alt=))|(?:(<div class='news-text '><h2><a\\s+.*?>(?<title>.*?)</a></h2>))|(?:(an>(?<source>.*?)<span))|(?:(<span\\s+class='fl'>(?<time>.*?)</sp))"

        val pattern = Pattern.compile(regEx, Pattern.DOTALL)
        val matcher = pattern.matcher(tagText)
        var linkA:String=""
        var imgUrl:String=""
        var title:String=""
        var source=""
        var time=""

        while (matcher.find()) {

            if (matcher.start("linkA") != -1) {
                linkA= matcher.group("linkA")
            }
            if (matcher.start("imgUrl") != -1) {
                imgUrl=matcher.group("imgUrl")
            }
            if (matcher.start("title") != -1) {
                title=matcher.group("title")
            }
            if (matcher.start("time") != -1) {
                time=matcher.group("time")
            }
            if (matcher.start("source") != -1) {
                source=matcher.group("source")
            }
        }
        return NewsBean(linkA,imgUrl,title,time,source)
    }

    /***
     * 给定正则，获取指定Tag元素
      */
   private fun handlerHtmlByRegex(input:String,regEx: String):List<String>{
        val pattern = Pattern.compile(regEx, Pattern.DOTALL)
        val matcher = pattern.matcher(input)
        var list:MutableList<String> = arrayListOf()
        while (matcher.find()){
            list.add(matcher.group())
        }
        return list
    }

}