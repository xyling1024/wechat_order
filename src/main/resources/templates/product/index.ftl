<html>
<#include "../commons/header.ftl">
<body>

    <div id="wrapper" class="toggled">
        <#--边栏 sidebar-->
        <#include "../commons/nav.ftl">
        <#--主要内容-->
        <div id="page-content-wrapper">
            <div class="container-fluid">
                <div class="col-md-12 column">
                    <form role="form" action="/seller/product/save" method="post">
                        <div class="form-group">
                            <label for="productName">名称</label>
                            <input type="text" class="form-control" name="productName" value="${(productInfo.productName)!""}" />
                        </div>
                        <div class="form-group">
                            <label for="productPrice">价格</label>
                            <input type="text" class="form-control" name="productPrice" value="${(productInfo.productPrice)!""}" />
                        </div>
                        <div class="form-group">
                            <label for="productStock">库存</label>
                            <input type="number" class="form-control" name="productStock" value="${(productInfo.productStock)!""}" />
                        </div>
                        <div class="form-group">
                            <label for="productDesc">描述</label>
                            <input type="text" class="form-control" name="productDesc" value="${(productInfo.productDesc)!""}" />
                        </div>
                        <div class="form-group">
                            <div><strong>图片</strong></div>
                            <img src="${(productInfo.productIcon)!""}" id="productIcon" width="300" height="300">
                            <input type="text" class="form-control" name="productIcon" value="${(productInfo.productIcon)!""}" />
                        </div>
                        <div class="form-group">
                            <label for="categoryType">类目</label>
                            <select name="categoryType" class="form-control">
                                <#list categoryList as category>
                                    <option value="${category.categoryType}"
                                            <#--如果存在 用?? -->
                                        <#if (productInfo.categoryType)?? && productInfo.categoryType == category.categoryType>
                                            selected
                                        </#if>
                                    >${category.categoryName}</option>
                                </#list>
                            </select>
                        </div>
                        <input hidden type="text" name="productId" value="${(productInfo.productId)!''}">
                        <button type="submit" class="btn btn-default">Submit</button>
                    </form>
                </div>

            </div>
        </div>
    </div>
</body>

</html>