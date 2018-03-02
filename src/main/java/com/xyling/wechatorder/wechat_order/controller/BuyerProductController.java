package com.xyling.wechatorder.wechat_order.controller;

import com.xyling.wechatorder.wechat_order.VO.ProductInfoVO;
import com.xyling.wechatorder.wechat_order.VO.ProductVO;
import com.xyling.wechatorder.wechat_order.VO.ResultVO;
import com.xyling.wechatorder.wechat_order.domain.ProductCategory;
import com.xyling.wechatorder.wechat_order.domain.ProductInfo;
import com.xyling.wechatorder.wechat_order.enums.ResultEnum;
import com.xyling.wechatorder.wechat_order.service.CategoryService;
import com.xyling.wechatorder.wechat_order.service.ProductService;
import com.xyling.wechatorder.wechat_order.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品控制层
 * Created by: xyling
 * 2018-03-01 17:55
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/list")
    public ResultVO list() {

        // 测试
//        ProductInfoVO productInfo = new ProductInfoVO("111111", "皮蛋粥", new BigDecimal(1.2), "好吃的皮蛋粥", "http://xxx.com");
//        ProductVO product = new ProductVO("热榜", 1, Arrays.asList(productInfo));
//        return new ResultVO<ProductVO>(0, "成功", Arrays.asList(product));

    // =======================================================================

        // 查询所有的已上架商品
        List<ProductInfo> productInfoList = productService.findUpAll();

        // 获取已上架商品的类目类型的集合
        /*ArrayList<Integer> categoryTypeList = new ArrayList<>();
        for (ProductInfo productInfo : productInfoList) {
            categoryTypeList.add(productInfo.getCategoryType());
        }*/
        // java8 lambda写法
        List<Integer> categoryTypeList = productInfoList.stream()
                .map( a -> a.getCategoryType() ).collect(Collectors.toList());

        // 根据类目类型查询所有商品类目(一次性查询, 一定不要在for循环中查询数据库!)
        List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        // 存放所有商品
        List<ProductVO> productVOList = new ArrayList<>();
        // 根据类目对商品进行分组封装
        for (ProductCategory category : categoryList) {
            // 存放某分类下的所有商品
            ArrayList<ProductInfoVO> foods = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                // 如果商品类目类型 == 当前遍历的类目类型
                if(productInfo.getCategoryType().equals(category.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    foods.add(productInfoVO);
                }
            }
            productVOList.add(new ProductVO(category.getCategoryName(), category.getCategoryType(), foods));
        }

       return ResultVOUtil.success(productVOList);

    }
}
