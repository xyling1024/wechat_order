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
                    <form role="form" action="/seller/category/save" method="post">
                        <div class="form-group">
                            <label for="categoryName">名称</label>
                            <input type="text" class="form-control" name="categoryName" value="${(category.categoryName)!""}" />
                        </div>
                        <div class="form-group">

                            <label for="categoryType">type</label>
                            <input type="number" class="form-control" name="categoryType" value="${(category.categoryType)!""}" />
<#--                            <select name="categoryType" class="form-control">
                            <#list categoryList as c>
                                <option value="${c.categoryType}"
                                &lt;#&ndash;如果存在 用?? &ndash;&gt;
                                    <#if (category.categoryType)?? && category.categoryType == c.categoryType>
                                        selected
                                    </#if>
                                >${c.categoryName}</option>
                            </#list>
                            </select>-->
                        </div>
                        <input hidden type="text" name="categoryId" value="${(category.categoryId)!''}">
                        <button type="submit" class="btn btn-default">Submit</button>
                    </form>
                </div>

            </div>
        </div>
    </div>
</body>

</html>