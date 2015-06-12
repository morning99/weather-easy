$(document).ready(function() {
	var ptitle = "世界杯狂欢也太好玩了，根本停不下来~还有机会赢电子阅读器和高端相机~一起来吧！http://hdm.feixin.10086.cn/worldcup/index/11.shtml";
	var preturnurl = getURL();
	//alert(preturnurl)
	var ppics = preturnurl+"/resources/common/images/sharepic.jpg";
	var data = {
			s1:{prefix:"http://i2.feixin.10086.cn/apps/share/share",title:"title",pic:"pic",url:"url"},//tong chuang
			s2:{prefix:"http://app.weibo.10086.cn/apps/share/share.php",title:"app_text",url:'app_link'},//10086 wei bo
			s3:{prefix:"http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey",title:"title",pic:"pics",url:"url"},//qzone
			s4:{prefix:"http://service.weibo.com/share/share.php",title:"title",pic:"pic",url:"url"},//sina wei bo
			s5:{prefix:"http://widget.renren.com/dialog/share?resourceUrl="+preturnurl,title:"title",pic:"pic"},
			s6:{prefix:"http://share.v.t.qq.com/index.php?c=share&a=index",title:"title",pic:"pic",url:''}//q wei bo
	};
	var EndPoint = function(prefix,title,pic,returnurl){
		this.prefix=prefix;
		this.title=title;
		this.pic=pic;
		this.returnurl=returnurl;
		EndPoint.prototype.genFullURL=function(){
			var q = -1;
			if(this.prefix.indexOf('?')==-1){
				if(this.pic)
					return this.prefix+"?"+this.title+"="+ptitle+"&"+this.pic+"="+ppics+"&"+this.returnurl+"=" +preturnurl;
				else
					return this.prefix+"?"+this.title+"="+ptitle+"&"+this.returnurl+"=" +preturnurl;
			}else{
				return this.prefix+"&"+this.title+"="+ptitle+"&"+this.pic+"="+ppics+"&"+this.returnurl+"=" +preturnurl;
			}
		};
	};
	$("a[stype='p']").each(function(idx, item) {
		var sid = $(item).attr('sid');
		prefix=data[sid].prefix;
		pics=data[sid].pic;
		url=data[sid].url;
		title=data[sid].title;
		var fullurl = new EndPoint(prefix, title, pics, url).genFullURL();
		$(item).click(function(){
			window.open(fullurl);
		});
	});
});
jQuery.extend({
	stringify : function stringify(obj) {
		var t = typeof (obj);
		if (t != "object" || obj === null) {
			// simple data type
			if (t == "string")
				obj = '"' + obj + '"';
			return String(obj);
		} else {
			// recurse array or object
			var n, v, json = [], arr = (obj && obj.constructor == Array);

			for (n in obj) {
				v = obj[n];
				t = typeof (v);
				if (obj.hasOwnProperty(n)) {
					if (t == "string")
						v = '"' + v + '"';
					else if (t == "object" && v !== null)
						v = jQuery.stringify(v);
					json.push((arr ? "" : '"' + n + '":') + String(v));
				}
			}
			return (arr ? "[" : "{") + String(json) + (arr ? "]" : "}");
		}
	}
});

function getURL(){
    var curWwwPath = window.document.location.href; 
    var pathName = window.document.location.pathname; //获取主机地址之后的目录，如： worldcup/index.shtml
    var pos = curWwwPath.indexOf(pathName); //获取主机地址，如： http://localhost:8080 
    var localhostPaht = curWwwPath.substring(0, pos); //获取带"/"的项目名，如：/cis 
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1); 
    var rootPath = localhostPaht + projectName; 
    return rootPath; 
     
} 