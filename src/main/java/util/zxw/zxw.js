var zxwc = "698";
var zxwf = "_1_1";
var diary = {
  a: Math.random(), file: zxwc + zxwf + yvid + code
};
var s = "../fzsplayer/player1812_S.swf";
var ggurl = encodeURIComponent((window.location.href));
jwplayer("myElement").setup({
  file: zxwc + zxwf + yvid + code,
  width: 960,
  height: 600,
  flashplayer: s,
  type: "study",
  text: n(),
  allowfull: true,
  istest: true,
  stagevideo: false,
  autohidecontrols: false,
  showdownload: true,
  autoplay: false,
  showfull: true,
  tag: "http://www.51zxw.net/fzsplayer/ptext.asp?id=" + zxwc,
  ad: {
    time: 8, buffered: 8, playerwidth: 960, playerheight: 600,

    startad: {
      type: "myad",
      width: 500,
      height: 400,
      adurl: "http://www.51zxw.net/news_vvvs_photos.swf?cid=" + zxwc
    },

    pausead: {
      type: defaultgg,
      width: defaultww,
      height: defaulthh,
      param: {
        url: "http://cpro.baidustatic.com/cpro/ui/baiduPatch.swf",
        id: "u2167424",
        tu: "51zxw.net",
        cpro_url: "www.51zxw.net",
        cpro_template: "baiduxml_tiepian_400_300",
        cpro_channel: "0",
        cpro_client: "79031046_tp_cpr"
      },
      ad_tag: "http://googleads.g.doubleclick.net/pagead/ads?client=ca-video-pub-7598233762967127&ad_type=image&channel=b1&description_url="
          + ggurl

    }, stopad: {
      type: defaultgg,
      width: defaultww,
      height: defaulthh,
      param: {
        url: "http://cpro.baidustatic.com/cpro/ui/baiduPatch.swf",
        id: "u2167424",
        tu: "51zxw.net",
        cpro_url: "www.51zxw.net",
        cpro_template: "baiduxml_tiepian_400_300",
        cpro_channel: "0",
        cpro_client: "79031046_tp_cpr"
      },
      ad_tag: "http://googleads.g.doubleclick.net/pagead/ads?client=ca-video-pub-7598233762967127&ad_type=image&channel=b1&description_url="
          + ggurl
    }, bannerad_cancle: {
      type: "google",
      width: 700,
      height: 80,
      bottom: 40,
      interval: 10,
      ad_tag: "http://googleads.g.doubleclick.net/pagead/ads?client=ca-video-pub-7598233762967127&ad_type=image&description_url="
          + ggurl,
      closebtn: true
    }
  },
  defaultline: defaultline
});
