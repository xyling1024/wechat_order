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
                                <th>订单id</th>
                                <th>姓名</th>
                                <th>手机号</th>
                                <th>地址</th>
                                <th>金额</th>
                                <th>订单状态</th>
                                <th>支付状态</th>
                                <th>创建时间</th>
                                <th colspan="2">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list orderDTOPage.getContent() as orderDTO >
                            <tr>
                                <td>${orderDTO.orderId}</td>
                                <td>${orderDTO.buyerName}</td>
                                <td>${orderDTO.buyerPhone}</td>
                                <td>${orderDTO.buyerAddress}</td>
                                <td>${orderDTO.orderAmount}</td>
                                <td>${orderDTO.getOrderStatusEnum().message}</td>
                                <td>${orderDTO.getPayStatusEnum().message}</td>
                                <td>${orderDTO.createTime}</td>
                                <td><a href="/seller/order/detail?orderId=${orderDTO.orderId}">详情</a></td>
                                <td>
                                    <#if orderDTO.getOrderStatusEnum().status == 0>
                                        <a href="/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
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

                <#--<div class="col-md-12 column">
                    <ul class="pagination pull-right">
                        <!-- 上一页 &ndash;&gt;
                        <#if currentPage lte 1>
                            <li class="disabled"><a href="#">上一页</a></li>
                        <#else >
                            <li><a href="/seller/order/list?page=${currentPage-1}&size=${size}">上一页</a></li>
                        </#if>
                        <!-- 遍历页码 &ndash;&gt;
                        <#list 1..orderDTOPage.getTotalPages() as index>
                            <#if currentPage == index>
                                <li class="disabled"><a href="#">${index}</a></li>
                            <#else >
                                <li><a href="/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                            </#if>
                        </#list>
                        <!-- 下一页 &ndash;&gt;
                        <#if currentPage gte orderDTOPage.getTotalPages()>
                            <li class="disabled"><a href="#">下一页</a></li>
                        <#else >
                            <li><a href="/seller/order/list?page=${currentPage+1}&size=${size}">下一页</a></li>
                        </#if>
                    </ul>
                </div>
        -->

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
            totalPages: ${orderDTOPage.getTotalPages()}, //总页数，获取从后台传过来的值
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
                location.href = "/seller/order/list?size=${size}&page=" + page;
            }
        };
        $('#pagintor').bootstrapPaginator(options);
    })
</script>

</html>




