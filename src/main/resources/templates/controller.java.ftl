package ${package.Controller};

import ${package.Entity}.${entity};
import ${package.ServiceImpl}.${table.serviceImplName};
import ${requestPackage}.${requestFileName};
import ${RPackage}.R;
import ${RPackage}.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

/**
* ${entity} 控制器
* RESTful 风格
* 自动生成
*/
@RestController
@RequestMapping("/${entity?lower_case}")
@RequiredArgsConstructor
public class ${table.controllerName} {

    private final ${table.serviceImplName} service;

    /**
    * 分页查询
    */
    @GetMapping
    public R<?> list(${requestFileName} pageRequest) {
        IPage<${entity}> qp = pageRequest.toPage();
        LambdaQueryWrapper<${entity}> queryWrapper = new LambdaQueryWrapper<>();
        // 查询条件封装
        // 这里写你的查询条件
        IPage<${entity}> ret =  service.page(qp, queryWrapper);
        return R.ok(Page.convert(ret));
    }

    /**
    * 查询单个对象
    */
    @GetMapping("/{${primaryKeyFieldName}}")
    public R<${entity}> get(@PathVariable ${primaryKeyJavaType} ${primaryKeyFieldName}) {
        return R.ok(service.getById(id));
    }

    /**
    * 新增对象
    */
    @PostMapping
    public R<?> create(@RequestBody ${entity} entity) {
        return R.ok(service.save(entity));
    }

    /**
    * 更新对象
    */
    @PutMapping("/{${primaryKeyFieldName}}")
    public R<?> update(@PathVariable ${primaryKeyJavaType} ${primaryKeyFieldName}, @RequestBody ${entity} entity) {
        entity.setId(${primaryKeyFieldName});
        return R.ok(service.updateById(entity));
    }

    /**
    * 删除对象
    */
    @DeleteMapping("/{${primaryKeyFieldName}}")
    public R<?> delete(@PathVariable ${primaryKeyJavaType} ${primaryKeyFieldName}) {
        return R.ok(service.removeById(${primaryKeyFieldName}));
    }


}
