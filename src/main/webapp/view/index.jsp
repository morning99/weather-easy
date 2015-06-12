<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/view/front_resource.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta name="viewport" content="width=device-width,minimum-scale=1,maximum-scale=1,user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>容易天气</title>
<style type="text/css">
li .subm{background:#b83e31;border-radius:5px;border-bottom:5px solid #932e24;color:#fff3a7;font-size:3.6rem;letter-spacing:5px;width:95% !important;border-top:4px solid #d64e40;text-align:center;}
li span{display:inline-block;text-align:center;}
li span select{border-radius:5px;font-size:3.6rem;letter-spacing:5px;width:95% !important;text-align:center;}
ul .showbtn{height:72px;}
ul .btn .showbtn{height:15px;line-height:45px;margin-bottom: 55px;}
.show ul li{font-size:1.6rem;text-align:left;}
</style>
</head>
<body>
<div class="lott_warp lotto_index" style='text-align:center;'>
	<h1 style="font-size:3.6rem;">天气预报</h1><br>
	<div>
	<label>位置：</label>
	<span id="locationInfo">未获取</span>
	</div>
	<ul class="btn">
		<li class="showbtn">
			<span class="subm" id="vbtn">城市列表</span>
		</li>
		<li class="showbtn">
			<span id="showct"></span>
		</li>
		<li class="showbtn">
			<span class="subm" id="ctsbtn">下属区域</span>
		</li>
		<li class="showbtn">
			<span id="showCitys"></span>
		</li>
		<li class="showbtn">
			<span class="subm" id="wtbtn">天气情况</span>
		</li>
		<li class="show" id="showWeather">
		</li>
	</ul>
		<div class="bshare-custom icon-medium-plus" style="margin-top: 100px;">
			<a title="分享到" href="http://www.bShare.cn/" id="bshare-shareto" class="bshare-more">分享到</a>
			<a title="分享到QQ空间" class="bshare-qzone"></a>
			<a title="分享到新浪微博" class="bshare-sinaminiblog"></a>
			<a title="分享到人人网" class="bshare-renren"></a>
			<a title="分享到腾讯微博" class="bshare-qqmb"></a>
			<a title="分享到网易微博" class="bshare-neteasemb"></a>
			<a title="更多平台" class="bshare-more bshare-more-icon more-style-addthis"> </a>
			<span class="BSHARE_COUNT bshare-share-count">0</span>
		</div>
</div>
			<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=2&amp;lang=zh"></script>
			<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC0.js"></script>

</body>
<script type="text/javascript">
$(function(){
	$("#vbtn").on('click',function(){
		var vname = $("#vname").val();
		getmsg(vname);
	});
	$("#ctsbtn").on('click',function(){
		var vname = $("#country").val();
		getCitys(vname);
	});
	$("#wtbtn").on('click',function(){
		var vname = $("#citys option:selected").val();
		getWeather(vname);
	});
	//获取地理位置
	 getlocationInfo();
})

function getmsg(vname){
	var ctdiv = "<select id=\"country\">";
	$.ajax({
		type : "POST",
		url : "${path}/findstate",
		data : "t=" + new Date()+"&name="+vname,
		success : function(data) {
		      $.each(data.cts, function(key, value){
		             ctdiv+="<option value="+value+">"+key;
		      });
		      ctdiv+="</select>"
		      $("#showct").html(ctdiv);
		}
	});
}
	
	function getCitys(code){
		var ctdiv = "<select id=\"citys\">";
		$.ajax({
			type : "POST",
			url : "${path}/findCity",
			data : "t=" + new Date()+"&code="+code,
			success : function(data) {
			      $.each(data.cts, function(key, value){
			    	  ctdiv+="<option value="+value+">"+key;
			      });
			      ctdiv+="</select>"
			      $("#showCitys").html(ctdiv);
			}
		});
	}
	function getWeather(code){
		var ctdiv = "<ul>";
		$.ajax({
			type : "POST",
			url : "${path}/getWeather",
			data : "t=" + new Date()+"&code="+code,
			success : function(data) {
			      $.each(data.cts, function(key, value){
			    	  if(value.indexOf(".gif")>=0)
			    		  value="<img  src=\"${path}/images/weather/"+value+"\">";
			             ctdiv+="<li>"+value+"</li>";
			      });
			      ctdiv+="</ul>"
			      $("#showWeather").html(ctdiv);
			}
		});
	}
	
	function getlocationInfo(){
		var url = "${path}/getlocationInfo";
		$.post(url,
			function(data){
			if(data.errMsg){
				if(data.errNum == 0){
					var retData = data.retData;
				$("#locationInfo").empty();
				$("#locationInfo").append(retData.district);
				$("#locationInfo").append(" - ");
				$("#locationInfo").append(retData.city);
				$("#locationInfo").append(" - ");
				$("#locationInfo").append(retData.country);
				//根据城市名称获取天气
				getWeather(retData.district);
				}else{
					$("#locationInfo").empty();
					$("#locationInfo").append(data.ip+data.retData[0]);
				}
			}
		}		
		);
	}
</script>
</html>