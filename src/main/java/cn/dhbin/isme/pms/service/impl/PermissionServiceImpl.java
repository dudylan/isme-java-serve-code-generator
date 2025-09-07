package cn.dhbin.isme.pms.service.impl;

import cn.dhbin.isme.pms.domain.dto.PermissionDto;
import cn.dhbin.isme.pms.domain.entity.Permission;
import cn.dhbin.isme.pms.domain.request.CreatePermissionRequest;
import cn.dhbin.isme.pms.mapper.PermissionMapper;
import cn.dhbin.isme.pms.service.PermissionService;
import cn.dhbin.isme.pms.util.PermissionUtil;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 权限服务类的实现类，主要负责权限相关的处理
 *
 * @author dhb
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
    implements PermissionService {

    private static final String TYPE_MENU = "MENU";
    private static final String TYPE_BUTTON = "BUTTON";


    @Override
    public List<Permission> findByRoleId(Long roleId) {
        return getBaseMapper().findByRoleId(roleId);
    }

    @Override
    public void create(CreatePermissionRequest request) {
        Permission permission = request.convert(Permission.class);
        this.save(permission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBatch(List<CreatePermissionRequest> request) {
        List<Permission> list = request.stream().map(dto -> dto.convert(Permission.class))
            .toList();
        this.saveBatch(list);
    }

    @Override
    public List<PermissionDto> findAllMenu() {
        return lambdaQuery().eq(Permission::getType, TYPE_MENU)
            .list()
            .stream()
            .map(permission -> permission.convert(PermissionDto.class))
            .toList();
    }

    @Override
    public List<Tree<Long>> findAllMenuTree() {
        List<Permission> permissions = lambdaQuery().eq(Permission::getType, TYPE_MENU)
            .orderByAsc(Permission::getOrder)
            .list()
            .stream()
            .toList();
        return PermissionUtil.toTreeNode(permissions, null);
    }

    @Override
    public List<Tree<Long>> findAllTree() {
        List<Permission> permissions = lambdaQuery()
            .orderByAsc(Permission::getOrder)
            .list()
            .stream()
            .toList();
        return PermissionUtil.toTreeNode(permissions, null);
    }

    @Override
    public List<Permission> findButton(Long parentId) {
        return lambdaQuery()
            .eq(Permission::getParentId, parentId)
            .in(Permission::getType, TYPE_BUTTON)
            .orderByAsc(Permission::getOrder)
            .list()
            .stream()
            .toList();
    }

    @Override
    public boolean validateMenuPath(String path) {
        return lambdaQuery().eq(Permission::getPath, path).exists();
    }

}
