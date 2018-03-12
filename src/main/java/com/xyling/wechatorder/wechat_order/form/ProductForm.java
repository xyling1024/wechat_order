package com.xyling.wechatorder.wechat_order.form;

import com.xyling.wechatorder.wechat_order.enums.ProductStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by: xyling
 * 2018-03-12 22:19
 */
@Data
@NoArgsConstructor
public class ProductForm {

    private String productId;

    @NotEmpty(message = "名称不能为空")
    private String productName;

    @NotNull(message = "价格不能为空")
    private BigDecimal productPrice;

    @NotNull(message = "库存不能为空")
    private Integer productStock;

    private String productDesc;

    @NotEmpty(message = "图片不能为空")
    private String productIcon;

    @NotNull(message = "类目不能为空")
    private Integer categoryType;

}
