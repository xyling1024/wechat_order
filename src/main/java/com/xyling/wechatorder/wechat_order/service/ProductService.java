package com.xyling.wechatorder.wechat_order.service;

import com.xyling.wechatorder.wechat_order.DTO.CartDTO;
import com.xyling.wechatorder.wechat_order.domain.ProductCategory;
import com.xyling.wechatorder.wechat_order.domain.ProductInfo;
import com.xyling.wechatorder.wechat_order.query.ProductQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by: xyling
 * 2018-03-01 13:16
 */
public interface ProductService {

    ProductInfo findOne(String productId);

    /**
     * 分页查询所有商品
     * @param page
     * @param size
     * @return
     */
    Page<ProductInfo> findAllByPage(Integer page, Integer size);

    /**
     * 根据条件分页查询商品
     * @param page
     * @param size
     * @param productQuery
     * @return
     */
    Page<ProductInfo> findAllByPageWithCriteria(Integer page, Integer size, ProductQuery productQuery);

    /**
     * 查询所有已上架商品
     * @return
     */
    public List<ProductInfo> findUpAll();

    /**
     * 分页查询所有已上架商品
     * @param page
     * @param size
     * @return
     */
    public Page<ProductInfo> findUpAll(Integer page, Integer size);

    /**
     * 查询所有下架商品
     * @return
     */
    public List<ProductInfo> findDownAll();

    /**
     * 分页查询所有下架商品
     * @param page
     * @param size
     * @return
     */
    public Page<ProductInfo> findDownAll(Integer page, Integer size);

    ProductInfo save(ProductInfo productInfo);

    // 加库存
    void increaseStock(List<CartDTO> cartDTOList);

    // 减库存
    void decreaseStock(List<CartDTO> cartDTOList);

    // 上架
    ProductInfo onSale(String productId);

    // 下架
    ProductInfo offSale(String productId);

    // 查询某个类目下的所有商品
    List<ProductInfo> findByCategorytype(Integer categoryType);
}
