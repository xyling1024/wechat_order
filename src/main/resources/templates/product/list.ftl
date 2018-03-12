<html>
<#include "../commons/header.ftl">
<body>
    <div id="wrapper" class="toggled">
        <#--边栏 sidebar-->
        <#include "../commons/nav.ftl">
        <#--主要内容-->
        <div id="page-content-wrapper">
            <div class="container-fluid">
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <table class="table table-bordered table-hover table-condensed">
                            <thead>
                            <tr>
                                <th>商品id</th>
                                <th>名称</th>
                                <th>图片</th>
                                <th>单价</th>
                                <th>库存</th>
                                <th>描述</th>
                                <th>类目</th>
                                <th>创建时间</th>
                                <th>修改时间</th>
                                <th colspan="2">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list productInfoPage.getContent() as productInfo >
                            <tr>
                                <td>${productInfo.productId}</td>
                                <td>${productInfo.productName}</td>
                                <td>
                                    <img src="${productInfo.productIcon}" alt="${productInfo.productDesc}" width="100" height="100">
                                </td>
                                <td>${productInfo.productPrice}</td>
                                <td>${productInfo.productStock}</td>
                                <td>${productInfo.productDesc}</td>
                                <td>${productInfo.categoryType}</td>
                                <td>${productInfo.createTime}</td>
                                <td>${productInfo.updateTime}</td>
                                <td><a href="/sell/seller/product/index?productId=${productInfo.productId}">修改</a></td>
                                <td>
                                    <#if productInfo.getProductStatusEnum().status == 0>
                                        <a href="/sell/seller/product/off_sale?productId=${productInfo.productId}">下架</a>
                                    <#else >
                                        <a href="/sell/seller/product/on_sale?productId=${productInfo.productId}">上架</a>
                                    </#if>
                                </td>
                            </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>

                    <!-- 分页 -->
                    <div class="col-md-6 pull-right">
                        <ul id="pagintor"></ul>
                    </div>

                </div>
            </div>
        </div>
    </div>
</body>

<script type="text/javascript">
    $(function () {
        /*分页*/
        options = {
            bootstrapMajorVersion: 3, //对应的bootstrap版本,2.X 的 分页必须使用div元素，3.X分页的必须使用ul元素
            currentPage: ${currentPage}, //当前页数，获取从后台传过来的值
            alignment: "center",
            numberOfPages: 10, //分页栏显示页面数
            totalPages: ${productInfoPage.getTotalPages()}, //总页数，获取从后台传过来的值
            shouldShowPage: true, //是否显示该按钮
            itemTexts: function (type, page, current) { //设置显示的样式，默认是箭头
                switch (type) {
                    case "first":
                        return "首页";
                    case "prev":
                        return "上一页";
                    case "next":
                        return "下一页";
                    case "last":
                        return "末页";
                    case "page":
                        return page;
                }
            },
            //点击事件
            onPageClicked: function (event, originalEvent, type, page) {
                location.href = "/sell/seller/product/list?size=${size}&page=" + page;
            }
        };
        $('#pagintor').bootstrapPaginator(options);
    })
</script>

</html>




