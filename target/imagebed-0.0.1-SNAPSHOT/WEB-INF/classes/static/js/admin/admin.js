function login() {
    var password = $("#login-password").val();
    axios.get('/api/login', {
        params: {
            "password": password
        }
    })
        .then(function (response) {
            if (response.data.msg == "success") {
                console.log("登录成功");

                var conf = response.data.data;
                if (conf[0] == "true") {
                    $(".switch").toggleClass("switch-on");
                    $("#turn").attr("checked", true);
                }
                $("#imageUploadCount").val(conf[1]);
                $("#size").val(conf[2]);
                $("#password").val(conf[3]);
                $("#version").val(conf[4]);
                overturn();

            } else {
                $("#login-password").val("");
                $("#login-password").css("border", "red solid 1px");
            }
            console.log(response);
        })
        .catch(function (error) {
            console.log(error);
        });
}

function changeConf() {
    var list = ["anonymous", "imageUploadCount", "size", "password", "version"];
    $.each(list, function (i, value) {
        if (i == 0) {
            var param = {"key" : value, "value": $("#turn").is(":checked")};
        } else {
            var param = {"key" : value, "value" : $("#" + value).val()}
        }
        axios.get('/api/setConf', {
            params: param
        })
            .then(function (response) {
                if (response.data.msg == "success") {
                } else {
                    bs4pop.notice("配置错误，请重试", {position: "bottomright", type: "secondary"});
                }
            })
            .catch(function (response) {
                console.log(response);
            })
    });
    bs4pop.notice("配置已更新，请刷新此页面", {position: "bottomright", type: "secondary"});
}


function reload() {
    axios.get('/api/reload')
        .then(function (response) {
            if (response.data.msg == "success") {
                bs4pop.notice("config.ini文件重载成功!请刷新页面", {position: "bottomright", type: "secondary"});
                overturnback();
            } else {
                bs4pop.notice("重载出错，请检查config.ini文件", {position: "bottomright", type: "secondary"});
            }
        })
}

function logout() {
    axios.get('/api/logout')
        .then(function (response) {
            if (response.data.msg == "success") {
                bs4pop.notice("即将注销", {position: "bottomright", type: "secondary"});
                overturnback();
            }
        })
}


function overturn() {
    $(".login-wrapper").css("transform", "rotateY(90deg)");
    $(".login-wrapper").css("opacity", "0");
    setTimeout(function () {
        $(".admin-wrapper").css("transform", "rotateY(0deg)");
        $(".admin-wrapper").css("opacity", "1");
    }, 1000);
    $("#login-password").val("");
}

function overturnback() {
    $(".admin-wrapper").css("transform", "rotateY(90deg)");
    $(".admin-wrapper").css("opacity", "0");
    setTimeout(function () {
        $(".login-wrapper").css("transform", "rotateY(0deg)");
        $(".login-wrapper").css("opacity", "1");
    }, 1000);
}