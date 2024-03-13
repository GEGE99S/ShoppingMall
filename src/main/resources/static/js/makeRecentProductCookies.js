$(document).ready(function() {
        console.log('상품상세 조회!');
        const itemId = $("#itemId").val();
        const repImg = $("#repImg").val(); // 대표 이미지
        const itemPrice = $("#price").val();
        const itemNm = $("#itemNm").text();

        console.log('아이템 이름'+itemNm);
        console.log('아이템 이미지'+repImg);

        const url = "/clickProduct/recent-products/" + itemId;
        // post csrf
        console.log('hi makeRecentProductCookies');
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        var paramData = {
            itemHref: window.location.href,
            imgUrl: repImg,
            itemPrice: itemPrice,
            itemNm: itemNm,
            itemId: itemId,
        };
        $.ajax({
            url: url,
            type: "POST",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(paramData),
            cache: false,
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function(response, status) {
                console.log("쿠키 완료!");
            },
            error: function(error) {
                console.log("서버 응답:", error);
            }
        });
});