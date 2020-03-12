$(function() {
    axios.get('/api/getAllPics')
        .then(function (response) {
            console.log("历史记录根据您的IP地址生成");
            console.log(response);
            if (response.data.msg == "fail") {
                $(".ip").text("您的IP地址(" + response.data.data + ")");
                $(".count").text("找到0条记录");
                console.log("用户没有上传任何图片");
                return;
            }
            $(".ip").text("您的IP地址(" + response.data.data[0].ip + ")");
            $(".count").text("找到" + response.data.data.length + "条记录");
            $.each(response.data.data, function(i, value) {
                let year = this.path.split("/")[1];
                let date = this.path.split("/")[2];
                let src = location.origin + "/uploadImages" + this.path;
                // filename path ip
                let img = $("<li><a target='_blank' href=" + src + "><img height='200px' src=" + src + "><h3>" + year + "年" + date.split("-")[0] + "月" + date.split("-")[1] + "日</h3></a></li>");
                $("#grid").append(img);
                console.log(img.html());
            });
            new GridScrollFx(document.getElementById( 'grid' ), {
                viewportFactor : 0.4
            } );
        })
        .catch(function (error) {
            console.log(error);
        });
});