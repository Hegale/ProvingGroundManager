// 불가능한 날짜 지정


// thwhole-enlPbA0Maj [enlPbA0Maj]
(function() {
  $(function() {
    $(".thwhole-enlPbA0Maj").each(function() {
      const $block = $(this);
      // Header Scroll
      $(window).on("load scroll", function() {
        const $thisTop = $(this).scrollTop();
        if ($thisTop > 0) {
          $block.addClass("block-scroll-active");
        } else {
          $block.removeClass("block-scroll-active");
        }
      });
      // Gnb
      $block.find(".header-center").on("mouseover", function() {
        if (window.innerWidth > 992) {
          $block.addClass("block-active");
        }
      });
      $block.find(".header-center").on("mouseout", function() {
        if (window.innerWidth > 992) {
          $block.removeClass("block-active");
        }
      });
      // Gnb DecoLine
      $block.find(".header-gnbitem").each(function() {
        const $this = $(this);
        $this.on("mouseover", function() {
          if (window.innerWidth > 992) {
            $this.find(".header-gnblink").addClass("on");
          }
        });
        $this.on("mouseout", function() {
          if (window.innerWidth > 992) {
            $this.find(".header-gnblink").removeClass("on");
          }
        });
      });
      // Full Gnb
      $block.find(".btn-allmenu").on("click", function() {
        $block.find(".header-fullmenu").addClass("fullmenu-active");
      });
      $block.find(".fullmenu-close").on("click", function() {
        $block.find(".header-fullmenu").removeClass("fullmenu-active");
      });
      // Full Gnb Search
      $block.find(".btn-search").on("click", function() {
        $block.find(".header-search").addClass("fullmenu-active");
      });
      $block.find(".fullmenu-close").on("click", function() {
        // $block.find(".header-fullmenu").removeClass("fullmenu-active");
        $block.find(".header-search").removeClass("fullmenu-active");
      });
      // Full Gnb DecoLine
      $block.find(".fullmenu-gnbitem").each(function() {
        const $this = $(this);
        $this.on("mouseover", function() {
          if (window.innerWidth > 992) {
            $this.find(".fullmenu-gnblink").addClass("on");
          }
        });
        $this.on("mouseout", function() {
          if (window.innerWidth > 992) {
            $this.find(".fullmenu-gnblink").removeClass("on");
          }
        });
      });
      $block.find(".fullmenu-gnblink").each(function() {
        const $this = $(this);
        $this.on("click", function(e) {
          if (window.innerWidth > 992) {
            e.preventDefault();
          }
          if (window.innerWidth <= 992) {
            $this.toggleClass("on").next(".fullmenu-sublist").slideToggle().closest(".fullmenu-gnbitem").siblings().find(".fullmenu-gnblink").removeClass("on").next(".fullmenu-sublist").slideUp();
          }
        });
      });
    });
    $(window).on({
      resize: function() {
        if ($(window).width() > 992) {
          $(".fullmenu-sublist").slideDown(0);
        }
        if ($(window).width() <= 992) {
          $(".fullmenu-gnblink").removeClass("on").next(".fullmenu-sublist").slideUp(0);
        }
      },
    });
    // Header Mobile 1Depth Click
    if (window.innerWidth <= 992) {
      $block.find(".header-gnbitem").each(function() {
        const $gnblink = $(this).find(".header-gnblink");
        const $sublist = $(this).find(".header-sublist");
        if ($sublist.length) {
          $gnblink.attr("href", "javascript:void(0);");
        }
      });
    }
  });
})();
// thwhole-PYlPbA0mbO [PYlPbA0mbO]
(function() {
  $(function() {
    var swiper = new Swiper(".thwhole-PYlPbA0mbO .slide-container", {
      loop: "true",
      allowTouchMove: false,
      touchEventsTarget: "wrapper",
      navigation: {
        nextEl: ".thwhole-PYlPbA0mbO .swiper-button-next",
        prevEl: ".thwhole-PYlPbA0mbO .swiper-button-prev",
      },
      /* autoplay: {
        delay: 2500,
        disableOnInteraction: false,
      }, */
      breakpoints: {
        320: {
          slidesPerView: 1.1446,
          spaceBetween: 20,
        },
        768: {
          slidesPerView: 2.427,
          spaceBetween: 20,
        },
        922: {
          slidesPerView: 3.232,
          spaceBetween: 20,
        },
        1025: {
          slidesPerView: 3.75,
          spaceBetween: 40,
        },
      },
    });
    $(".thwhole-PYlPbA0mbO .slide-container .swiper-slide").hover(function() {
      swiper.autoplay.stop();
    }, function() {
      swiper.autoplay.start();
    });
  });
})();