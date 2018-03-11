<html>
<header>
    <meta charset="UTF-8" />
    <title>商家订单详情</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</header>

<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-6 column">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>订单id</th>
                    <th>订单总金额</th>
                    <th>订单状态</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${orderDTO.orderId}</td>
                    <td>${orderDTO.orderAmount}</td>
                    <td>${orderDTO.getOrderStatusEnum().message}</td>
                </tr>

                </tbody>
            </table>
        </div>
        <div class="col-md-12 column">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>商品id</th>
                    <th>商品名称</th>
                    <th>价格</th>
                    <th>数量</th>
                    <th>总额</th>
                </tr>
                </thead>
                <tbody>
                <#list orderDTO.orderDetailList as orderDetail>
                <tr>
                    <td>${orderDetail.productId}</td>
                    <td>${orderDetail.productName}</td>
                    <td>${orderDetail.productPrice}</td>
                    <td>${orderDetail.productQuantity}</td>
                    <td>${orderDetail.productPrice * orderDetail.productQuantity}</td>
                </tr>
                </#list>
                </tbody>
            </table>
        </div>

        <#--按钮-->
        <#if orderDTO.getOrderStatusEnum().status == 0><!-- 新订单 -->
            <div class="col-md-12 column">
                <a href="/sell/seller/order/finish?orderId=${orderDTO.orderId}" type="button" class="btn btn-default btn-primary">完结订单</a>
                <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}" type="button" class="btn btn-default btn-danger">取消订单</a>
            </div>
        </#if>
    </div>
</div>

</body>

</html>