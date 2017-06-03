var picPath;
var client_view = new OSS.Wrapper({
    region: 'oss-cn-shenzhen',
    accessKeyId: 'LTAIBu1n6STvMIym',
    accessKeySecret: 'q5PIq6KivS2HTXnl3TJiOyTdTRQ1mW',
    bucket: 'yangshaoping'
});


client_view.list({
    'max-keys': 10
}).then(function (result) {
    console.log(result);
    picPath = result.objects[0].url;
    $("#Pic_").attr("src", picPath);//原圖
    $("#Pic_1").attr("src", picPath + '?x-oss-process=image/resize,m_fixed,w_100,h_100');//縮放
    $("#Pic_2").attr("src", picPath + '?x-oss-process=image/crop,w_100,h_100,x_0,y_0,r_1');//裁剪
    $("#Pic_3").attr("src", picPath + '?x-oss-process=image/rotate,45');//旋轉
    $("#Pic_4").attr("src", picPath + '?x-oss-process=image/sharpen,100');//銳化
    $("#Pic_5").attr("src", picPath + '?x-oss-process=image/watermark,text_SGVsbG8g5Zu-54mH5pyN5YqhIQ');//水印
    $("#Pic_6").attr("src", picPath + '?x-oss-process=image/format,png');//格式轉換
    // $("#Pic_7").html(picPath + '?x-oss-process=image/info');
}).catch(function (err) {
    console.log(err);
});


$(function(){
    resize_("kk");
})


// 縮放
function resize_(picPath_) {
    picPath_ = picPath_ + '?x-oss-process=image/resize,m_fixed,w_100,h_100';

    $("#Pic_1").attr("src", picPath_);
}

// 裁剪
function crop_() {
    picPath = picPath + '?x-oss-process=image/crop,w_100,h_100,x_100,y_100,r_1';
    $("#Pic_2").attr("src", picPath);
}

// 旋轉
function rotate_() {
    picPath = picPath + '?x-oss-process=image/rotate,45';
}

// 銳化
function sharpen_() {
    picPath = picPath + '?x-oss-process=image/sharpen,100';
}

// 水印
function watermark_() {
    picPath = picPath + '?x-oss-process=image/watermark,text_SGVsbG8g5Zu-54mH5pyN5YqhIQ';
}

// 格式轉換
function format_() {
    picPath = picPath + '?x-oss-process=image/format,png';
}


// var client_op = new OSS({
//     accessKeyId: 'LTAIBu1n6STvMIym',
//     accessKeySecret: 'q5PIq6KivS2HTXnl3TJiOyTdTRQ1mW',
//     bucket: 'yangshaoping',
//     endpoint: 'http://oss-cn-shenzhen.aliyuncs.com'
// });

// 缩放
// OSS.co(function* () {
//   var result = yield client_op.get(picPath, './example-resize.jpg', 
//                                 {process: 'image/resize,m_fixed,w_150,h_100'});
//                                 console.log(result);
// }).catch(function (err) {
//   console.log(err);
// });