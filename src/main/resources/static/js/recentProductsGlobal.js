$(document).ready(function () {
  getRecentProducts();
  function getRecentProducts() {
    var cookies = JSON.parse(getCookie("recentProducts"));
    const recentProductsContainer = $("#recentProductsUL");
    if (cookies != null) {
      cookies.forEach((cookie, index) => {
        var itemHref = cookie.itemHref;
        var recentItemNm = cookie.recentItemNm.replace(/\+/g, ' '); // '+'를 공백으로 대체
        var imageSrc = cookie.recentImage;
        var price = cookie.price;

        var li = $("<li class='recentProductList'>");
        if (index === 0) {
          li.addClass('active'); // 첫 번째 상품  active 클래스 추가 css 설정 hidden 풀림.
        }

        var productLink = $("<a class='recentProductLink'>").attr("href", itemHref);
        var imageElement = $("<img class='recentProductIMG'>").attr("src", imageSrc);
        var name = $("<p class='recentProductName'>").text(recentItemNm);
        var priceElement = $("<p class='recentProductPrice'>").text(price + "원");

        li.append(productLink);
        productLink.append(imageElement);
        productLink.append(name);
        productLink.append(priceElement);

        recentProductsContainer.append(li);
      });
    } else {
      var li = $("<li class='recentProductList'>");
      var nameParagraph = $("<p class='recentProductName'>").text("최근 본 상품 없음");
      li.append(nameParagraph);
      recentProductsContainer.append(li);
    }
  }

  function getCookie(cookieName) {
    var name = cookieName + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var cookieArray = decodedCookie.split(";");
    for (var i = 0; i < cookieArray.length; i++) {
      var cookie = cookieArray[i].trim();
      if (cookie.indexOf(name) === 0) {
        return cookie.substring(name.length, cookie.length);
      }
    }
    return null;
  }
      $('#prevButton').click(function() {
        changeSlide(-1);
      });
      $('#nextButton').click(function() {
        changeSlide(1);
      });
      var currentIndex = 0;
      var productListItems = document.querySelectorAll('.recentProductList');
      function changeSlide(direction) {
        productListItems[currentIndex].classList.remove('active');
        currentIndex = (currentIndex + direction + productListItems.length) % productListItems.length;
        productListItems[currentIndex].classList.add('active');
      }
});
