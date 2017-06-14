### wangEditer3.0.0 部分设置  ###
     // 默认菜单配置
    // menus: ['head', 'bold', 'italic', 'underline', 'strikeThrough', 'foreColor', 'backColor', 'link', 'list', 'justify', 'quote', 'emoticon', 'image', 'table', 'video', 'code', 'undo', 'redo'],
    menus: ['head', 'bold', 'italic', 'underline', 'strikeThrough', 'foreColor', 'backColor', 'link', 'list', 'justify', 'quote', 'emoticon', 'image', 'table', 'code', 'undo', 'redo'],

    // 是否开启 debug 模式（debug 模式下错误会 throw error 形式抛出）
    debug: true,

    // 是否显示添加网络图片的 tab
    showLinkImg: true,

    // 默认上传图片 max size: 2M
    uploadImgMaxSize: 2 * 1024 * 1024,

    // 上传图片，是否显示 base64 格式
    uploadImgShowBase64: false,

    // 上传图片，server 地址（如果有值，则 base64 格式的配置则失效），注意前面必须是项目名称+接口地址全路径
    uploadImgServer: '/DingChong/whcd/manage/wangEditor/wangEditorUpload',

    // 上传图片的自定义参数
    uploadImgParams: {
        // token: 'abcdef12345'
    },

    // 上传图片的自定义header
    uploadImgHeaders: {
        // 'Accept': 'text/x-json'
    },

    // 配置 XHR withCredentials
    withCredentials: true,

    // 自定义上传图片超时时间 ms
    uploadImgTimeout: 5000,

### wangEditer3.0.0 表情及手势设置  ###
    // 拼接表情字符串
        var faceHtml = '';
        var faceStr = '😀 😃 😄 😁 😆 😅 😂  😊 😇 🙂 🙃 😉 😌 😍 😘 😗 😙 😚 😋 😜 😝 😛 🤑 🤗 🤓 😎 😏 😒 😞 😔 😟 😕 🙁  😣 😖 😫 😩 😤 😠 😡 😶 😐 😑 😯 😦 😧 😮 😲 😵 😳 😱 😨 😰 😢 😥 😭 😓 😪 😴 🙄 🤔 😬 🤐';
        faceStr.split(/\s/).forEach(function (item) {
            if (item) {
                faceHtml += '<span class="w-e-item">' + item + '</span>';
            }
        });

        var handHtml = '';
        var handStr = '🙌 👏 👋 👍 👎 👊 ✊ ️👌 ✋ 👐 💪 🙏 ️👆 👇 👈 👉 🖕 🖐 🤘 🖖';
        handStr.split(/\s/).forEach(function (item) {
            if (item) {
                handHtml += '<span class="w-e-item">' + item + '</span>';
            }
        });


### wangEditer3.0.0 基本功能设置  ###
    // MenuConstructors.video = Video;  // 通过取消构造器构造video来取消视频url上传这个功能
    当然也可以通过其他方式，参见官方说明！

      var maxSizeM = maxSize; // 上传文件最大字节

###### 还有其他对于宽高等更多功能的设置，已联系原作者进行沟通，还需要等原作者进行版本修改、更新。 #####