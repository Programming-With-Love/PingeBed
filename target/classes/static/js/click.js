$("#select").click(function () {
    $("#upload").click();
});

// 将网址拷贝到剪贴板
function clipboard(obj) {
    let node = $(obj).parent().parent().prev();
    node.select();
    document.execCommand("Copy");
    bs4pop.notice("复制成功!", {position: "bottomright", type: "secondary"});
}