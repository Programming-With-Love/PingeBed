$(function() {
    // 在页面第一次加载时设置dialog的位置
    setFocus();

    // 每次浏览器尺寸发生改变
    $(window).resize(function() {
        setFocus();
    });

    // 点击上传后的选择窗口
    $("#selectButton").click(function() {
        $("#selectFile").fadeIn(500);
        // 内容
        $(".upload-file").show();
        $(".clone-file").hide();
    });

    // 关闭窗口
    $(".dialog-close").click(function() {
        $("#selectFile").fadeOut(500);

        // 标题的效果
        $("#clone").removeClass("selected");
        $("#cloneArea").val("");
        $("#local-upload").addClass("selected");
        $("#upload").val("");
    });

    // 点击url上传的效果
    $("#clone").click(function() {
        // 标题的效果
        $("#local-upload").removeClass("selected");
        $("#clone").addClass("selected");

        // 内容
        $(".upload-file").hide();
        $(".clone-file").show();

        // 清空input里选择的文件
        $("#upload")[0].value= '';
    });

    // 点击普通上传的效果
    $("#local-upload").click(function() {
        // 标题的效果
        $("#clone").removeClass("selected");
        $("#local-upload").addClass("selected");

        // 内容
        $(".upload-file").show();
        $(".clone-file").hide();
    });
});