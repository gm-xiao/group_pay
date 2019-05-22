var message;
layui.config({
    base: '/build/js/'
}).use(['app', 'message'], function() {
    var app = layui.app,
        $ = layui.jquery,
        layer = layui.layer;
    message = layui.message;
    app.set({
        type: 'iframe'
    }).init();
});