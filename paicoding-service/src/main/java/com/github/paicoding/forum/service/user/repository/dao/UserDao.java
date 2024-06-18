package com.github.paicoding.forum.service.user.repository.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.paicoding.forum.api.model.enums.YesOrNoEnum;
import com.github.paicoding.forum.api.model.vo.PageParam;
import com.github.paicoding.forum.service.user.repository.entity.UserDO;
import com.github.paicoding.forum.service.user.repository.entity.UserInfoDO;
import com.github.paicoding.forum.service.user.repository.mapper.UserInfoMapper;
import com.github.paicoding.forum.service.user.repository.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * UserDao
 *
 * @author YiHui
 * @date 2022/9/2
 */
@Repository
public class UserDao extends ServiceImpl<UserInfoMapper, UserInfoDO> {

    @Resource
    private UserMapper userMapper;

    /**
     * 获取指定用户ID之后的用户ID列表
     *
     * @param userId 用户ID
     * @param size 获取的用户数量
     * @return 用户ID列表
     */
    public List<Long> scanUserId(Long userId, Integer size) {
        return userMapper.getUserIdsOrderByIdAsc(userId, size == null ? PageParam.DEFAULT_PAGE_SIZE : size);
    }

    /**
     * 通过第三方账号ID获取用户
     *
     * @param accountId 第三方账号ID
     * @return 用户
     */
    public UserDO getByThirdAccountId(String accountId) {
        return userMapper.getByThirdAccountId(accountId);
    }

    /**
     * 通过用户名模糊查询用户
     *
     * @param userName 用户名
     * @return 用户列表
     */
    public List<UserInfoDO> getByUserNameLike(String userName) {
        LambdaQueryWrapper<UserInfoDO> query = Wrappers.lambdaQuery();
        query.select(UserInfoDO::getUserId, UserInfoDO::getUserName, UserInfoDO::getPhoto, UserInfoDO::getProfile)
                .and(!StringUtils.isEmpty(userName),
                        v -> v.like(UserInfoDO::getUserName, userName)
                )
                .eq(UserInfoDO::getDeleted, YesOrNoEnum.NO.getCode());
        return baseMapper.selectList(query);
    }

    /**
     * 保存用户，如果用户ID为空则插入，否则更新
     *
     * @param user 用户
     */
    public void saveUser(UserDO user) {
        if (user.getId() == null) {
            userMapper.insert(user);
        } else {
            userMapper.updateById(user);
        }
    }

    /**
     * 通过用户ID获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    public UserInfoDO getByUserId(Long userId) {
        LambdaQueryWrapper<UserInfoDO> query = Wrappers.lambdaQuery();
        query.eq(UserInfoDO::getUserId, userId)
                .eq(UserInfoDO::getDeleted, YesOrNoEnum.NO.getCode());
        return baseMapper.selectOne(query);
    }

    /**
     * 通过用户ID列表获取用户信息列表
     *
     * @param userIds 用户ID列表
     * @return 用户信息列表
     */
    public List<UserInfoDO> getByUserIds(Collection<Long> userIds) {
        LambdaQueryWrapper<UserInfoDO> query = Wrappers.lambdaQuery();
        query.in(UserInfoDO::getUserId, userIds)
                .eq(UserInfoDO::getDeleted, YesOrNoEnum.NO.getCode());
        return baseMapper.selectList(query);
    }

    /**
     * 获取用户数量
     *
     * @return 用户数量
     */
    public Long getUserCount() {
        return lambdaQuery()
                .eq(UserInfoDO::getDeleted, YesOrNoEnum.NO.getCode())
                .count();
    }

    /**
     * 更新用户信息，如果用户信息没有变化则不更新
     *
     * @param user 用户信息
     */
    public void updateUserInfo(UserInfoDO user) {
        UserInfoDO record = getByUserId(user.getUserId());
        if (record.equals(user)) {
            return;
        }
        if (StringUtils.isEmpty(user.getPhoto())) {
            user.setPhoto(null);
        }
        if (StringUtils.isEmpty(user.getUserName())) {
            user.setUserName(null);
        }
        user.setId(record.getId());
        updateById(user);
    }

    /**
     * 通过用户名获取用户
     *
     * @param userName 用户名
     * @return 用户
     */
    public UserDO getUserByUserName(String userName) {
        LambdaQueryWrapper<UserDO> queryUser = Wrappers.lambdaQuery();
        queryUser.eq(UserDO::getUserName, userName)
                .eq(UserDO::getDeleted, YesOrNoEnum.NO.getCode())
                .last("limit 1");
        return userMapper.selectOne(queryUser);
    }

    /**
     * 通过用户ID获取用户
     *
     * @param userId 用户ID
     * @return 用户
     */
    public UserDO getUserByUserId(Long userId) {
        return userMapper.selectById(userId);
    }

    /**
     * 更新用户
     *
     * @param userDO 用户
     */
    public void updateUser(UserDO userDO) {
        userMapper.updateById(userDO);
    }
}