package cn.dhbin.isme.pms.controller;

import cn.dhbin.isme.pms.domain.entity.Product;
import cn.dhbin.isme.pms.service.impl.ProductServiceImpl;
import cn.dhbin.isme.pms.domain.request.ProductRequest;
import cn.dhbin.isme.common.response.R;
import cn.dhbin.isme.common.response.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

/**
* Product 控制器
* RESTful 风格
* 自动生成
*/
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl service;

    /**
    * 分页查询
    */
    @GetMapping
    public R<?> list(ProductRequest pageRequest) {
        IPage<Product> qp = pageRequest.toPage();
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        // 查询条件封装
        // 这里写你的查询条件
        IPage<Product> ret =  service.page(qp, queryWrapper);
        return R.ok(Page.convert(ret));
    }

    /**
    * 查询单个对象
    */
    @GetMapping("/{id}")
    public R<Product> get(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    /**
    * 新增对象
    */
    @PostMapping
    public R<?> create(@RequestBody Product entity) {
        return R.ok(service.save(entity));
    }

    /**
    * 更新对象
    */
    @PutMapping("/{id}")
    public R<?> update(@PathVariable Long id, @RequestBody Product entity) {
        entity.setId(id);
        return R.ok(service.updateById(entity));
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{id}")
    public R<?> delete(@PathVariable Long id) {
        return R.ok(service.removeById(id));
    }


}
