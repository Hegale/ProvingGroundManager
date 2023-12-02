// thwhole-pVlPbA0MBb [pVlPbA0MBb]
(function() {
  $(".thwhole-pVlPbA0MBb").each(function() {
    const $block = $(this);
    const el = $(".thwhole-pVlPbA0MBb .visual-container .control-wrap");
    const txtBox = $(".thwhole-pVlPbA0MBb .visual-swiper .visual-text-box");
    $(function() {
      var visualSwiper = new Swiper(".thwhole-pVlPbA0MBb .visual-swiper", {
        speed: 600,
        parallax: true,
        loop: true,
        allowTouchMove: false,
        touchEventsTarget: "wrapper",
        slidesPerView: "auto",
        autoplay: {
          delay: 2500,
          disableOnInteraction: false,
        },
        pagination: {
          el: ".thwhole-pVlPbA0MBb .visual-swiper .control-wrap .swiper-pagination",
          clickable: "true",
        },
        on: {
          init: function() {
            $(".thwhole-pVlPbA0MBb .visual-swiper .control-wrap .pagination_fraction .all").text(("0" + this.loopedSlides).slice(-2));
          },
          slideChangeTransitionStart: function() {
            $(".thwhole-pVlPbA0MBb .visual-swiper .control-wrap .pagination_fraction .current").text("0" + (this.realIndex + 1)).slice(-2);
          },
        },
      });
      if ($(".visual-swiper").length > 0) {
        $(window).on({
          load: function() {
            boxHeight();
          },
          resize: function() {
            boxHeight();
          },
          keyup: function(e) {
            if (e.keyCode == 116 || (e.ctrlKey == true && e.keyCode == 82)) {
              boxHeight();
            }
          },
        });
      }
      var isMobile = /Android|webOS|iPhone|iPad|iPod|BlackBerry/i.test(navigator.userAgent) ? true : false;
      if (isMobile) {
        $(".thwhole-pVlPbA0MBb .visual-container .pagination_fraction").css({
          top: "-6rem",
        });
        $(".thwhole-pVlPbA0MBb .visual-container .control-bottom").css({
          bottom: "-8rem",
        });
      }
      // Swiper Play, Pause Button
      const pauseButton = $block.find(".swiper-button-pause");
      const playButton = $block.find(".swiper-button-play");
      playButton.hide();
      pauseButton.show();
      pauseButton.on("click", function() {
        visualSwiper.autoplay.stop();
        playButton.show();
        pauseButton.hide();
      });
      playButton.on("click", function() {
        visualSwiper.autoplay.start();
        playButton.hide();
        pauseButton.show();
      });
    });

    function boxHeight() {
      const el = $(".thwhole-pVlPbA0MBb .visual-container .control-wrap");
      const txtBox = $(".thwhole-pVlPbA0MBb .visual-swiper .visual-text-box");
      el.css({
        height: txtBox.height(),
        transition: "all .5s ",
      });
      $(".load").text(el.position().top);
    }
  });
})();
// opilsol-N8 [ZxlPBA0Mcg]
(function() {
  $(function() {
    var swiper = new Swiper(".opilsol-N8 .slide-container", {
      // loop: "true",
      allowTouchMove: false,
      touchEventsTarget: "wrapper",
      autoplay: {
        delay: 2500,
        disableOnInteraction: false,
      },
      navigation: {
        nextEl: ".opilsol-N8 .swiper-button-next",
        prevEl: ".opilsol-N8 .swiper-button-prev",
      },
      breakpoints: {
        360: {
          slidesPerView: 1.1,
          spaceBetween: 20,
          centeredSlides: true,
        },
        768: {
          slidesPerView: 2.3,
          spaceBetween: 20,
        },
        922: {
          slidesPerView: 3.182,
          spaceBetween: 20,
        },
        1025: {
          slidesPerView: 2.75,
          spaceBetween: 30,
        },
      },
    });
    $(".opilsol-N8 .slide-container .swiper-slide").hover(function() {
      swiper.autoplay.stop();
    }, function() {
      swiper.autoplay.start();
    });
  });
})();
// opilsol-N1 [VHlpcO73j3]
(function() {
  $(function() {
    $(".opilsol-N1").each(function() {
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
// personal-IulPb9T9gT [VjLpnvP94I]
(function() {
  $(function() {
    $(".personal-IulPb9T9gT").each(function() {
      const $block = $(this);
      const $calendar = $block.find(".contents-date");
      const weekday = new Array(7);
      weekday[0] = "일";
      weekday[1] = "월";
      weekday[2] = "화";
      weekday[3] = "수";
      weekday[4] = "목";
      weekday[5] = "금";
      weekday[6] = "토";
      // Date Range Picker
      $calendar.dateRangePicker({
        locale: {
          format: "YYYY-MM-DD",
        },
        container: ".personal-IulPb9T9gT .calendar-wrap",
        language: "custom",
        inline: true,
        alwaysOpen: true,
        stickyMonths: true,
        hoveringTooltip: false,
        extraClass: "date-range-picker19",
        SingleMonth: "false",
        separator: " to ",
        getValue: function() {
          if ($(".date-range200").val() && $(".date-range201").val()) return ($(".date-range200").val() + " to " + $(".date-range201").val());
          else return "";
        },
        setValue: function(s, s1, s2) {
          let first = new Date(s1).getDay();
          let last = new Date(s2).getDay();
          console.log(first);
          $(".date-range200").val(s1.replaceAll("-", ".") + weekday[first]);
          $(".date-range201").val(s2.replaceAll("-", ".") + weekday[last]);
        },
        customArrowPrevSymbol: '<i class="icon icon-arrow-left"></i>',
        customArrowNextSymbol: '<i class="icon icon-arrow-right"></i>',
      }).bind("datepicker-first-date-selected", function(event, obj) {
        console.log(obj);
        if ($(".last-date-selected") && !$(".first-date-selected")) {
          setTimeout(function() {
            $(".first-date-selected").parent().addClass("first");
          }, 10);
        } else {
          $("td").removeClass();
        }
      }).bind("datepicker-change", function(event, obj) {
        console.log(obj);
        $(".first-date-selected").parent().addClass("first");
        setTimeout(function() {
          if (!$(".last-date-selected").parent().addClass("last")) {
            $(".last-date-selected").parent().addClass("last");
          } else if ($(".last-date-selected").parent().addClass("last")) {
            $(".first-date-selected").parent().addClass("first");
          }
        }, 10);
      });
      // Amount Count Button Click Event
      $block.find(".contents-amount").each(function() {
        const $this = $(this);
        const $amountNumElement = $this.find(".contents-amount-num span");
        $this.on("click", ".btn-minus", function() {
          let amountNum = parseInt($amountNumElement.text());
          if (amountNum > 1) {
            amountNum--;
          }
          $amountNumElement.text(amountNum);
        });
        $this.on("click", ".btn-plus", function() {
          let amountNum = parseInt($amountNumElement.text());
          amountNum++;
          $amountNumElement.text(amountNum);
        });
      });
    });
  });
})();