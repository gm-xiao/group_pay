<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>Group Pay</title>
    <base id="base" href="${basePath}"/>
    <link rel="shortcut icon" href="/image/favicon.ico"/>
    <link rel="stylesheet" href="${basePath}/plugins/layui/css/layui.css?v=${version}" media="all">
    <link rel="stylesheet" href="${basePath}/css/style.css?v=${version}" media="all">
    <link rel="stylesheet" href="${basePath}/css/font-awesome/css/font-awesome.css?v=${version}">
    <link rel="stylesheet" href="${basePath}/build/css/app.css?v=${version}" media="all">
</head>
<body data-type="admin">
<div class="layui-layout layui-layout-admin kit-layout-admin">
    <div class="layui-header" event="admin">
        <div class="layui-logo" style="margin-left: 10px;">Group Pay</div>
        <div class="layui-logo kit-logo-mobile">Pay</div>
        <ul class="layui-nav layui-layout-right kit-nav">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <i class="fa fa-user-circle ui-icon" aria-hidden="true" style="font-size: 20px;vertical-align: middle;"></i>
                </a>
                <dl class="layui-nav-child">
                    <#--<dd><a href="javascript:;">基本资料</a></dd>-->
                    <dd><a href="javascript:;" event="resetPwd">修改密码</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="javascript:;" class="logout"><i class="fa fa-sign-out" aria-hidden="true"></i> 注销</a></li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black kit-side">
        <div class="layui-side-scroll">
            <div class="kit-side-fold"><i class="fa fa-navicon" aria-hidden="true"></i></div>
            <ul class="layui-nav layui-nav-tree" lay-filter="kitNavbar" kit-navbar>
                <li class="layui-nav-item">
                    <a class="" href="javascript:;"><i class="fa fa-address-book ui-icon" aria-hidden="true"></i><span>订单管理</span></a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a href="javascript:;" data-url="/admin/customer" data-icon="&#xe7ae;" data-title="订单列表" kit-target data-id='0' kit-target>
                                <i class="fa fa-calendar ui-icon" aria-hidden="true"></i><span>订单列表</span></a>
                        </dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a class="" href="javascript:;"><i class="fa fa-address-book ui-icon" aria-hidden="true"></i><span>支付宝配置</span></a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a href="javascript:;" data-url="/admin/customer" data-icon="&#xe7ae;" data-title="APP支付" kit-target data-id='1' kit-target>
                                <i class="fa fa-calendar ui-icon" aria-hidden="true"></i><span>APP支付</span></a>
                        </dd>
                        <dd>
                            <a href="javascript:;" data-url="/admin/customer/card" data-icon="&#xe7ae;" data-title="扫码支付" kit-target data-id='2' kit-target>
                                <i class="fa fa-address-card ui-icon" aria-hidden="true"></i><span>扫码支付</span></a>
                        </dd>
                        <dd>
                            <a href="javascript:;" data-url="/admin/customer/card" data-icon="&#xe7ae;" data-title="手机支付" kit-target data-id='3' kit-target>
                                <i class="fa fa-address-card ui-icon" aria-hidden="true"></i><span>WEB支付</span></a>
                        </dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a class="" href="javascript:;"><i class="fa fa-address-book ui-icon" aria-hidden="true"></i><span>微信配置</span></a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a href="javascript:;" data-url="/admin/customer" data-icon="&#xe7ae;" data-title="公众号支付" kit-target data-id='1' kit-target>
                                <i class="fa fa-calendar ui-icon" aria-hidden="true"></i><span>公众号支付</span></a>
                        </dd>
                        <dd>
                            <a href="javascript:;" data-url="/admin/customer/card" data-icon="&#xe7ae;" data-title="APP支付" kit-target data-id='2' kit-target>
                                <i class="fa fa-address-card ui-icon" aria-hidden="true"></i><span>APP支付</span></a>
                        </dd>
                        <dd>
                            <a href="javascript:;" data-url="/admin/customer/card" data-icon="&#xe7ae;" data-title="代金券" kit-target data-id='3' kit-target>
                                <i class="fa fa-address-card ui-icon" aria-hidden="true"></i><span>代金券</span></a>
                        </dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a class="" href="javascript:;"><i class="fa fa-address-book ui-icon" aria-hidden="true"></i><span>系统管理</span></a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a href="javascript:;" data-url="/admin/customer" data-icon="&#xe7ae;" data-title="账户管理" kit-target data-id='0' kit-target>
                                <i class="fa fa-calendar ui-icon" aria-hidden="true"></i><span>账户管理</span></a>
                        </dd>
                    </dl>
                </li>
            </ul>
        </div>
    </div>
    <div class="layui-body" id="container">
        <!-- 内容主体区域 -->
        <div style="padding: 15px;"></div>
    </div>

    <div class="layui-footer">
        <!-- 底部固定区域 -->
        <#--2018 &copy;-->
        <#--<a href="">www.baidu.com</a>-->

    </div>
</div>

<script src="${basePath}/plugins/layui/layui.js?v=${version}"></script>
<script src="${basePath}/js/admin.js?v=${version}"></script>
</body>

</html>