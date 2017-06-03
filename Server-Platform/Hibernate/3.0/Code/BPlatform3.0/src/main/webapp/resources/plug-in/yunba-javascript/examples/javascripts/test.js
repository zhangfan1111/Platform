/**
 * Created by MSI on 2016/5/24.
 */
var appkey = "573ec50e7245df350c37ae4e";
//建立云巴连接
var yunba = new Yunba({server: 'sock.yunba.io', port: 3000, appkey: appkey});
$(function() {
    //初始化云巴
    yunba.init(function (success) {
        if (success) {
            yunba.connect_by_customid('whcd201504', function (success, msg, sessionid) {
                if (success) {
                    $('#msg').html('<div style="color:green">已连接上 socket</div>');
                    $('#msg').append('<div style="color:green">SocketId: ' + yunba.socket.id) + '</div>';
                    $('#msg').append('你已成功连接到消息服务器，会话ID：' + sessionid);

                    $('#connect_status').html('Connected Success !');
                    $('#connect_status').css('color', 'green');
                } else {
                    console.log(msg);
                }
            });
        }
    });
});

/**
 * 连接云巴
 */
function mqtt_connect() {
    yunba.connect(function (success, msg) {
        if (success) {
            $('#connect_status').html('Connected Success !');
            $('#connect_status').css('color', 'green');
        } else {
            alert(msg);
        }
    });
}

/**
 * 断开云巴
 */
function mqtt_disconnect() {
    yunba.disconnect(function (success, msg) {
        if (success) {
            $('#connect_status').html('Disconnected Success !');
            $('#connect_status').css('color', 'red');
        } else {
            alert(msg);
        }
    });
}

/**
 * 按频道发布
 * @returns {boolean}
 */
function mqtt_publish() {
    yunba.publish({'topic': 'my_topic', 'msg': '你好！Yunba。'},
        function (success, msg) {
            if (success) {
                console.log('消息发布成功');
            } else {
                console.log(msg);
            }
        }
    );
}