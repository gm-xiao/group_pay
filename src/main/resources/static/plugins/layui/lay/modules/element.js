/** layui-v2.2.5 MIT License By https://www.layui.com */
;
layui.define("jquery",
    function(i) {
        "use strict";
        var t = layui.$,
            a = (layui.hint(), layui.device()),
            e = "element",
            l = "layui-this",
            n = "layui-show",
            s = function() {
                this.config = {}
            };
        s.prototype.set = function(i) {
            var a = this;
            return t.extend(!0, a.config, i),
                a
        },
            s.prototype.on = function(i, t) {
                return layui.onevent.call(this, e, i, t)
            },
            s.prototype.tabAdd = function(i, a) {
                var e = ".layui-tab-title",
                    l = t(".layui-tab[lay-filter=" + i + "]"),
                    n = l.children(e),
                    s = n.children(".layui-tab-bar"),
                    o = l.children(".layui-tab-content"),
                    c = '<li lay-id="' + (a.id || "") + '">' + (a.title || "unnaming") + "</li>";
                return s[0] ? s.before(c) : n.append(c),
                    o.append('<div class="layui-tab-item">' + (a.content || "") + "</div>"),
                    y.hideTabMore(!0),
                    y.tabAuto(),
                    this
            },
            s.prototype.tabDelete = function(i, a) {
                var e = ".layui-tab-title",
                    l = t(".layui-tab[lay-filter=" + i + "]"),
                    n = l.children(e),
                    s = n.find('>li[lay-id="' + a + '"]');
                return y.tabDelete(null, s),
                    this
            },
            s.prototype.tabChange = function(i, a) {
                var e = ".layui-tab-title",
                    l = t(".layui-tab[lay-filter=" + i + "]"),
                    n = l.children(e),
                    s = n.find('>li[lay-id="' + a + '"]');
                return y.tabClick.call(s[0], null, null, s),
                    this
            },
            s.prototype.tab = function(i) {
                i = i || {},
                    v.on("click", i.headerElem,
                        function(a) {
                            var e = t(this).index();
                            y.tabClick.call(this, a, e, null, i)
                        })
            },
            s.prototype.progress = function(i, a) {
                var e = "layui-progress",
                    l = t("." + e + "[lay-filter=" + i + "]"),
                    n = l.find("." + e + "-bar"),
                    s = n.find("." + e + "-text");
                return n.css("width", a),
                    s.text(a),
                    this
            };
        var o = ".layui-nav",
            c = "layui-nav-item",
            r = "layui-nav-bar",
            u = "layui-nav-tree",
            d = "layui-nav-child",
            h = "layui-nav-more",
            f = "layui-anim layui-anim-upbit",
            y = {
                tabClick: function(i, a, s, o) {
                    o = o || {};
                    var c = s || t(this),
                        a = a || c.parent().children("li").index(c),
                        r = o.headerElem ? c.parent() : c.parents(".layui-tab").eq(0),
                        u = o.bodyElem ? t(o.bodyElem) : r.children(".layui-tab-content").children(".layui-tab-item"),
                        d = c.find("a"),
                        h = r.attr("lay-filter");
                    "javascript:;" !== d.attr("href") && "_blank" === d.attr("target") || (c.addClass(l).siblings().removeClass(l), u.eq(a).addClass(n).siblings().removeClass(n)),
                        layui.event.call(this, e, "tab(" + h + ")", {
                            elem: r,
                            index: a
                        })
                },
                tabDelete: function(i, a) {
                    var n = a || t(this).parent(),
                        s = n.index(),
                        o = n.parents(".layui-tab").eq(0),
                        c = o.children(".layui-tab-content").children(".layui-tab-item"),
                        r = o.attr("lay-filter");
                    n.hasClass(l) && (n.next()[0] ? y.tabClick.call(n.next()[0], null, s + 1) : n.prev()[0] && y.tabClick.call(n.prev()[0], null, s - 1)),
                        n.remove(),
                        c.eq(s).remove(),
                        setTimeout(function() {
                                y.tabAuto()
                            },
                            50),
                        layui.event.call(this, e, "tabDelete(" + r + ")", {
                            elem: o,
                            index: s
                        })
                },
                tabAuto: function() {
                    var i = "layui-tab-more",
                        e = "layui-tab-bar",
                        l = "layui-tab-close",
                        n = this;
                    t(".layui-tab").each(function() {
                        var s = t(this),
                            o = s.children(".layui-tab-title"),
                            c = (s.children(".layui-tab-content").children(".layui-tab-item"), 'lay-stope="tabmore"'),
                            r = t('<span class="layui-unselect layui-tab-bar" ' + c + "><i " + c + ' class="layui-icon">&#xe61a;</i></span>');
                        if (n === window && 8 != a.ie && y.hideTabMore(!0), s.attr("lay-allowClose") && o.find("li").each(function() {
                            var i = t(this);
                            if (!i.find("." + l)[0]) {
                                var a = t('<i class="layui-icon layui-unselect ' + l + '">&#x1006;</i>');
                                a.on("click", y.tabDelete),
                                    i.append(a)
                            }
                        }), o.prop("scrollWidth") > o.outerWidth() + 1) {
                            if (o.find("." + e)[0]) return;
                            o.append(r),
                                s.attr("overflow", ""),
                                r.on("click",
                                    function(t) {
                                        o[this.title ? "removeClass": "addClass"](i),
                                            this.title = this.title ? "": "收缩"
                                    })
                        } else o.find("." + e).remove(),
                            s.removeAttr("overflow")
                    })
                },
                hideTabMore: function(i) {
                    var a = t(".layui-tab-title");
                    i !== !0 && "tabmore" === t(i.target).attr("lay-stope") || (a.removeClass("layui-tab-more"), a.find(".layui-tab-bar").attr("title", ""))
                },
                clickThis: function() {
                    var i = t(this),
                        a = i.parents(o),
                        n = a.attr("lay-filter"),
                        s = i.find("a"),
                        c = "string" == typeof i.attr("lay-unselect");
                    i.find("." + d)[0] || ("javascript:;" !== s.attr("href") && "_blank" === s.attr("target") || c || (a.find("." + l).removeClass(l), i.addClass(l)), layui.event.call(this, e, "nav(" + n + ")", i))
                },
                clickChild: function() {
                    var i = t(this),
                        a = i.parents(o),
                        n = a.attr("lay-filter");
                    a.find("." + l).removeClass(l),
                        i.addClass(l),
                        layui.event.call(this, e, "nav(" + n + ")", i)
                },
                showChild: function() {
                    var i = t(this),
                        a = i.parents(o),
                        e = i.parent(),
                        l = i.siblings("." + d);
                    a.hasClass(u) && (l.removeClass(f), e["none" === l.css("display") ? "addClass": "removeClass"](c + "ed"))
                },
                collapse: function() {
                    var i = t(this),
                        a = i.find(".layui-colla-icon"),
                        l = i.siblings(".layui-colla-content"),
                        s = i.parents(".layui-collapse").eq(0),
                        o = s.attr("lay-filter"),
                        c = "none" === l.css("display");
                    if ("string" == typeof s.attr("lay-accordion")) {
                        var r = s.children(".layui-colla-item").children("." + n);
                        r.siblings(".layui-colla-title").children(".layui-colla-icon").html("&#xe602;"),
                            r.removeClass(n)
                    }
                    l[c ? "addClass": "removeClass"](n),
                        a.html(c ? "&#xe61a;": "&#xe602;"),
                        layui.event.call(this, e, "collapse(" + o + ")", {
                            title: i,
                            content: l,
                            show: c
                        })
                }
            };
        s.prototype.init = function(i, e) {
            var l = function() {
                    return e ? '[lay-filter="' + e + '"]': ""
                } (),
                s = {
                    tab: function() {
                        y.tabAuto.call({})
                    },
                    nav: function() {
                        var i = 200,
                            e = {},
                            s = {},
                            p = {},
                            v = function(l, o, c) {
                                var r = t(this),
                                    y = r.find("." + d);
                                o.hasClass(u) ? l.css({
                                    top: r.position().top,
                                    height: r.children("a").height(),
                                    opacity: 1
                                }) : (y.addClass(f), l.css({
                                    left: r.position().left + parseFloat(r.css("marginLeft")),
                                    top: r.position().top + r.height() - l.height()
                                }), e[c] = setTimeout(function() {
                                        l.css({
                                            width: r.width(),
                                            opacity: 1
                                        })
                                    },
                                    a.ie && a.ie < 10 ? 0 : i), clearTimeout(p[c]), "block" === y.css("display") && clearTimeout(s[c]), s[c] = setTimeout(function() {
                                        y.addClass(n),
                                            r.find("." + h).addClass(h + "d")
                                    },
                                    300))
                            };
                        t(o + l).each(function(a) {
                            var l = t(this),
                                o = t('<span class="' + r + '"></span>'),
                                f = l.find("." + c);
                            l.find("." + r)[0] || (l.append(o), f.on("mouseenter",
                                function() {
                                    v.call(this, o, l, a)
                                }).on("mouseleave",
                                function() {
                                    l.hasClass(u) || (clearTimeout(s[a]), s[a] = setTimeout(function() {
                                            l.find("." + d).removeClass(n),
                                                l.find("." + h).removeClass(h + "d")
                                        },
                                        300))
                                }), l.on("mouseleave",
                                function() {
                                    clearTimeout(e[a]),
                                        p[a] = setTimeout(function() {
                                                l.hasClass(u) ? o.css({
                                                    height: 0,
                                                    top: o.position().top + o.height() / 2,
                                                    opacity: 0
                                                }) : o.css({
                                                    width: 0,
                                                    left: o.position().left + o.width() / 2,
                                                    opacity: 0
                                                })
                                            },
                                            i)
                                })),
                                f.each(function() {
                                    var i = t(this),
                                        a = i.find("." + d);
                                    if (a[0] && !i.find("." + h)[0]) {
                                        var e = i.children("a");
                                        e.append('<span class="' + h + '"></span>')
                                    }
                                    i.off("click", y.clickThis).on("click", y.clickThis),
                                        i.children("a").off("click", y.showChild).on("click", y.showChild),
                                        a.children("dd").off("click", y.clickChild).on("click", y.clickChild)
                                })
                        })
                    },
                    breadcrumb: function() {
                        var i = ".layui-breadcrumb";
                        t(i + l).each(function() {
                            var i = t(this),
                                a = "lay-separator",
                                e = i.attr(a) || "/",
                                l = i.find("a");
                            l.next("span[" + a + "]")[0] || (l.each(function(i) {
                                i !== l.length - 1 && t(this).after("<span " + a + ">" + e + "</span>")
                            }), i.css("visibility", "visible"))
                        })
                    },
                    progress: function() {
                        var i = "layui-progress";
                        t("." + i + l).each(function() {
                            var a = t(this),
                                e = a.find(".layui-progress-bar"),
                                l = e.attr("lay-percent");
                            e.css("width",
                                function() {
                                    return /^.+\/.+$/.test(l) ? 100 * new Function("return " + l)() + "%": l
                                } ()),
                            a.attr("lay-showPercent") && setTimeout(function() {
                                    e.html('<span class="' + i + '-text">' + l + "</span>")
                                },
                                350)
                        })
                    },
                    collapse: function() {
                        var i = "layui-collapse";
                        t("." + i + l).each(function() {
                            var i = t(this).find(".layui-colla-item");
                            i.each(function() {
                                var i = t(this),
                                    a = i.find(".layui-colla-title"),
                                    e = i.find(".layui-colla-content"),
                                    l = "none" === e.css("display");
                                a.find(".layui-colla-icon").remove(),
                                    a.append('<i class="layui-icon layui-colla-icon">' + (l ? "&#xe602;": "&#xe61a;") + "</i>"),
                                    a.off("click", y.collapse).on("click", y.collapse)
                            })
                        })
                    }
                };
            return s[i] ? s[i]() : layui.each(s,
                function(i, t) {
                    t()
                })
        },
            s.prototype.render = s.prototype.init;
        var p = new s,
            v = t(document);
        p.render();
        var b = ".layui-tab-title li";
        v.on("click", b, y.tabClick),
            v.on("click", y.hideTabMore),
            t(window).on("resize", y.tabAuto),
            i(e, p)
    });