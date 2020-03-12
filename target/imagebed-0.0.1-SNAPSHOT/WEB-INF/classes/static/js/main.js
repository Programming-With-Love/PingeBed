/**
 * 获取悬浮窗的焦点
 * 将它设置在窗口的中心点
 */
function setFocus() {
    var clientHeight = $(document.body).height();
    var clientWidth = $(document.body).width();


    var secWidth = $("#selectFile").width()
    var secHeight = $("#selectFile").height();

    var left = (clientWidth/2) - (secWidth/2);
    var top = (clientHeight/2) - (secHeight/2);

    $("#selectFile").css("left", left);
    $("#selectFile").css("top", top);
}


/**
 * 获取上传文件对象
 */
function upload() {
    if ($(".selected").text() == "本地上传") {
        // 直接选择图片时
        var fileCount = $("#upload")[0].files.length;
        console.log(fileCount);
        for (var i = 0; i < fileCount; i++) {
            let file = ($("#upload")[0].files[i]);
            uploadFile(file, (res) =>{
                let loaded = res.loaded,
                    total = res.total;

                console.log("loaded: " + loaded);
                console.log("total: " + total);
                $("#test").css("width", loaded/total * 100 + "%");
            },(res) =>{
                switch (res.data.state) {
                    case "ok" :
                        let instance = generateInstance(res.data.ext, location.origin + res.data.msg);
                        $(".progress").after(instance);
                        instance.slideDown(1000);
                        bs4pop.notice("一张图片已上传成功!", {position: "bottomright", type: "secondary"});
                        $("#test").animate({width:'0%'});
                        break;
                    case "!pic" :
                        bs4pop.notice("请上传图片文件!", {position: "bottomright", type: "secondary"});
                        break;
                    case "error" :
                        bs4pop.notice("上传错误，请重新上传!", {position: "bottomright", type: "secondary"});
                        break;
                    case "null" :
                        break;
                    case "!admin" :
                        bs4pop.notice(res.data.msg, {position: "bottomright", type: "secondary"});
                        break;
                }
                $(".progress-bar").css("width", "0%")
            });
        }
        $(".dialog-close").click();
    } else {
        // 获取输入框里的地址
        var url = $("#cloneArea").val();

        // 判断是否为空
        if (url == "") {
            console.log("没有输入任何网址");
            console.log("无法clone");
            return;
        } else {
            $(".remind").text("准备传输，请稍后");
            // 将输入框里的内容按行分开
            let urls = url.split("\n");
            // 使用for in
            for (let i in urls) {
                console.log(urls[i]);
                clone(urls[i]);
            }
        }
        $(".dialog-close").click();
    }
}

/**
 * 向服务器上传文件
 */
function uploadFile(file, callback1, callback2) {
    let param = new FormData();
    param.append("file", file);
    axios({
        method: 'post',
        url: '/upload',
        data: param,
        onUploadProgress: function(progressEvent) {
            callback1(progressEvent);
        }
    })
        .then(function (response) {
            callback2(response);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * 将输入框内的url地址提交给服务器
 */
function clone(str) {
    $(".remind").text("正在克隆图片......");
    axios({
        method: 'post',
        url: '/clone',
        data: {
            "url": str
        }
    })
        .then(function (response) {
            switch (response.data.state) {
                case "ok" :
                    let instance = generateInstance(response.data.org, location.origin + response.data.msg);
                    $(".progress").after(instance);
                    instance.slideDown(1000);
                    bs4pop.notice("一张图片已上传成功!", {position: "bottomright", type: "secondary"});
                    $("#test").animate({width:'0%'});
                    $(".remind").text("克隆成功!");
                    break;
                case "!pic" :
                    bs4pop.notice(response.data.msg, {position: "bottomright", type: "secondary"});
                    $(".remind").text("克隆失败，请输入图片地址");
                    break;
                case "error" :
                    bs4pop.notice(response.data.msg, {position: "bottomright", type: "secondary"});
                    $(".remind").text("克隆错误，请重试");
                    break;
                case "repeat" :
                    bs4pop.notice(response.data.msg, {position: "bottomright", type: "secondary"});
                    $(".remind").text("克隆失败，请重试");
                    break;
                case "!url" :
                    bs4pop.notice(response.data.msg, {position: "bottomright", type: "secondary"});
                    $(".remind").text("克隆失败，请检查图片地址无误后重试");
                    break;
                case "!full" :
                    bs4pop.notice(response.data.msg, {position: "bottomright", type: "secondary"});
                    $(".remind").text("克隆错误，地址有误");
                case "!admin" :
                    bs4pop.notice(response.data.msg, {position: "bottomright", type: "secondary"});
                    $(".remind").text("管理员已禁止克隆文件!");
            }
            $(".progress-bar").css("width", "0%")
        })
        .catch(function (error) {
            console.log(error);
        });
}


function generateHtml(str) {
    return "<img src='" + str +  "'>";
}

function generateMd(originalName, str) {
    return '![' + originalName + '](' + str + ')';
}

function generateInstance(orgName, str) {

    let instance = $("<div class='instance'></div>");

    let instance_pic = $("<a target='_blank' href='" + str + "'><img class='preview' src='" + str + "'></a>");

    let input_group = $("<div class='instance-address'></div>");

    let input_http = $("<div class='input-group mb-3'><div class='input-group-prepend'><span class='input-group-text'>HTTP</span></div><input class='form-control' type='text'><div class='input-group-append'><span class='input-group-text'><a class='cp' onclick='clipboard(this)'>复制</a></span></div></div>");
    input_http.children()[1].value = str;

    let input_html = $("<div class='input-group mb-3'><div class='input-group-prepend'><span class='input-group-text'>HTML</span></div><input class='form-control' type='text'><div class='input-group-append'><span class='input-group-text'><a class='cp' onclick='clipboard(this)'>复制</a></span></div></div>");
    input_html.children()[1].value = generateHtml(str);

    let input_md = $("<div class='input-group mb-3'><div class='input-group-prepend'><span class='input-group-text'>MARKDOWN</span></div><input class='form-control' type='text'><div class='input-group-append'><span class='input-group-text'><a class='cp' onclick='clipboard(this)'>复制</a></span></div></div>");
    input_md.children()[1].value = generateMd(orgName, str);

    instance_pic.appendTo(instance);


    input_http.appendTo(input_group);
    input_html.appendTo(input_group);
    input_md.appendTo(input_group);

    input_group.appendTo(instance);

    instance.css("display", "none");

    return instance;
}