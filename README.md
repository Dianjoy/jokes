点乐服务器端API接口
===================
###1、获取广告数据列表地址：

http://a.xxxx.com/dev/api/adlist/adlist.php?device_id=354965058517687&os_version=4.1.2&app_id=072cb4d9d9d5dfd23ed2981e5e33fe59&imei=456000000000&output=json

参数说明：device_id：设备号,os_version:系统版本号 ,app_id:当前应用的ID,imei:设备imei,output:返回数据类型
 
 
###2、某广告下载成功后,通过HTTP GET请求以下地址通知点乐服务器：
 
http://a.xxxx.com/dev/api/downok.php?device_id=868943001942681&os_version=2.3.6&app_id=072cb4d9d9d5dfd23ed2981e5e33fe5  ad_id=1a5950324b6c6ae31cc38a4bdf035429
 
参数说明：device_id：设备号,os_version:系统版本号,app_id:当前应用的,ad_id:广告id


###3、广告安装激活后，通过HTTP GET请求以下地址,通知点乐服务器：
  
http://a.xxxx.com/dev/api/adlist/adnotify_storm.php?device_id=868232000177360&os_version=4.1.2&app_id=073e3d88f85771980a17e7019b4d7bf0&ad_id=1a5950324b6c6ae31cc38a4bdf035429
  
参数说明：device_id：设备号,os_version:系统版本号 ,app_id:当前应用的id,ad_id:激活广告的id

