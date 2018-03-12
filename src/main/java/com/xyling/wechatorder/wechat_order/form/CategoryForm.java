package com.xyling.wechatorder.wechat_order.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by: xyling
 * 2018-03-12 22:19
 */
@Data
@NoArgsConstructor
public class CategoryForm {

    private Integer categoryId;

    private String categoryName;

    private Integer categoryType;

}
