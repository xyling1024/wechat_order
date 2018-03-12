package com.xyling.wechatorder.wechat_order.controller;

import com.xyling.wechatorder.wechat_order.DTO.OrderDTO;
import com.xyling.wechatorder.wechat_order.VO.ProductInfoVO;
import com.xyling.wechatorder.wechat_order.domain.ProductCategory;
import com.xyling.wechatorder.wechat_order.domain.ProductInfo;
import com.xyling.wechatorder.wechat_order.enums.ResultEnum;
import com.xyling.wechatorder.wechat_order.exception.SellException;
import com.xyling.wechatorder.wechat_order.form.ProductForm;
import com.xyling.wechatorder.wechat_order.service.CategoryService;
import com.xyling.wechatorder.wechat_order.service.OrderService;
import com.xyling.wechatorder.wechat_order.service.ProductService;
import com.xyling.wechatorder.wechat_order.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/seller/product")
@Slf4j
public class SellProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 查询商品列表
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        if ( page < 1 ) {
            page = 1;
        }
        Page<ProductInfo> productInfoPage = productService.findAllByPage(page - 1, size);
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("product/list", map);
    }

    /**
     * 上架
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String, Object> map) {
        try {
            ProductInfo productInfo = productService.onSale(productId);
        } catch (SellException e) {
            log.error("[卖家端商品上架] {}", e.getMessage());
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("commons/error", map);
        }
        map.put("msg", ResultEnum.ONSALE_PRODUCT_SUCCESS.getMsg());
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("commons/success", map);
    }

    /**
     * 下架
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                               Map<String, Object> map) {
        try {
            ProductInfo productInfo = productService.offSale(productId);
        } catch (SellException e) {
            log.error("[卖家端商品下架] {}", e.getMessage());
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("commons/error", map);
        }
        map.put("msg", ResultEnum.OFFSALE_PRODUCT_SUCCESS.getMsg());
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("commons/success", map);
    }

    /**
     * 跳转到商品详情页
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView update(@RequestParam(value = "productId", required = false) String productId,
                               Map<String, Object> map) {
        if ( productId != null ) {
            ProductInfo productInfo = productService.findOne(productId);
            if ( productInfo == null ) {
                log.error("[卖家端查询商品详情] {}", ResultEnum.PRODUCT_NO_EXIST);
                throw new SellException(ResultEnum.PRODUCT_NO_EXIST);
            }
            map.put("productInfo", productInfo);
        }
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("product/index", map);

    }

    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm productForm,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        if ( bindingResult.hasErrors() ) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("commons/error", map);
        }
        try {
            ProductInfo productInfo = new ProductInfo();
            if ( !StringUtils.isEmpty(productForm.getProductId()) ) {
                productInfo = productService.findOne(productForm.getProductId());
            } else {
                productForm.setProductId(KeyUtil.genUniqueKey());
            }
            BeanUtils.copyProperties(productForm, productInfo);
            productService.save(productInfo);
        } catch (SellException e) {
            log.error("[卖家端保存商品] {}", e.getMessage());
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("commons/error", map);
        }
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("commons/success", map);

    }
}
