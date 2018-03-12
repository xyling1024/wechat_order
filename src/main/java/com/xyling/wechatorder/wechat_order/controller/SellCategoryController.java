package com.xyling.wechatorder.wechat_order.controller;

import com.xyling.wechatorder.wechat_order.domain.ProductCategory;
import com.xyling.wechatorder.wechat_order.domain.ProductInfo;
import com.xyling.wechatorder.wechat_order.enums.ResultEnum;
import com.xyling.wechatorder.wechat_order.exception.SellException;
import com.xyling.wechatorder.wechat_order.form.CategoryForm;
import com.xyling.wechatorder.wechat_order.form.ProductForm;
import com.xyling.wechatorder.wechat_order.service.CategoryService;
import com.xyling.wechatorder.wechat_order.service.ProductService;
import com.xyling.wechatorder.wechat_order.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by: xyling
 * 2018-03-10 15:26
 */
@Controller
@RequestMapping("/seller/category")
@Slf4j
public class SellCategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    /**
     * 查询类目列表
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> map) {
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("category/list", map);
    }

    /**
     * 展示类目
     * @param categoryId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                              Map<String, Object> map) {
        if ( categoryId != null ) {
            ProductCategory category = categoryService.findOne(categoryId);
            if ( category == null ) {
                log.error("[卖家端商品类目展示] {}", ResultEnum.PRODUCT_CATEGORY_NO_EXIST);
                throw new SellException(ResultEnum.PRODUCT_CATEGORY_NO_EXIST);
            }
            map.put("category", category);
            List<ProductCategory> categoryList = categoryService.findAll();
            map.put("categoryList", categoryList);
        }
        return new ModelAndView("category/index", map);
    }

    /**
     * 保存类目
     * @param categoryForm
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        if ( bindingResult.hasErrors() ) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/category/index");
            return new ModelAndView("commons/error", map);
        }
        try {
            ProductCategory category = new ProductCategory();
            if ( categoryForm.getCategoryId() != null ) {   // 更新
                category = categoryService.findOne(categoryForm.getCategoryId());
                // 如果更改的是categoryType, 那么更新以原categoryType作为商品类目的所有商品的商品类目
                if ( categoryForm.getCategoryType() != category.getCategoryType() ) {
                    List<ProductInfo> productInfoList = productService.findByCategorytype(category.getCategoryType());
                    if (!CollectionUtils.isEmpty(productInfoList)) {
                        for (ProductInfo productInfo : productInfoList) {
                            productInfo.setCategoryType(categoryForm.getCategoryType());
                            productService.save(productInfo);
                        }
                    }
                }
            }
            BeanUtils.copyProperties(categoryForm, category);
            categoryService.save(category);
        } catch (SellException e) {
            log.error("[卖家端保存商品类目] {}", e.getMessage());
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/category/list");
            return new ModelAndView("commons/error", map);
        }
        map.put("url", "/sell/seller/category/list");
        return new ModelAndView("commons/success", map);
    }

}
