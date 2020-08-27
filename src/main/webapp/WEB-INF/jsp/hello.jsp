<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>这是一个jsp的测试类</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
    <script type="text/javascript">
        var ws =
            $(function(){

                ws = new WebSocket("ws://localhost:8080/dunHandler");

                ws.onopen = function () {
                    alert("已经链接")
                    //ws.send("{}");
                }
                ws.onclose = function () {
                    console.log("onclose");
                }

                ws.onmessage = function (msg) {
                    //	alert(msg.data+"这是接收到的内容");
                    $("#h1").append("<h4 style='color: royalblue;' >"+msg.data+"</h4>");
                }
            });

        function sendMessage(){
            var messageText = $('#messageText').val();
            $.post('${pageContext.request.contextPath}/sendMessage',{'messageText':messageText},function(data){
                //alert(data.message);
            })
        }

    </script>

</head>
<body>
<div align="center" style="width: 100%;height: 100%;background: moccasin;border: 3px solid darkolivegreen;" >
    <h1 align="center">欢迎来到websoket测试页面</h1>
    <div align="center" style="width: 600px;height: 400px;border: 2px solid aquamarine;">
        <div style="width: 100%;height: 10%;border: 2px solid gray;">
            <font style="color: blue;size: a3;">聊天窗口</font>
        </div>
        <div style="width:100%;height:78%;color: blanchedalmond;border: darkgoldenrod solid seagreen;">
            <input type="text" id="message" value="输入的内容" />
            <textarea id="description" name="description"></textarea>
            <span id="h1" style="color: royalblue;" ><h4 >这是什么</h4></span>
        </div>
        <div style="width: 100%;height: 10%;border: 2px solid saddlebrown;">
            <input type="text" name="messageText" id="messageText"  />请输入内容
            <input type="button" value="sendBtn" onclick="sendMessage()" />
        </div>
    </div>

</div>
</body>
</html>