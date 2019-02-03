package com.org.any

import java.util.regex.Pattern


fun main(args: Array<String>) {
    val url="http://hb.sina.com.cn/"
    var ret = CrawlerManager.getDataFromUrl(url)
    with(CrawlerManager){
        handlerUl(ret).forEach {
            handlerDiv(it).forEach{
               println( convertNewsBean(it))
            }
        }
    }
//    testRegex()
}


fun testRegex() {
//    var htmlTag =
//        "<div class='news-img'><a href='http://hb.sina.com.cn/news/b/2019-02-03/detail-ihrfqzka3362761.shtml' target='_blank'><img src='http://n.sinaimg.cn/hb/transform/266/w640h426/20190203/78-D-hsmkfyn9689292.jpg' alt='东湖绿道三期“美颜”完工 12个湖塘回归碧波' /></a></div>"
//    val regEx = "(?:(href='(?<href>.*?)'))|(?:(src='(?<src>.*?)'))|(?:(alt='(?<alt>.*?)'))"

//    val htmlTag="<p><span class='fl'>07-18 09:54</span>&nbsp;&nbsp;楚天都市报<span class='fr'><a href='#' class='grey'><b class='mcom_num' data-comment='comos-hfnsvyz8377575'></b></a></span></p>"
//    val regEx="(?:(<span\\s+class='fl'>(?<time>.*?)</sp))|(?:(an>(?<source>.*?)<span))"
//
////    val regEx="</span>(?<source>.*?)<span"
//    val pattern = Pattern.compile(regEx, Pattern.DOTALL)
//    val matcher = pattern.matcher(htmlTag)
//
//    println(matcher.groupCount())
//
//    var index=0

//    while (matcher.find()) {

//        if (matcher.start("href") != -1) {
//            println(matcher.group("href"))
//        }
//        if (matcher.start("src") != -1) {
//            println(matcher.group("src"))
//        }
//        if (matcher.start("alt") != -1) {
//            println(matcher.group("alt"))
//        }

//        if (matcher.start("time") != -1) {
//            println(matcher.group("time"))
//        }
//
//        if (matcher.start("source") != -1) {
//            println(matcher.group("source"))
//        }

//        println(matcher.group(index))
//
//        index++

//        println(matcher.group("source"))

//    }

}