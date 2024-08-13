package cn.codefit.quju.system.api.vo;

import cn.codefit.quju.base.model.BaseBean;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SysUserVo extends BaseBean {

    private static final long serialVersionUID = 1L;

    /**
     * 管理系统用户主键
     */
    private Long id;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 性别：0-男 1-女
     */
    private Integer gender;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户头像(仅存储文件名称)
     */
    private String avatarUrl;

    /**
     * 用户状态-0、正常(true) 1、禁用(否)
     */
    private Integer status;

    /**
     * 是否已绑定默认微信服务号 0、未绑定 1、已绑定
     */
    private Integer hasWechat;

    /**
     * 来源web端用户 1、趣聚后台管理系统
     */
    private Integer platform;

    /**
     * 国家地区
     */
    private String country;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 用户真实姓名
     */
    private String name;

    /**
     * 用户身份证号码
     */
    private String idNumber;

    /**
     * 逻辑删除标识：0-未删除；1-已删除
     */
    private Integer isDeleted;

    /**
     * 创建人工号
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;

    /**
     * 创建人昵称
     */
    private String createUserName;

    /**
     * 修改人工号
     */
    private String modifyUser;

    /**
     * 修改人昵称
     */
    private String modifyUserName;

    /*-----------关联字段-------------------------*/

    /**
     * 用户角色编码集合【目前仅支持单角色】
     */
    private List<Long> rolesIds;

}
